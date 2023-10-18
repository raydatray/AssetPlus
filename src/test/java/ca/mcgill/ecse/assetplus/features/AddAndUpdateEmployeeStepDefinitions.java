package ca.mcgill.ecse.assetplus.features;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ca.mcgill.ecse.assetplus.model.*;

import java.util.List;
import java.util.Map;

public class AddAndUpdateEmployeeStepDefinitions {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error;  

  /**
   * @author Jatin Patel and Anastasiia Nemyrovska
   */
  @Given("the following employees exist in the system \\(p11)")
  public void the_following_employees_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> employeesToAdd = dataTable.asMaps();
    for (Map<String, String> employee : employeesToAdd) {
      String email = employee.get("email");
      String name = employee.get("name");
      String password = employee.get("password");
      String phoneNumber = employee.get("phoneNumber");
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
  }

  /** 
   * @author Jatin Patel and Anastasiia Nemyrovska
   */
  @Given("the following manager exists in the system \\(p11)")
  public void the_following_manager_exists_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> managerToAdd = dataTable.asMaps();
    for (Map<String, String> manager : managerToAdd) {
      String email = manager.get("email");
      String name = manager.get("name");
      String password = manager.get("password");
      String phoneNumber = manager.get("phoneNumber");
      new Manager(email, name, password, phoneNumber, assetPlus);
    }
  }

  /**
   * @author Jatin Patel and Anastasiia Nemyrovska
   */
  @When("a new employee attempts to register with {string}, {string}, {string}, and {string} \\(p11)")
  public void a_new_employee_attempts_to_register_with_and_p11(String string, String string2,
      String string3, String string4) {

    callController(addEmployeeOrGuest(string, string2, string3, string4,true));
  }

  /**
   * @author Pei Yan Geng, Dmytro Martyniuk and Laurence Perreault
   */
  @When("the employee with {string} attempts to update their account information to {string}, {string}, and {string} \\(p11)")
  public void the_employee_with_attempts_to_update_their_account_information_to_and_p11(
      String string, String string2, String string3, String string4) {
    // string = email; string2 = newPassword; string3 = newName; string4 = newPhoneNumber;
    callController(updateEmployeeOrGuest(string, string2, string3, string4)); 
  }
  /**
   * @author Pei Yan Geng, Dmytro Martyniuk and Laurence Perreault
   */
  @Then("the following {string} shall be raised \\(p11)")
  public void the_following_shall_be_raised_p11(String string) {
    assertTrue(error.contains(string));
  }
  /**
   * @author Pei Yan Geng, Dmytro Martyniuk and Laurence Perreault
   */
  @Then("the number of employees in the system shall be {string} \\(p11)")
  public void the_number_of_employees_in_the_system_shall_be_p11(String string) {
    
    List<Employee> employees = assetPlus.getEmployees();

    assertEquals( (Integer) employees.size(), Integer.parseInt(string));
  }
  
   /**
   * @author Marc-Antoine Nadeau & Behrad Rezaie
   */
  @Then("a new employee account shall exist with {string}, {string}, {string}, and {string} \\(p11)")
  public void a_new_employee_account_shall_exist_with_and_p11(String string, String string2,
      String string3, String string4) {
    
    //Checks an employee with given email exists
    assertTrue(Employee.hasWithEmail(string));
    //Checks other employee attributes
    Employee existingEmployee = (Employee) User.getWithEmail(string);
    assertEquals(string2, existingEmployee.getPassword());
    assertEquals(string3, existingEmployee.getName());
    assertEquals(string4,existingEmployee.getPhoneNumber());

  }
  
  /**
   * @author Marc-Antoine Nadeau & Behrad Rezaie
   */
  @Then("their employee account information will be updated and is now {string}, {string}, {string}, and {string} \\(p11)")
  public void their_employee_account_information_will_be_updated_and_is_now_and_p11(String string,
      String string2, String string3, String string4) {
    
    assertTrue(Employee.hasWithEmail(string));    
    Employee updatedEmployeeWithKnownEmailAddress = (Employee) User.getWithEmail(string);
    assertEquals(string2, updatedEmployeeWithKnownEmailAddress.getPassword());
    assertEquals(string3, updatedEmployeeWithKnownEmailAddress.getName());
    assertEquals(string4, updatedEmployeeWithKnownEmailAddress.getPhoneNumber());
  }

  /**
   * @author Marc-Antoine Nadeau & Behrad Rezaie
   */
  @Then("the following employees shall exist in the system \\(p11)")
  public void the_following_employees_shall_exist_in_the_system_p11(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> employeeData = dataTable.asMaps();
    //dataTable should be converted to this format:
    //[
    //  { "email"="jeff@ap.com", "password"="pass1", "name"="Jeff", "phoneNumber"="(555)555-5555" },
    //  { "email"="john@ap.com", "password"="pass2", "name"="John", "phoneNumber"="(444)444-4444" }
    //]
      for (Map<String, String> data : employeeData) {
        // Get info from each sub-directory
        String email = data.get("email");
        String name = data.get("name");
        String password = data.get("password");
        String phoneNumber = data.get("phoneNumber");

        //Compares it to what we have in the system
        Employee employee = (Employee) User.getWithEmail(email);
        assertNotNull(employee, "Employee with" + email + " was not found." );
        assertEquals(name, employee.getName());
        assertEquals(password, employee.getPassword());
        assertEquals(phoneNumber, employee.getPhoneNumber());
    }
  }
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
    }
  }
}

