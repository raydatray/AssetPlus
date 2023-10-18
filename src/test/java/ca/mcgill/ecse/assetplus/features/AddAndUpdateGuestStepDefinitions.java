package ca.mcgill.ecse.assetplus.features;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateGuestStepDefinitions {
  private String error;

  /**
   * @author Eric Zhu
   */
  @Given("the following guests exist in the system \\(p10)")
  public void the_following_guests_exist_in_the_system_p10(
      io.cucumber.datatable.DataTable dataTable) {
    // Converts the dataTable into a list of maps
    List<Map<String, String>> rows = dataTable.asMaps();

    // Iterate through each row of the dataTable and creates a new Guest for each row
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String phoneNumber = row.get("phoneNumber");
      new Guest(email, name, password, phoneNumber, AssetPlusApplication.getAssetPlus());
    }
  }

  /**
   * 
   * @author Muhammad Hamamd
   */
  @Given("the following manager exists in the system \\(p10)")
  public void the_following_manager_exists_in_the_system_p10(
      io.cucumber.datatable.DataTable dataTable) {
    // Converts the dataTable into a list of maps
    List<Map<String, String>> rows = dataTable.asMaps();

    // Iterate through each row of the dataTable and creates a new Manager for each row
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String phoneNumber = row.get("phoneNumber");
      new Manager(email, name, password, phoneNumber, AssetPlusApplication.getAssetPlus());
    }
  }

  /**
   * @author Qasim Li
   */
  @When("a new guest attempts to register with {string}, {string}, {string}, and {string} \\(p10)")
  public void a_new_guest_attempts_to_register_with_and_p10(String string, String string2,
      String string3, String string4) {
    // Calls the controller to add a guest to the AssetPlus object and stores the error code
    error =
        AssetPlusFeatureSet1Controller.addEmployeeOrGuest(string, string2, string3, string4, false);
  }

  /**
   * @author Steve
   */
  @When("the guest with {string} attempts to update their account information to {string}, {string}, and {string} \\(p10)")
  public void the_guest_with_attempts_to_update_their_account_information_to_and_p10(String string,
      String string2, String string3, String string4) {
    // Updates a guest to the AssetPlus object and stores the error code
    error = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(string, string2, string3, string4);
  }

  /**
   * @author Eric Zhu
   */
  @Then("the following {string} shall be raised \\(p10)")
  public void the_following_shall_be_raised_p10(String string) {
    // Checks if the error code contains the expected error code as a substring
    assertTrue(error.contains(string));
  }

  /**
   * @author Qasim Li, Bohan Wang
   */
  @Then("the number of guests in the system shall be {string} \\(p10)")
  public void the_number_of_guests_in_the_system_shall_be_p10(String string) {
    // Retrieves the number of guests in the AssetPlus object and typecasts it into a String
    Integer numberOfGuests = AssetPlusApplication.getAssetPlus().getGuests().size();
    Integer expectedSize = Integer.parseInt(string);

    // Checks if the number of guests in AssetPlus matches the expected number
    assertEquals(numberOfGuests, expectedSize);
  }

  /**
   * @author Christopher
   */
  @Then("a new guest account shall exist with {string}, {string}, {string}, and {string} \\(p10)")
  public void a_new_guest_account_shall_exist_with_and_p10(String string, String string2,
      String string3, String string4) {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    // Retrieves the list of guests currently in the AssetPlus object
    List<Guest> guests = assetPlus.getGuests();
    Guest newGuest = null;

    // Iterates through all guests, stores the guest with the specified email into newGuest
    for (Guest guest : guests) {
      if (guest.getEmail().equals(string)) {
        newGuest = guest;
      }
    }

    // Checks if newGuest is null, i.e. if the email matches a guest in the system
    assertNotNull(newGuest);

    // Checks if the guest attributes match the expected values
    assertEquals(string, newGuest.getEmail());
    assertEquals(string2, newGuest.getPassword());
    assertEquals(string3, newGuest.getName());
    assertEquals(string4, newGuest.getPhoneNumber());
  }

  /**
   * @author Connor Tate
   */
  @Then("the following guests shall exist in the system \\(p10)")
  public void the_following_guests_shall_exist_in_the_system_p10(
      io.cucumber.datatable.DataTable dataTable) {
    // Converts the dataTable into a list of maps
    List<Map<String, String>> rows = dataTable.asMaps();

    // Iterates through all rows in dataTable to verify if a guest exists with each email
    for (var row : rows) {
      String email = row.get("email");
      // Verifies that the user with that email exists
      assertTrue(User.getWithEmail(email) != null);
    }
  }

  /**
   * @author Steve
   */
  @Then("their guest account information will be updated and is now {string}, {string}, {string}, and {string} \\(p10)")
  public void their_guest_account_information_will_be_updated_and_is_now_and_p10(String string,
      String string2, String string3, String string4) {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    Guest currentGuest = null;

    // Iterates through all guests, stores the guest with the specified email into newGuest
    for (Guest guest : assetPlus.getGuests()) {
      if (string.equals(guest.getEmail())) {
        currentGuest = guest;
      }
    }

    // Checks if newGuest is null, i.e. if the email matches a guest in the system
    assertNotNull(currentGuest);
    // Checks if the guest attributes match the expected values
    assertEquals(string, currentGuest.getEmail());
    assertEquals(string2, currentGuest.getPassword());
    assertEquals(string3, currentGuest.getName());
    assertEquals(string4, currentGuest.getPhoneNumber());
  }
}
