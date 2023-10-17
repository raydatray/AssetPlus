package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.model.User;

// completed by Colin Xiong (CX899 on github)

public class AssetPlusFeatureSet1Controller {

  /**
   * Updates the manager password.
   *
   * @param password The new password for the manager.
   * @return A string describing any error that occurred, or an empty string if the operation was successful.
   */
  public static String updateManager(String password) {
    //Check if password is valid
    var error = isValidPassword(password);

    if (!error.isEmpty()) {
      System.out.println(error);
      return error.trim();
    }

    try{

    } catch (RuntimeException e){
      error = e.getMessage();
    }

    return error;
  }

  /**
   * Adds a new employee or guest to the system.
   *
   * @param email       The email address of the employee or guest.
   * @param password    The password for the employee or guest.
   * @param name        The name of the employee or guest.
   * @param phoneNumber The phone number of the employee or guest.
   * @param isEmployee  A boolean indicating whether the user is an employee (true) or a guest (false).
   * @return A string describing any error that occurred, or an empty string if the operation was successful.
   */
  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    
    //Check if inputs are valid
    var error = isValidEmployeeOrGuest(email, password, name, phoneNumber,
        boolean isEmployee);

    if (!error.isEmpty()) {
      System.out.println(error);
      return error.trim();
    }
     
    try{
      if (isEmployee) {
        Employee employee = Employee.getWithEmail(email);
        if (employee != null) {
          error = "Employee already exists.";
          return error;
        }

        if (!(email.substring(email.length() - 7).equals("@ap.com"))){
          error = "Employee email domain must be @ap.com";
          return error;
        }

        Employee newEmployee = new Employee(email, password, name, phoneNumber);
      } 
    
      else {
        Guest guest = Guest.getWithEmail(email);
        if (guest != null) {
          error = "Employee already exists.";
          return error;
        }

        if (!(email.substring(email.length() - 7).equals("@ap.com"))){
          error = "Guest email domain cannot be @ap.com";
          return error;
        }

        Guest newGuest = new Guest(email, password, name, phoneNumber);
      }
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    
    return error; // empty string means operation was successful (no error)
  }

  /**
   * Updates the information of an existing employee or guest.
   *
   * @param email          The email address of the employee or guest.
   * @param newPassword    The new password for the employee or guest.
   * @param newName        The new name for the employee or guest.
   * @param newPhoneNumber The new phone number for the employee or guest.
   * @return A string describing any error that occurred, or an empty string if the operation was successful.
   */
  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    //Check if inputs are valid
    String error = isValidEmployeeOrGuest(email, newPassword, newName, newPhoneNumber);

    if (!error.isEmpty()) {
      System.out.println(error);
      return error.trim();
    }
     
    try{
        User user = User.getWithEmail(email);
        if (employee == null) {
          error = "Employee does not exist.";
          return error;
        }

        user.setPassword(newPassword);
        user.setName(newName);
        user.setPhoneNumber(newPhoneNumber);
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    
    return error; // empty string means operation was successful (no error)
  }

  /**
   * Validates the provided details for an employee or guest.
   *
   * @param email       The email address of the employee or guest.
   * @param password    The password for the employee or guest.
   * @param name        The name of the employee or guest.
   * @param phoneNumber The phone number of the employee or guest.
   * @return A string describing any validation errors, or an empty string if the details are valid.
   */
  public static String isValidEmployeeOrGuest(String email, String password, String name, String phoneNumber){
    var error = "";
    //Make sure the email is not empty or null
    if (email == null || email.trim().isEmpty()) {
      error += "Empty address cannot be empty. ";
    } 
    //Make sure the email does not contain spaces
    if (email.contains(" ")) {
      error += "Email cannot contain spaces. ";
    }
    //Make sure the email contains @
    if (email.indexOf("@") <= 0) {
      error += "Email must contain @ and cannot start with @. ";
    }
    if (email.indexOf("@") == email.lastIndexOf("@")) {
      error += "Email cannot have more than 1 \"@\". ";
    }
    if (email.indexOf("@") > (email.lastIndexOf(".") - 1)) {
      error += "Email must contain \".\" after \"@\". ";
    }
    if (email.indexOf(".") == (email.length() - 1)) {
      error += "Email cannot end with \".\". ";
    }
    if (email.equals("manager@ap.com")) {
      error += "Email cannot be manager@ap.com"
    }
    if (password == null || password.trim().isEmpty()) {
      error += "Password cannot be null or empty. ";
    }
    if (name == null){
      error += "Name cannot be null. ";
    }
    if (phoneNumber == null){
      error += "Phone number cannot be null. ";
    }

    return error.trim(); // returns string containing errors (or empty if none)
  }

  /**
   * Validates a manager password.
   *
   * @param password The password to be validated.
   * @return A string describing any validation errors, or an empty string if the password is valid.
   */
  public static String isValidPassword(pasword){
    var error = "";
     //Make sure the password is not empty
    if (password == null || password.length() == 0) {
      error += "Manager must have a password.";
    }
    //Make sure the password is at least 4 characters
    if (password.length() < 4) {
      error += "Password must be at least 4 characters. ";
    }
    //Make sure password contains one special character out of !#$
    if (!password.contains("!") & !password.contains("#") & !password.contains("$")) {
      error += "Password must contain at least one character out of !#$";
    }
    //Make sure password contains at least one upper character
    boolean upperFound = false;
    //Make sure password contains at least one lower character
    boolean lowerFound = false;
    for (int i = 0; i < password.length(); i++) {
      char character = password.charAt(i);
      if (Character.isUppercase(character)) {
        upperFound = true;
      }
      if (Character.isLowercase(character)) {
        lowerFound = true;
      }
    }
    if (!upperFound) {
      error += "Password must contain at least one upper case.";
    }
    if (!lowerFound) {
      error += "Password must contain at least one lower case.";
    }
    //Make sure the password does not have white spaces in it
    if (password.contains(" ")) {
      error += "Password must not contain any white spaces.";
    }

    return error.trim();
  }
}
