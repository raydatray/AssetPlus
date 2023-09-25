package ca.mcgill.ecse.assetplus.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateManagerStepDefinitions {
  @Given("the following manager exists in the system \\(p6)")
  public void the_following_manager_exists_in_the_system_p6(
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

  @When("a manager with {string} attempts to update their password to {string} \\(p6)")
  public void a_manager_with_attempts_to_update_their_password_to_p6(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following {string} shall be raised \\(p6)")
  public void the_following_shall_be_raised_p6(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the manager account information will not be updated and will keep {string} and {string} \\(p6)")
  public void the_manager_account_information_will_not_be_updated_and_will_keep_and_p6(
      String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the manager account information will be updated and is now {string} and {string} \\(p6)")
  public void the_manager_account_information_will_be_updated_and_is_now_and_p6(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the number of managers in the system shall be {string} \\(p6)")
  public void the_number_of_managers_in_the_system_shall_be_p6(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
