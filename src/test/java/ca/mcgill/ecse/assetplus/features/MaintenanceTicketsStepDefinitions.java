package ca.mcgill.ecse.assetplus.features;

import java.util.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MaintenanceTicketsStepDefinitions {

  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error;
  private List<TOMaintenanceTicket> tickets;

  /**
   * @author Sebastian Reinhardt
   */

  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // Retrieving the data from the feature // file is a usable format

    for (Map<String, String> row : rows) { // iterating through the rows of the data from the feature file
    assetPlus.addEmployee(row.get("email"), row.get("name"), row.get("password"), row.get("phoneNumber")); // adding employees with the given information from the feature file to the assetPlus
    }
  }

  /**
   * @author Sebastian Reinhardt
   */

  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // Retrieving the data from the feature file is a usable format

    for (Map<String, String> row : rows) { // iterating through the rows of the data from the feature file
      Manager manager = new Manager(row.get("email"), "", row.get("password"), "", assetPlus); // Creating the corresponding manager
      assetPlus.setManager(manager); // setting the manager
    }
  }

  /**
   * @author Sebastian Reinhardt
   */

  @Given("the following asset types exist in the system")
  public void the_following_asset_types_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // Retrieving the data from the feature file is a usable format

    for (Map<String, String> row : rows) { // iterating through the rows of the data from the feature file
      assetPlus.addAssetType(row.get("name"), Integer.parseInt(row.get("expectedLifeSpan"))); // adding assetTypes with the given information from the feature file
    }
  }

  /**
   * @author Sebastian Reinhardt
   */

  @Given("the following assets exist in the system")
  public void the_following_assets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // Retrieving the data from the feature file is a usable format

    for (Map<String, String> row : rows) { // iterating through the rows of the data from the feature file
      assetPlus.addSpecificAsset(Integer.parseInt(row.get("assetNumber")),
          Integer.parseInt(row.get("floorNumber")), Integer.parseInt(row.get("roomNumber")), Date.valueOf(row.get("purchaseDate")), AssetType.getWithName(row.get("type"))); // adding specificAssets with the given information from the feature file
    }
  }

  /**
   * @author Alexander Liu
   */

  @Given("the following tickets exist in the system")
  public void the_following_tickets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    // Retrieving the data from the feature file in a usable format
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row: rows) {
      // Get appropriate user who is the ticket raiser
      User ticketRaiser = User.getWithEmail(row.get("ticketRaiser"));
      // Instantiate new maintenance ticket
      MaintenanceTicket newMaintenanceTicket = new MaintenanceTicket(Integer.parseInt(row.get("id")), Date.valueOf(row.get("raisedOnDate")), row.get("description"), assetPlus, ticketRaiser);
      // Add specific assets to the new maintenance ticket if applicable
      if (!(row.get("assetNumber") == null)) {
        newMaintenanceTicket.setAsset(SpecificAsset.getWithAssetNumber(Integer.parseInt(row.get("assetNumber"))));
      }

      // Check if table has a column called timeToResolve
      if (row.containsKey("timeToResolve")) {
        // Check if the state is not open (... to initialize appropriate variables)
        if (!(row.get("status").equals("Open"))) {
          // Storing values in variables
          HotelStaff ticketFixer = (HotelStaff) User.getWithEmail(row.get("fixedByEmail"));
          TimeEstimate timeEstimate = TimeEstimate.valueOf(row.get("timeToResolve"));
          PriorityLevel priorityLevel = PriorityLevel.valueOf(row.get("priority"));
          Boolean isApprovalRequired = row.get("approvalRequired").equals("true");

          // Set the new maintenance ticket's state
          switch (row.get("status")) {
            case "Assigned":
              newMaintenanceTicket.assign(ticketFixer, timeEstimate, priorityLevel, isApprovalRequired, assetPlus.getManager());
              break;
            case "InProgress":
              newMaintenanceTicket.assign(ticketFixer, timeEstimate, priorityLevel, isApprovalRequired, assetPlus.getManager());
              newMaintenanceTicket.startTicket();
              break;
            case "Resolved":
              newMaintenanceTicket.assign(ticketFixer, timeEstimate, priorityLevel, isApprovalRequired, assetPlus.getManager());
              newMaintenanceTicket.startTicket();
              newMaintenanceTicket.closeTicket();
              break;
            case "Closed":
              newMaintenanceTicket.assign(ticketFixer, timeEstimate, priorityLevel, isApprovalRequired, assetPlus.getManager());
              newMaintenanceTicket.startTicket();
              newMaintenanceTicket.closeTicket();
              break;
            default:
              // Do nothing
              break;
          }
        }
      }
    }
  }

  /**
   * @author Sebastian Reinhardt
   */

  @Given("the following notes exist in the system")
  public void the_following_notes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // Retrieving the data from the feature file is a usable format

    for (Map<String, String> row : rows) { // iterating through the rows of the data from the feature file
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(row.get("ticketId"))); // finding the correct maintenanceTicket
      maintenanceTicket.addTicketNote(Date.valueOf(row.get("addedOnDate")), row.get("description"), (HotelStaff) HotelStaff.getWithEmail(row.get("noteTaker"))); // adding the maintenanceNotes with the given information from the feature file
    }
  }

  /**
   * @author Sebastian Reinhardt
   */

  @Given("the following ticket images exist in the system")
  public void the_following_ticket_images_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // Retrieving the data from the feature file is a usable format
    for (Map<String, String> row : rows) { // iterating through the rows of the data from the feature file
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(row.get("ticketId"))); // finding the correct maintenanceTicket
      maintenanceTicket.addTicketImage(row.get("imageUrl")); // adding the TicketImages from the feature file
    }
  }

  /**
   * @author Alexander Liu
   */

  @Given("ticket {string} is marked as {string} with requires approval {string}")
  public void ticket_is_marked_as_with_requires_approval(String ticketId, String state,
      String requiresApproval) {
    // Fetch specific ticket
    MaintenanceTicket targetTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));
    
    // ===== DUMMY VALUES =====
    HotelStaff ticketFixer = (HotelStaff) assetPlus.getManager();
    TimeEstimate timeEstimate = TimeEstimate.LessThanADay;
    PriorityLevel priority = PriorityLevel.Low;
    Manager manager = assetPlus.getManager();
    
    Boolean requiresManagerApproval = false;
    // Set maintenance ticket fix approver if applicable
    if (requiresApproval.equals("true")) {
      requiresManagerApproval = true;
    }
    // Set state of the maintenance ticket
    switch (state) {
      case "Assigned":
        targetTicket.assign(ticketFixer, timeEstimate, priority, requiresManagerApproval, manager);
        break;
      case "InProgress":
        targetTicket.assign(ticketFixer, timeEstimate, priority, requiresManagerApproval, manager);
        targetTicket.startTicket();
        break;
      case "Resolved":
        targetTicket.assign(ticketFixer, timeEstimate, priority, requiresManagerApproval, manager);
        targetTicket.startTicket();
        targetTicket.closeTicket();
        break;
      case "Closed":
        targetTicket.assign(ticketFixer, timeEstimate, priority, requiresManagerApproval, manager);
        targetTicket.startTicket();
        targetTicket.closeTicket();
        break;
      default:
        // Default case does nothing
        // This case includes the open state since maintenance tickets are in the open state by default
        break;
    }
  }

  /**
   * @author Alexander Liu
   */

  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String ticketId, String status) {
    // Fetch maintenance ticket from the system
    MaintenanceTicket targetTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));
    
    // ===== DUMMY VALUES =====
    HotelStaff ticketFixer = (HotelStaff) assetPlus.getManager();
    TimeEstimate timeEstimate = TimeEstimate.LessThanADay;
    PriorityLevel priority = PriorityLevel.Low;
    Manager manager = assetPlus.getManager();

    // Setting the maintenance ticket's state
    switch (status) {
      case "Assigned":
        targetTicket.assign(ticketFixer, timeEstimate, priority, false, manager);
        break;
      case "InProgress":
        targetTicket.assign(ticketFixer, timeEstimate, priority, false, manager);
        targetTicket.startTicket();
        break;
      case "Resolved":
        targetTicket.assign(ticketFixer, timeEstimate, priority, true, manager);
        targetTicket.startTicket();
        targetTicket.closeTicket();
        break;
      case "Closed":
        targetTicket.assign(ticketFixer, timeEstimate, priority, false, manager);
        targetTicket.startTicket();
        targetTicket.closeTicket();
        break;
      default:
        // Default case does nothing
        // This case includes the open state since maintenance tickets are in the open state by default
        break;
    }
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    tickets = AssetPlusFeatureSet6Controller.getTickets(); // Calling controller method
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String ticketId, String employeeEmail, String timeEstimate, String priority, String requiresApproval) {
    error = AssetPlusFeatureSet4Controller.AssignHotelStaffToMaintenanceTicket(Integer.parseInt(ticketId), employeeEmail, timeEstimate, priority, Boolean.parseBoolean(requiresApproval), "manager@ap.com"); // calling controller method
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String ticketId) {
    error = AssetPlusFeatureSet4Controller.StartWorkOnMaintenanceTicket(ticketId); // calling controller method
  }

  /**
   * @author Alice Godbout
   */

  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String ticketID) {
    // Write code here that turns the phrase above into concrete actions
    error = AssetPlusFeatureSet4Controller.ApproveWorkOnMaintenanceTicket(ticketID); //calling controller method
  }

  /**
   * @author Alice Godbout
   */

  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String ticketID) {
    // Write code here that turns the phrase above into concrete actions
    error = AssetPlusFeatureSet4Controller.CompleteWorkOnMaintenanceTicket(ticketID); //calling controller method
  }

  /**
   * @author Alice Godbout
   */

  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(String ticketID,
      String dateDisapproved, String reason) {
    error = AssetPlusFeatureSet4Controller.DisapproveWorkOnMaintenanceTicket(Integer.parseInt(ticketID), Date.valueOf(dateDisapproved.trim()), reason); //calling controller method
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String ticketId, String status) {
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId)); // Getting target ticket

    assertNotNull(maintenanceTicket); // Making sure that the ticket exists
    assertEquals(status, maintenanceTicket.getStatusFullName()); // Making sure that the description matches
  }

  /**
   * @author Colin Xiong
   */

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String errorMessage) {
    assertEquals(errorMessage, error); //Asserting appropriate error message is returned
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String ticketId) {
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId)); //Getting target ticket

    assertNull(maintenanceTicket); //Asserting it does not exist
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String ticketId,
      String estimatedTime, String priority, String requiresApproval) {
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId)); //Getting target ticket

    assertNotNull(maintenanceTicket); //Asserting it exists
    assertEquals(TimeEstimate.valueOf(estimatedTime), maintenanceTicket.getTimeToResolve()); //Asserting the time estimate is correct
    assertEquals(PriorityLevel.valueOf(priority), maintenanceTicket.getPriority()); //Asserting the priority is correct
    assertEquals(requiresApproval, Boolean.toString(maintenanceTicket.hasFixApprover())); //Asserting the fix approver is correct
  }

  /**
   * @author Houman Azari
   */

  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String ticketId, String employeeEmail) {
    MaintenanceTicket targetTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId)); //Getting target ticket

    assertNotNull(targetTicket); //Asserting ticket exists
    assertEquals(employeeEmail, targetTicket.getTicketFixer().getEmail()); //Asserting correct ticket fixer
    }

  /**
   * @author Houman Azari
   */

  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String expectedNumOfTickets) {
    int expectedAmountOfTickets = Integer.parseInt(expectedNumOfTickets); //Parsing amount of tickets to int
    int amountOfTickets = assetPlus.numberOfMaintenanceTickets(); //Getting actual amount of tickets

    assertEquals(expectedAmountOfTickets, amountOfTickets); //Checking if the the two numbers are equal
    }

  /**
   * @author Sebastian Reinhardt
   */

  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> maps = dataTable.asMaps(); // getting the data in a usable format

    for (Map<String, String> map : maps){ //iterating through the maps of data
      int id = Integer.parseInt(map.get("id")); //getting the id of the ticket
      TOMaintenanceTicket ticket = null; //instantianting the ticket to be verified exists
      for (TOMaintenanceTicket temp : tickets){ //iterating through the tickets
        if (temp.getId() == id){ //checking whether the ticket id matches the target id
          ticket = temp; //assigning the corresponding ticket
        }
      }
      assertNotNull(ticket); //making sure the ticket is not null
      assertEquals(id, ticket.getId()); //Asserting the ticket ids are the same
      assertEquals(map.get("ticketRaiser"), ticket.getRaisedByEmail()); //Asserting the ticket raisers are the same
      assertEquals(map.get("raisedOnDate"), ticket.getRaisedOnDate().toString()); //Asserting the ticket raisedOnDate's are the same
      assertEquals(map.get("description"), ticket.getDescription()); //Asserting the ticket descriptions' are the same
      assertEquals(map.get("assetName"), ticket.getAssetName()); //Asserting the ticket's assetName's are the same
      String expectLifeSpanStr = map.get("expectLifeSpan"); //Getting the expectLifeSpan
      int expectLifeSpan = -1; //Instantiating the value to be compared to
      if (expectLifeSpanStr != null) { //Checking that it is not null
        expectLifeSpan = Integer.parseInt(expectLifeSpanStr); // Assigning the correct value
      }
      assertEquals(expectLifeSpan, ticket.getExpectLifeSpanInDays()); //Asserting the tickets lifespans' are the same
      String purchaseDateStr = map.get("purchaseDate"); //Getting the purchaseDate
      Date purchaseDate = null; //Instantiating the value to be compared to
      if (purchaseDateStr != null) { //Checking that it is not null
        purchaseDate = Date.valueOf(purchaseDateStr); // Assigning the correct value
      }
      assertEquals(purchaseDate, ticket.getPurchaseDate()); //Asserting the tickets purchaseDates' are the same
      String floorNumberStr = map.get("floorNumber"); //Getting the floorNumber
      int floorNumber = -1; //Instantiating the value to be compared to
      if (floorNumberStr != null) { //Checking that it is not null
        floorNumber = Integer.parseInt(floorNumberStr); // Assigning the correct value
      }
      assertEquals(floorNumber, ticket.getFloorNumber()); //Asserting the tickets floorNumbers' are the same
      String roomNumberStr = map.get("roomNumber"); //Getting the roomNumber
      int roomNumber = -1; //Instantiating the value to be compared to
      if (roomNumberStr != null) { //Checking that it is not null
        roomNumber = Integer.parseInt(roomNumberStr); // Assigning the correct value
      }
      assertEquals(roomNumber, ticket.getRoomNumber()); //Asserting the tickets roomNumbers' are the same
      assertEquals(map.get("status"), ticket.getStatus()); //Asserting the ticket status are the same
      String fixedByExpected = map.get("fixedByEmail"); //Getting the fixedByEmail
      String fixedByEmail = ""; //Instantiating the value to be compared to
      if (fixedByExpected != null) { //Checking that it is not null
        fixedByEmail = fixedByExpected; // Assigning the correct value
      }
      assertEquals(fixedByEmail, ticket.getFixedByEmail()); //Asserting the tickets fixedBy are the same
      String timeToResolveExpected = map.get("timeToResolve"); //Getting the timeToResolve
      String timeToResolve = ""; //Instantiating the value to be compared to
      if (timeToResolveExpected != null) { //Checking that it is not null
        timeToResolve = timeToResolveExpected; // Assigning the correct value
      }
      assertEquals(timeToResolve, ticket.getTimeToResolve()); //Asserting the tickets timeToResolves' are the same
      String priorityExpected = map.get("priority"); //Getting the priority
      String priority = ""; //Instantiating the value to be compared to
      if (priorityExpected != null) { //Checking that it is not null
        priority = priorityExpected; // Assigning the correct value
      }
      assertEquals(priority, ticket.getPriority()); //Asserting the tickets priorities' are the same
      if (ticket.getStatus().equals("Open")){ //Checking whether the ticket status is equal to Open
        assertFalse(ticket.getApprovalRequired()); //If it is make sure that a ticket's approvalRequired is false, as this is the default value of the boolean
      }
      else{
        assertEquals(map.get("approvalRequired"), Boolean.toString(ticket.getApprovalRequired())); //if not, make sure the approvalRequireds' are the same
      }
    }
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String id,
      io.cucumber.datatable.DataTable dataTable) {
    int ticketId = Integer.valueOf(id); //Parsing ticket id into int
    List<List<String>> rows = dataTable.asLists(); //Retrieving the data

    for (MaintenanceNote note : MaintenanceTicket.getWithId(ticketId).getTicketNotes()){ //iterating through the target ticket's notes
      List<String> noteAsList = new ArrayList<String>(); //Creating new list version of the note

      noteAsList.add(note.getNoteTaker().getEmail()); //adding email to list version of the note

      noteAsList.add(note.getDate().toString()); //adding date to list version of the note

      noteAsList.add(note.getDescription()); //adding description to list version of the note

      assertTrue(rows.contains(noteAsList)); //Asserting the list version of the note is contained in the data
    }
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String id) {
    int ticketId = Integer.valueOf(id); //parsing the id into an int

    assertEquals(0, MaintenanceTicket.getWithId(ticketId).numberOfTicketNotes()); //Asserting that the given ticket has no notes

  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String id,
      io.cucumber.datatable.DataTable dataTable) {
    int ticketId = Integer.valueOf(id); //parsing the id into an int
    List<String> rows = dataTable.asList(); //Retrieving the data in a usable form

    for(TicketImage image : MaintenanceTicket.getWithId(ticketId).getTicketImages()){ //iterating through a notes images
      String url = image.getImageURL(); //getting the image url
      assertTrue(rows.contains(url)); //Asserting the ticket should could the image
    }
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String id) {
    int ticketId = Integer.valueOf(id); //parsing the id into an int

    assertEquals(0, MaintenanceTicket.getWithId(ticketId).numberOfTicketImages()); //Asserting that the given ticket has no images
  }
}
