package ca.mcgill.ecse.assetplus.features;

import java.util.*;
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
      targetTicket.setFixApprover(assetPlus.getManager());
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
        targetTicket.assign(ticketFixer, timeEstimate, priority, targetTicket.hasFixApprover(), manager);
        break;
      case "InProgress":
        targetTicket.assign(ticketFixer, timeEstimate, priority, targetTicket.hasFixApprover(), manager);
        targetTicket.startTicket();
        break;
      case "Resolved":
        targetTicket.assign(ticketFixer, timeEstimate, priority, targetTicket.hasFixApprover(), manager);
        targetTicket.startTicket();
        targetTicket.closeTicket();
        break;
      case "Closed":
        targetTicket.assign(ticketFixer, timeEstimate, priority, targetTicket.hasFixApprover(), manager);
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
    tickets = AssetPlusFeatureSet6Controller.getTickets();
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String ticketId, String employeeEmail, String timeEstimate, String priority, String requiresApproval) {
    error = AssetPlusFeatureSet4Controller.AssignHotelStaffToMaintenanceTicket(Integer.parseInt(ticketId), employeeEmail, TimeEstimate.valueOf(timeEstimate.trim()), PriorityLevel.valueOf(priority.trim()), Boolean.parseBoolean(requiresApproval), "manager@ap.com"); // calling controller method
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
    // Write code here that turns the phrase above into concrete actions
  
    //calling controller method
    error = AssetPlusFeatureSet4Controller.DisapproveWorkOnMaintenanceTicket(Integer.parseInt(ticketID), Date.valueOf(dateDisapproved.trim()), reason);
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String ticketId, String status) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));

    assertNotNull(maintenanceTicket); // Making sure that the ticket exists
    assertEquals(status, maintenanceTicket.getStatusFullName()); // Making sure that the description matches
  }

  /**
   * @author Colin Xiong
   */

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String errorMessage) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    assertEquals(errorMessage, error);
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String ticketId) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));

    assertNull(maintenanceTicket);
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String ticketId,
      String estimatedTime, String priority, String requiresApproval) {
    // Colin
    // Write code here that turns the phrase above into concrete actions

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));

    assertNotNull(maintenanceTicket);
    assertEquals(TimeEstimate.valueOf(estimatedTime), maintenanceTicket.getTimeToResolve());
    assertEquals(PriorityLevel.valueOf(priority), maintenanceTicket.getPriority());
    assertEquals(requiresApproval, Boolean.toString(maintenanceTicket.hasFixApprover()));
    // Set proper attributes
    // if (maintenanceTicket != null) {   
    //   switch (estimatedTime) {
    //     case "LessThanADay":
    //       maintenanceTicket.setTimeToResolve(TimeEstimate.LessThanADay);
    //       break;
    //     case "OneToThreeDays":
    //       maintenanceTicket.setTimeToResolve(TimeEstimate.OneToThreeDays);
    //       break;
    //     case "ThreeToSevenDays":
    //       maintenanceTicket.setTimeToResolve(TimeEstimate.ThreeToSevenDays);
    //       break;
    //     case "OneToThreeWeeks":
    //       maintenanceTicket.setTimeToResolve(TimeEstimate.OneToThreeWeeks);
    //       break;
    //     case "ThreeOrMoreWeeks":
    //       maintenanceTicket.setTimeToResolve(TimeEstimate.OneToThreeWeeks);
    //       break;
    //   }

    //   switch (priority) {
    //   case "Urgent":
    //     maintenanceTicket.setPriority(PriorityLevel.Urgent);
    //     break;
    //   case "Normal":
    //     maintenanceTicket.setPriority(PriorityLevel.Normal);
    //     break;
    //   case "Low":
    //     maintenanceTicket.setPriority(PriorityLevel.Low);
    //     break;
    //   }

    //   if (maintenanceTicket.hasFixApprover() == Boolean.parseBoolean(requiresApproval)) {
    //     maintenanceTicket.setFixApprover(assetPlus.getManager());
    //   }
    // }

  }

  /**
   * @author Houman Azari
   */

  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String ticketId, String employeeEmail) {
    // Houman
    // Write code here that turns the phrase above into concrete actions

    MaintenanceTicket targetTicket = assetPlus.getMaintenanceTicket(Integer.parseInt(ticketId));
    User ticketFixer = (HotelStaff) User.getWithEmail(employeeEmail);

    assertNotNull(targetTicket);
    assertEquals(ticketFixer, targetTicket.getTicketFixer());
    }

  /**
   * @author Houman Azari
   */

  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String string) {
    // Houman
    // Write code here that turns the phrase above into concrete actions
    int expectedAmountOfTickets = Integer.parseInt(string);
    int amountOfTickets = assetPlus.numberOfMaintenanceTickets();
    //Checking if the the two numbers are equal
    assertEquals(expectedAmountOfTickets, amountOfTickets);
    }

  /**
   * @author Houman Azari
   */

  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    // Houman
        // Write code here that turns the phrase above into concrete actions
    List<List<String>> rows = dataTable.asLists();

    for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()){
      List<String> ticketAsList = new ArrayList<String>();
      ticketAsList.add(""+ticket.getId());
      ticketAsList.add(ticket.getTicketRaiser().getEmail());
      ticketAsList.add(ticket.getRaisedOnDate().toString());
      ticketAsList.add(ticket.getDescription());
      if (ticket.getAsset() == null){
        ticketAsList.add(null);
        ticketAsList.add(null);
        ticketAsList.add(null);
        ticketAsList.add(null);
        ticketAsList.add(null);
      }
      else{
        ticketAsList.add(ticket.getAsset().getAssetType().getName());
        ticketAsList.add(""+ticket.getAsset().getAssetType().getExpectedLifeSpan());
        ticketAsList.add(ticket.getAsset().getPurchaseDate().toString());
        ticketAsList.add(""+ticket.getAsset().getFloorNumber());
        ticketAsList.add(""+ticket.getAsset().getRoomNumber());
      }
      if (ticket.getStatusFullName().equals("Open")){
        ticketAsList.add(ticket.getStatusFullName());
        ticketAsList.add(null);
        ticketAsList.add(null);
        ticketAsList.add(null);
        ticketAsList.add(null);
      }
      else{
        ticketAsList.add(ticket.getStatusFullName());
        ticketAsList.add(ticket.getTicketFixer().getEmail());
        ticketAsList.add(ticket.getTimeToResolve().toString());
        ticketAsList.add(ticket.getPriority().toString());
        ticketAsList.add(Boolean.toString(ticket.hasFixApprover()));

        assertTrue(rows.contains(ticketAsList));
      }
    }
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String string,
      io.cucumber.datatable.DataTable dataTable) {
    // Ray
        // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.

    int ticketId = Integer.valueOf(string);
    List<List<String>> rows = dataTable.asLists();

    for (MaintenanceNote note : MaintenanceTicket.getWithId(ticketId).getTicketNotes()){
      List<String> noteAsList = new ArrayList<String>();

      noteAsList.add(note.getNoteTaker().getEmail());

      noteAsList.add(note.getDate().toString());

      noteAsList.add(note.getDescription());

      assertTrue(rows.contains(noteAsList));
    }
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    // Ray
    // Write code here that turns the phrase above into concrete actions
    int ticketId = Integer.valueOf(string);

    assertEquals(0, MaintenanceTicket.getWithId(ticketId).numberOfTicketNotes());

  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String string,
      io.cucumber.datatable.DataTable dataTable) {
    // Ray
        // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    int ticketId = Integer.valueOf(string);
    List<String> rows = dataTable.asList();

    for(TicketImage image : MaintenanceTicket.getWithId(ticketId).getTicketImages()){
      String url = image.getImageURL();
      assertTrue(rows.contains(url));
    }
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    // Ray
    // Write code here that turns the phrase above into concrete actions
    int ticketId = Integer.valueOf(string);

    assertEquals(0, MaintenanceTicket.getWithId(ticketId).numberOfTicketImages());
  }
}
