package ca.mcgill.ecse.assetplus.features;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class AddTicketImageStepDefinitions {

  @Given("the following employees exist in the system \\(p5)")
  public void the_following_employees_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> employeeDataList = dataTable.asMaps();
    for (Map<String, String> data : employeeDataList) {
      AssetPlusFeatureSet1Controller.addEmployeeOrGuest(data.get("email"),data.get("name"),data.get("password"),data.get("phoneNumber"),true);
    }

  }

  @Given("the following manager exists in the system \\(p5)")
  public void the_following_manager_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Given("the following asset types exist in the system \\(p5)")
  public void the_following_asset_types_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> assetTypes = dataTable.asMaps();
    for (Map<String, String> data : assetTypes) {
      AssetPlusFeatureSet2Controller.addAssetType(data.get("name"), Integer.parseInt(data.get("expectedLifeSpan")));
    }
  }

  @Given("the following assets exist in the system \\(p5)")
  public void the_following_assets_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
      List<Map<String, String>> assets = dataTable.asMaps();
      for (Map<String, String> data : assets) {
        AssetPlusFeatureSet3Controller.addSpecificAsset(Integer.parseInt(data.get("assetNumber")),Integer.parseInt(data.get("floorNumeber")),Integer.parseInt(data.get("roomNumber")), Date.valueOf(data.get("purchaseDate")), data.get("type"));
      }
  }

  @Given("the following tickets exist in the system \\(p5)")
  public void the_following_tickets_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> tickets = dataTable.asMaps();
    for (Map<String, String> data : tickets) {
      AssetPlusFeatureSet4Controller.addMaintenanceTicket(Integer.parseInt(data.get("id")), Date.valueOf(data.get("raisedOnDate")),data.get("description"),data.get("email"),Integer.parseInt(data.get("assetNumber")));
    }
  }

  @Given("the following ticket images exist in the system \\(p5)")
  public void the_following_ticket_images_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> images = dataTable.asMaps();
    for (Map<String, String> data : images) {
      AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(data.get("imageUrl"),Integer.parseInt(data.get("ticketId")));
    }
  }

  @When("hotel staff adds an image with url {string} to the ticket with id {string} \\(p5)")
  public void hotel_staff_adds_an_image_with_url_to_the_ticket_with_id_p5(String string,
                                                                          String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the number of images in the system shall be {string} \\(p5)")
  public void the_number_of_images_in_the_system_shall_be_p5(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following ticket images shall exist in the system \\(p5)")
  public void the_following_ticket_images_shall_exist_in_the_system_p5(
          io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have an image with url {string} \\(p5)")
  public void the_ticket_with_id_shall_have_an_image_with_url_p5(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the number of images for ticket with id {string} in the system shall be {string} \\(p5)")
  public void the_number_of_images_for_ticket_with_id_in_the_system_shall_be_p5(String string,
                                                                                String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the system shall raise the error {string} \\(p5)")
  public void the_system_shall_raise_the_error_p5(String string) {
    throw new RuntimeException(string);
  }
}
