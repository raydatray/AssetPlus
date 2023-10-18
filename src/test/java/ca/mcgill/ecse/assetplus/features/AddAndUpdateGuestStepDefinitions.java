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
    List<Map<String, String>> rows = dataTable.asMaps();
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
    List<Map<String, String>> rows = dataTable.asMaps();
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
    error = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(string, string2, string3, string4,
        false);
  }

  /**
   * @author Steve
   */
  @When("the guest with {string} attempts to update their account information to {string}, {string}, and {string} \\(p10)")
  public void the_guest_with_attempts_to_update_their_account_information_to_and_p10(String string,
      String string2, String string3, String string4) {
    error = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(string, string2, string3, string4);
  }

  /**
   * @author Eric Zhu
   */
  @Then("the following {string} shall be raised \\(p10)")
  public void the_following_shall_be_raised_p10(String string) {
    assertTrue(error.contains(string));
  }

  /**
   * @author Qasim Li, Bohan Wang
   */
  @Then("the number of guests in the system shall be {string} \\(p10)")
  public void the_number_of_guests_in_the_system_shall_be_p10(String string) {
    Integer numberOfGuests = AssetPlusApplication.getAssetPlus().getGuests().size();
    Integer expectedSize = Integer.parseInt(string);

    assertEquals(numberOfGuests, expectedSize);
  }

  /**
   * @author Christopher
   */
  @Then("a new guest account shall exist with {string}, {string}, {string}, and {string} \\(p10)")
  public void a_new_guest_account_shall_exist_with_and_p10(String string, String string2,
      String string3, String string4) {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    List<Guest> guests = assetPlus.getGuests();
    Guest newGuest = null;

    for (Guest guest : guests) {
      if (guest.getEmail().equals(string)) {
        newGuest = guest;
      }
    }

    assertNotNull(newGuest);
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
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      assertTrue(User.getWithEmail(email) != null); // Verifies that the user with that email exists
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

    for (Guest guest : assetPlus.getGuests()) {
      if (string.equals(guest.getEmail())) {
        currentGuest = guest;
      }
    }

    assertNotNull(currentGuest);
    assertEquals(string, currentGuest.getEmail());
    assertEquals(string2, currentGuest.getPassword());
    assertEquals(string3, currentGuest.getName());
    assertEquals(string4, currentGuest.getPhoneNumber());
  }
}
