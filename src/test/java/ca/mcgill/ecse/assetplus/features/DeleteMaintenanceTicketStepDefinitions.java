package ca.mcgill.ecse.assetplus.features;

import java.util.List;
import java.util.Map;
import java.sql.Date;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteMaintenanceTicketStepDefinitions {

  AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
   * Adding employees given in the first @Given clause in Gherkin feature file.
   *
   * @author Isbat-ul Islam
   * @param dataTable
   */
  @Given("the following employees exist in the system \\(p7)")
  public void the_following_employees_exist_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String phoneNumber = row.get("phoneNumber");
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
  }

  /**
   * Adding Manager from the feature file.
   *
   * @author Isbat-ul Islam
   * @param dataTable
   */
  @Given("the following manager exists in the system \\(p7)")
  public void the_following_manager_exists_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String aEmail = row.get("email");
      String aPassword = row.get("password");
      String aName = "ManagerName";
      String aPhoneNumber = "5145145145";
      Manager newManager = new Manager(aEmail, aName, aPassword, aPhoneNumber, assetPlus);
      assetPlus.setManager(newManager);
    }
  }

  /**
   * Adds asset types specified in the Gherkin Feature file.
   *
   * @author Isbat-ul Islam
   * @param dataTable
   */
  @Given("the following asset types exist in the system \\(p7)")
  public void the_following_asset_types_exist_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String aName = row.get("name");
      Integer aExpectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      assetPlus.addAssetType(aName, aExpectedLifeSpan);
    }
  }

  /**
   * Adds specific assets defined in Gherkin feature file.
   *
   * @author Isbat-ul Islam
   * @param dataTable
   */
  @Given("the following assets exist in the system \\(p7)")
  public void the_following_assets_exist_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (var row : rows) {
      Integer aAssetNumber = Integer.parseInt(row.get("assetNumber"));
      AssetType aAssetType = AssetType.getWithName(row.get("type"));
      Date aDate = Date.valueOf(row.get("purchaseDate"));
      Integer aFloorNumber = Integer.parseInt(row.get("floorNumber"));
      Integer aRoomNumber = Integer.parseInt(row.get("roomNumber"));
      assetPlus.addSpecificAsset(aAssetNumber, aFloorNumber, aRoomNumber, aDate, aAssetType);
    }
  }

  /**
   * Add specified Tickets defined in feature file.
   *
   * @author Isbat-ul Islam
   * @param dataTable
   */
  @Given("the following tickets exist in the system \\(p7)")
  public void the_following_tickets_exist_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      Integer aID = Integer.parseInt(row.get("id"));
      String aTicketRaiserEmail = row.get("ticketRaiser");
      Date dateRaised = Date.valueOf(row.get("raisedOnDate"));
      String description = row.get("description");
      Integer assetID = Integer.parseInt(row.get("assetNumber"));
      AssetPlusFeatureSet4Controller.addMaintenanceTicket(
              aID, dateRaised, description, aTicketRaiserEmail, assetID);
    }
  }

  /**
   * Check if the number of maintenance tickets in the system is correct
   *
   * @author Neil Joe George
   * @param string
   */
  @When("the manager attempts to delete the maintenance ticket with id {string} \\(p7)")
  public void the_manager_attempts_to_delete_the_maintenance_ticket_with_id_p7(String string) {
    int aID = Integer.parseInt(string);
    AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(aID);
  }

  /**
   * Check if the maintenance tickets saved in the system are correct
   *
   * @author Mathieu Pestel
   * @author Neil Joe George
   * @param dataTable
   */
  @Then("the following tickets shall exist in the system \\(p7)")
  public void the_following_tickets_shall_exist_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> ticketTable = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> ticket : ticketTable) {
      int ticketID = Integer.parseInt(ticket.get("id"));
      MaintenanceTicket currentTicket = MaintenanceTicket.getWithId(ticketID);
      Date currentDate = Date.valueOf(ticket.get("raisedOnDate"));
      String currentDescription = currentTicket.getDescription();
      int currentAssetNumber = currentTicket.getAsset().getAssetNumber();

      assertEquals(currentDate, currentTicket.getRaisedOnDate());
      assertEquals(currentTicket.getId(), Integer.parseInt(ticket.get("id")));
      assertEquals(ticket.get("description"), currentDescription);
      assertEquals(Integer.parseInt(ticket.get("assetNumber")), currentAssetNumber);
    }
  }

  /**
   * Check if the number of maintenance tickets in the system is correct
   *
   * @author Mathieu Pestel
   * @param string
   */
  @Then("the number of maintenance tickets in the system shall be {string} \\(p7)")
  public void the_number_of_maintenance_tickets_in_the_system_shall_be_p7(String string) {
    assertEquals(Integer.parseInt(string), assetPlus.getMaintenanceTickets().size());
  }
}
