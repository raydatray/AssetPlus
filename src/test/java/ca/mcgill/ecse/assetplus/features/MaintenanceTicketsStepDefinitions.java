package ca.mcgill.ecse.assetplus.features;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
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
      String ticketRaiserEmail = row.get("ticketRaiser");
      User ticketRaiser = null;

      if (ticketRaiserEmail.equals("manager@ap.com")) {
        ticketRaiser = assetPlus.getManager();
      } else if (ticketRaiserEmail.endsWith("ap.com")) {
        for (Employee employee: assetPlus.getEmployees()) {
          if (employee.getEmail().equals(ticketRaiserEmail)) {
            ticketRaiser = employee;
          }
        }
      } else {
        for (Guest guest: assetPlus.getGuests()) {
          if (guest.getEmail().equals(ticketRaiserEmail)) {
            ticketRaiser = guest;
          }
        }
      }
      // Can I assume that the feature file scenarios have valid input? Otherwise, I would have to do a null check on ticketRaiser to see if the ticketRaiser email was matched to a user in the system.

      // Instantiate new maintenance ticket
      MaintenanceTicket newMaintenanceTicket = new MaintenanceTicket(Integer.parseInt(row.get("id")), Date.valueOf(row.get("raisedOnDate")), row.get("description"), assetPlus, ticketRaiser);
      // Add specific assets to the new maintenance ticket
      newMaintenanceTicket.setAsset(SpecificAsset.getWithAssetNumber(Integer.parseInt(row.get("assetNumber"))));
      // Once again assuming Integer.parseInt() will not throw an error because assuming the feature file's data table contains only valid input
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
    // Alex
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Alexander Liu
   */

  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String string, String string2) {
    // Alex
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    // Aurelia
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String ticketId, String employeeEmail, String timeEstimate, String priority, String requiresApproval) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));  
        // Aurelia
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Aurelia Bouliane
   */

  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String ticketId) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));
  }

  /**
   * @author Alice Godbout
   */

  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String string) {
    // Alice
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Alice Godbout
   */

  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String string) {
    // Alice
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Alice Godbout
   */

  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(String string,
      String string2, String string3) {
    // Alice
        // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String string, String string2) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(string));

    maintenanceTicket.setDescription(string2);
  }

  /**
   * @author Colin Xiong
   */

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    throw new AssertionError(string);
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String string) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(string));

    maintenanceTicket.delete();
  }

  /**
   * @author Colin Xiong
   */

  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String string,
      String string2, String string3, String string4) {
    // Colin
    // Write code here that turns the phrase above into concrete actions
    String estimatedTime = string2;
    String priority = string3;
    String requiresApproval = string4;

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(string));

    maintenanceTicket.setTimeToResolve(TimeEstimate.estimatedTime);
    maintenanceTicket.setPriority(PriorityLevel.priority);
    maintenanceTicket.setFixApprover(requiresApproval);
  }

  /**
   * @author Houman Azari
   */

  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String string, String string2) {
    // Houman
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Houman Azari
   */

  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String string) {
    // Houman
    // Write code here that turns the phrase above into concrete actions
    String expectedAmountOfTickets = string;
    Int amountOfTickets = assetPlus.numberOfMaintenanceTickets()
    //Checking if the the two numbers are equal
    if !amountOfTickets.equals(Integer.parseInt(expectedAmountOfTickets)) {
      throw new io.cucumber.java.PendingException();
    }    
  }

  /**
   * @author Houman Azari
   */

  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    // Houman
        // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
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
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    // Ray
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
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
    throw new io.cucumber.java.PendingException();
  }

  /**
   * @author Ray Liu
   */

  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    // Ray
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
