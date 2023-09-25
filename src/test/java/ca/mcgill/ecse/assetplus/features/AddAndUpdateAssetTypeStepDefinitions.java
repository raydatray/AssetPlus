package ca.mcgill.ecse.assetplus.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateAssetTypeStepDefinitions {
  @Given("the following asset types exist in the system \\(p14)")
  public void the_following_asset_types_exist_in_the_system_p14(
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

  @When("the manager attempts to add a new asset type to the system with name {string} and expected life span of {string} days \\(p14)")
  public void the_manager_attempts_to_add_a_new_asset_type_to_the_system_with_name_and_expected_life_span_of_days_p14(
      String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the manager attempts to update an asset type in the system with name {string} to have name {string} and expected life span of {string} days \\(p14)")
  public void the_manager_attempts_to_update_an_asset_type_in_the_system_with_name_to_have_name_and_expected_life_span_of_days_p14(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the number of asset types in the system shall be {string} \\(p14)")
  public void the_number_of_asset_types_in_the_system_shall_be_p14(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following asset types shall exist in the system \\(p14)")
  public void the_following_asset_types_shall_exist_in_the_system_p14(
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

  @Then("the asset type with name {string} and expected life span of {string} days shall exist in the system \\(p14)")
  public void the_asset_type_with_name_and_expected_life_span_of_days_shall_exist_in_the_system_p14(
      String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the asset type with name {string} and expected life span of {string} days shall not exist in the system \\(p14)")
  public void the_asset_type_with_name_and_expected_life_span_of_days_shall_not_exist_in_the_system_p14(
      String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the system shall raise the error {string} \\(p14)")
  public void the_system_shall_raise_the_error_p14(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
