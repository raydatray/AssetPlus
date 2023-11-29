package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

// completed by Colin Xiong (CX899 on github)

public class AssetPlusFeatureSet1Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
   * Updates the manager password.
   *
   * @param password The new password for the manager.
   * @return A string describing any error that occurred, or an empty string if the operation was successful.
   */
  public static String updateManager(String password) {
    //Check if password is valid
    String error = isValidPassword(password);

    //Check if inputs are valid
    if (!error.trim().isEmpty()) {;
      return error;
    }

    Manager manager = assetPlus.getManager();

    //Check if manager exists
    if (manager == null){
      error = "Manager does not exist";
      return error;
    }

    //Update manager password
    try{
      manager.setPassword(password);
      AssetPlusPersistence.save();
      return error;
    } catch (RuntimeException e){
      error = e.getMessage();
    }

    return error; // return error, or empty string meaning operation was successful (no error)
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
    String error = isValidEmployeeOrGuest(email, password, name, phoneNumber);

    if (!error.trim().isEmpty()) {
      return error;
    }

    
    if (isEmployee) {
      //Check if email domain is @ap.com
      if (!(email.endsWith("@ap.com"))){
        error = "Email domain must be @ap.com";
        return error;
      }

      User employee = Employee.getWithEmail(email);
      //Check if employee already exists
      if (employee != null) {
        error = "Email already linked to an employee account";
        return error;
      }

      try{
        Employee newEmployee = new Employee(email, name, password, phoneNumber, assetPlus);
        AssetPlusPersistence.save();
        return error;
      } catch (RuntimeException e) {
        error = e.getMessage();
      }

    } else {
        User guest = Guest.getWithEmail(email);
        //Check if guest already exists
        if (guest != null) {
          error = "Email already linked to a guest account";
          return error;
        }

        //Check if email domain is @ap
        if (email.endsWith("@ap.com")){
          error = "Email domain cannot be @ap.com";
          return error;
        }
        try {
          Guest newGuest = new Guest(email, name, password, phoneNumber, assetPlus);
          AssetPlusPersistence.save();
          return error;
        } catch (RuntimeException e){
          error = e.getMessage();
        }      
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

    try {
      User user = User.getWithEmail(email);

      //Check if user exists
      if (user == null) {
        error = "Employee does not exist.";
        return error;
      }

      user.setPassword(newPassword);
      user.setName(newName);
      user.setPhoneNumber(newPhoneNumber);

      AssetPlusPersistence.save();
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
  private static String isValidEmployeeOrGuest(String email, String password, String name, String phoneNumber){
    var error = "";
    //Make sure the email is not empty or null
    if (email == null || email.trim().isEmpty()) {
      return "Email cannot be empty";
    }
    //Make sure the email does not contain spaces
    if (email.contains(" ")) {
      error += "Email must not contain any spaces";
    }
    if (!email.contains("@")){
      error += "Invalid email";
    }
    //Make sure the email contains @
    if (email.indexOf("@")==0) {
      error += "Invalid email";
    }
    if (email.indexOf("@")+1 == email.lastIndexOf(".")) {
      error += "Invalid email";
    }
    if (email.indexOf("@") != email.lastIndexOf("@")) {
      error += "Invalid email";
    }
    if (email.indexOf("@") > (email.lastIndexOf(".") - 1)) {
      error += "Invalid email";
    }
    if (email.endsWith(".")) {
      error += "Invalid email";
    }
    if (email.equals("manager@ap.com")) {
      error += "Email cannot be manager@ap.com";
    }
    if (password == null || password.trim().isEmpty()) {
      error += "Password cannot be empty";
    }

    return error.trim(); // returns string containing errors (or empty if none)
  }

  /**
   * Validates a manager password.
   *
   * @param password The password to be validated.
   * @return A string describing any validation errors, or an empty string if the password is valid.
   */
  private static String isValidPassword(String password){
    var error = "";
     //Make sure the password is not empty
    if (password == null || password.length() == 0) {
      error = "Password cannot be empty";
      return error;
    }
    //Make sure the password is at least 4 characters
    if (password != null && password.length() < 4) {
      error = "Password must be at least four characters long";
      return error;
    }
    //Make sure password contains one special character out of !#$
    if (!password.contains("!") && !password.contains("#") && !password.contains("$")) {
      error = "Password must contain one character out of !#$";
      return error;
    }
    //Make sure password contains at least one upper character
    boolean upperFound = false;
    //Make sure password contains at least one lower character
    boolean lowerFound = false;
    for (int i = 0; i < password.length(); i++) {
      char character = password.charAt(i);
      if (Character.isUpperCase(character)) {
        upperFound = true;
      }
      if (Character.isLowerCase(character)) {
        lowerFound = true;
      }
    }
    if (!upperFound) {
      error = "Password must contain one upper-case character";
      return error;
    }
    if (!lowerFound) {
      error = "Password must contain one lower-case character";
      return error;
    }
    //Make sure the password does not have white spaces in it
    if (password.contains(" ")) {
      error = "Password must not contain any white spaces.";
      return error;
    }

    return "";
  }

  public static List<TOUser> getUsers() {

    List<TOUser> TOUserList = new ArrayList<>();
    try {
      List<Employee> employees = assetPlus.getEmployees();
      List<Guest> guests = assetPlus.getGuests();
      List<User> users = new ArrayList<>();
      for (Employee employee : employees){
        users.add(employee);
      }
      for (Guest guest : guests){
        users.add(guest);
      }
			for (User user : users) {
        String name;
        String phoneNumber;
        name = user.getName();
        phoneNumber = user.getPhoneNumber();

      TOUser transferUser = new TOUser(user.getEmail(), name, phoneNumber);

      TOUserList.add(transferUser);
    }
			AssetPlusPersistence.save();
		} catch (Exception e) {
			throw e;
		}
    return TOUserList;
}
}
