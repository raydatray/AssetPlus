package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.HotelStaff; // import
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager; // import
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel; // import
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate; // import

public class AssetPlusFeatureSet4Controller {
  // Done by Sebastian Reinhardt, SReinhardt283 on GitHub
  /**
   * The AssetPlus application we are using
   */
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  private AssetPlusFeatureSet4Controller() {}
  /**
   * Adds a MaintenanceTicket to the AssetPlus application
   * @param id The id of MaintenanceTicket that is to be added
   * @param raisedOnDate The date on which the MaintenanceTicket is raised on
   * @param description The description of the issue for whihc the MaintenanceTicket is raised
   * @param email The email of the User who raised the issue
   * @param assetNumber The id of the asset for which the ticket is raised, if it is -1 then no asset is specified
   * @return A string representing the error message, or an empty string if there were no errors
   */
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {
    String error = ""; // Initializing the error message

    if (description.isEmpty() || description == null){ // Validating that the description is not empty
      error = "Ticket description cannot be empty"; // Corresponding error message if the description is empty
      return error; // Returning the error message
    }

    try {
      User ticketRaiser = User.getWithEmail(email); // Gets the user who raised the MaintenanceTicket
      if (ticketRaiser == null){ // Checking that the user exists
        error = "The ticket raiser does not exist"; // Corresponding error message if the ticketRaiser does not exist
        return error; // Returning the error message
      }

      SpecificAsset specificAssetToBeAdded = null; // Initializing the asset that is to be added
      if (assetNumber != -1){ // Checking if the asset number is -1
        for (SpecificAsset specificAsset : assetPlus.getSpecificAssets()){ // If the asset number is not -1, then iterate through the assets in the application
          if (assetNumber == specificAsset.getAssetNumber()){ // Checking if the asset number to be added corresponds to an asset in the application
            specificAssetToBeAdded = specificAsset; // If the asset number matches the asset to be added, then set the specificAssetToBeAdded to the matching asset
          }
        }
        }
        
      if (assetNumber != -1 && specificAssetToBeAdded == null){ // Checking that if asset number is not -1 and null, means that the asset to be added was not found
      error = "The asset does not exist"; // Corresponding error message if the asset to be added does not exist
      return error; // Returning the error message
      }

      MaintenanceTicket toBeAdded = new MaintenanceTicket(id, raisedOnDate, description, assetPlus, ticketRaiser); // Creating the MaintenanceTicket
      boolean assetAdded = toBeAdded.setAsset(specificAssetToBeAdded); // Adding the asset associated with the ticket

      if (assetAdded == false){ // Checking whether the appropriate asset could be added
        error = "Specific asset unable to be added"; // Corresponding error message if the asset was not able to be added
        return error; // Returning the error message
      }

      AssetPlusPersistence.save();

    } catch (Exception e) {

      var message = e.getMessage(); // Getting the error message of the exception, which are possibly returned in the creating of the MaintenanceTicket

      if (message.startsWith("Cannot create due to duplicate id")){ // Checking the error message
        error = "Ticket id already exists"; // Rewriting the corresponding error message
      }

      else if (message.startsWith("Unable to create maintenanceTicket due to assetPlus")){ // Checking the error message
        error = "Cannot add MaintenanceTicket due to AssetPlus error"; // Rewriting the corresponding error message
      }

      else if (message.startsWith("Unable to create raisedTicket due to ticketRaiser")){ // Checking the error message
        error = "The ticket raiser does not exist"; // Rewriting the corresponding error message
      }

      else{
        error = "Unforseen error"; // If there is an unforseen error
      }

      return error; // Return the error raised
    }
    
    return error; // If there are any problems, an error message will be returned, if not an empty string will be returned

  }
  /**
   * Updating a MaintenanceTicket which already exists in the application
   * @param id The id of the MaintenanceTicket that is to be updated
   * @param newRaisedOnDate The date on which the MaintenanceTicket is updated
   * @param newDescription The new description for the updated MaintenanceTicket
   * @param newEmail The email address of the user who updated the MaintenanceTicket
   * @param newAssetNumber The id of the new asset which is to be addressed on the MaintenanceTicket, if it is -1 then no asset is specified
   * @return A string representing the error message, or an empty string if there were no errors
   */
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    var error = ""; // Initializing the error message

    if (newDescription.isEmpty() || newDescription == null){ // Validating that the description is not empty
      error = "Ticket description cannot be empty"; // Corresponding error message if the description is empty
      return error; // Returning the error message
    }

    try {
      MaintenanceTicket toBeUpdated = null; // Initializing the ticket that is to be updated

      for (MaintenanceTicket maintenanceTicket : assetPlus.getMaintenanceTickets()){ // Iterating through all the MaintenanceTickets in the system
        if (id == maintenanceTicket.getId()){ // Checking to see if the id of the MaintenanceTicket to be added matches any of the tickets in the system id
          toBeUpdated = maintenanceTicket; // If there is a matching id, set toBeUpdated to the corresponding MaintenanceTicket
        }
      }
      if (toBeUpdated == null){ // Checking if a ticket was found
        error ="Ticket not found"; // Corresponding error message
        return error; // Return the error message
      }

      SpecificAsset newAsset = null; // Initializing the asset that is to be updated
      if (newAssetNumber != -1){ // Checking if the asset number is -1
        for (SpecificAsset specificAsset : assetPlus.getSpecificAssets()){ // If the asset number is not -1, then iterate through the assets in the application
          if (newAssetNumber == specificAsset.getAssetNumber()){ // Checking if the asset number to be updated corresponds to an asset in the application
            newAsset = specificAsset; // If the asset number matches the asset to be added, then set the toBeUpdated to the matching asset
          }
        }
      }

      if (newAsset == null && newAssetNumber != -1){ // Checking that if asset number is not -1 and null, means that the asset to be updated was not found
        error = "The asset does not exist"; // Corresponding error message if the asset to be added does not exist
        return error; // Return the error message
      }

      User newTicketRaiser = User.getWithEmail(newEmail); // Gets the user who raised the MaintenanceTicket

      if (newTicketRaiser == null){ // Checking that the user exists
        error = "The ticket raiser does not exist"; // Corresponding error message if the newTicketRaiser does not exist
        return error; // Return the error message
      }

      if (toBeUpdated != null){ // Checking that the MaintenanceTicket to be updated was assigned
        boolean descriptionUpdated = toBeUpdated.setDescription(newDescription); // Setting the new description
        boolean dateUpdated = toBeUpdated.setRaisedOnDate(newRaisedOnDate); // Setting the new raisedOnDate
        boolean emailUpdated = toBeUpdated.setTicketRaiser(newTicketRaiser); // Setting the new TicketRaiser
        boolean assetUpdated = toBeUpdated.setAsset(newAsset); // Setting the new asset
        if (descriptionUpdated && dateUpdated && emailUpdated && assetUpdated){ // Checking that all fields were updated
          return ""; // Return empty string to signify no errors
        }
        else{
          error = "Ticket not updated"; // Corresponding error message if one of the fields was not updated
        }
      }

      AssetPlusPersistence.save();

    } catch (Exception e) { // Catch any exceptions that would crash the system
      return e.getMessage(); // Return the exception message
    }

    return error; // Return the error message

  }

  /**
   * Delete a MaintenanceTicket from the system
   * @param id // The id of the MaintenanceTicket to be deleted
   */
  public static void deleteMaintenanceTicket(int id) {
    try {
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id); // Get the MaintenanceTicket with the corresponding id

      if (maintenanceTicket != null){ // Check if the MaintenanceTicket exists
        maintenanceTicket.delete(); //Delete the MaintenanceTicket
      }

      AssetPlusPersistence.save();
    } catch (Exception e) { //Catch any exceptions
      throw e; //Throw the exception. This is required to delete any tickets that do not exist
    }
  }

  /**
   * Iteration 3 controller methods
   */

   /**
   * Assigns a hotel staff member to a maintenance ticket.
   *
   * @author Aurelia Bouliane
   * @param ticketId               The ID of the maintenance ticket.
   * @param staffMemberEmail       The email of the staff member to be assigned.
   * @param timeToResolve          The estimated time to resolve the ticket.
   * @param priority               The priority level of the ticket.
   * @param requiresManagerApproval Whether the ticket requires manager approval.
   * @param managerEmail           The email of the manager.
   * @return An error message if any input validation fails, or an empty string on success.
   */
  public static String AssignHotelStaffToMaintenanceTicket(int ticketId, String staffMemberEmail, TimeEstimate timeToResolve, PriorityLevel priority, boolean requiresManagerApproval, String managerEmail) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    String error = "";

    // Ensure ticket is not null
    if (ticket == null) {
      error = "Maintenance ticket does not exists.";
      return error;
    }

    // Check if staffMemberEmail exists
    if (!(User.getWithEmail(staffMemberEmail) instanceof HotelStaff)) {
      error = "Staff to assign does not exist";
    }

    // If all input is valid, proceed with assigning the staff member to the maintenance ticket
    User managerInitial = User.getWithEmail(managerEmail);
    Manager manager = (Manager) managerInitial;
    User hotelStaffInitial = User.getWithEmail(staffMemberEmail);
    Manager hotelStaff = (Manager) hotelStaffInitial;
    try {
      ticket.assign(hotelStaff, timeToResolve, priority, requiresManagerApproval, manager); // Check if this is good please!
      AssetPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return error;
}

  /**
   * Starts work on a maintenance ticket.
   * 
   * @author Alexander Liu
   * @param ticketId The ID of the maintenance ticket.
   * @return An error message if the ticket doesn't exist or if starting the ticket fails, or an empty string on success.
   */
  // check that ticketId exists
  public static String StartWorkOnMaintenanceTicket(String ticketId) { // ticketId should be int
    // Input validation
    try {
      Integer.parseInt(ticketId);
    } catch (Exception e) {
      return "Invalid input";
    }

    MaintenanceTicket targetTicket = null;

    for (MaintenanceTicket ticket: assetPlus.getMaintenanceTickets()) {
      if (ticket.getId() == Integer.parseInt(ticketId)) {
        targetTicket = ticket;
      }
    }

    if (targetTicket == null) {
      return "The maintenance ticket you are looking for does not exist";
    } else {
      try {
        targetTicket.startTicket();
        AssetPlusPersistence.save();
        return "";
      } catch (RuntimeException e) {
        return e.getMessage();
      }
    }
  }

   /**
   * Completes work on a maintenance ticket.
   *
   * @author Alexander Liu
   * @param ticketId The ID of the maintenance ticket.
   * @return An error message if the ticket doesn't exist or if completing the ticket fails, or an empty string on success.
   */
  // check that ticketId exists
  public static String CompleteWorkOnMaintenanceTicket(String ticketId) { // ticketID should be int
    // Input validation
    try {
      Integer.parseInt(ticketId);
    } catch (Exception e) {
      return "Invalid input";
    }

    MaintenanceTicket targetTicket = null;

    for (MaintenanceTicket ticket: assetPlus.getMaintenanceTickets()) {
      if (ticket.getId() == Integer.parseInt(ticketId)) {
        targetTicket = ticket;
      }
    }

    if (targetTicket == null) {
      return "The maintenance ticket you are looking for does not exist";
    } else {
      try {
        targetTicket.closeTicket();
        AssetPlusPersistence.save();
        return "";
      } catch (RuntimeException e) {
        return e.getMessage();
      }
    }
  }

  /**
   * Approves work on a maintenance ticket.
   *
   * @author Alexander Liu
   * @param ticketId The ID of the maintenance ticket.
   * @return An error message if the ticket doesn't exist or if approving the ticket fails, or an empty string on success.
   */
  // check that ticketId exists
  public static String ApproveWorkOnMaintenanceTicket(String ticketId) { // ticketID should be int
    // Input validation
    try {
      Integer.parseInt(ticketId);
    } catch (Exception e) {
      return "Invalid input";
    }

    MaintenanceTicket targetTicket = null;

    for (MaintenanceTicket ticket: assetPlus.getMaintenanceTickets()) {
      if (ticket.getId() == Integer.parseInt(ticketId)) {
        targetTicket = ticket;
      }
    }

    if (targetTicket == null) {
      return "The maintenance ticket you are looking for does not exist";
    } else {
      try {
        targetTicket.approveTicket();
        AssetPlusPersistence.save();
        return "";
      } catch (RuntimeException e) {
        return e.getMessage();
      }
    }
  }

  // TODO: DOUBLE CHECK THIS!!!
  /**
   * Disapproves work on a maintenance ticket and adds a note.
   *
   * @author Aurelia Bouliane and Ray Liu
   * @param ticketId    The ID of the maintenance ticket.
   * @param date        The date of the note.
   * @param description The description of the note.
   * @return An error message if input validation fails or if disapproving the ticket fails, or an empty string on success.
   */
  public static String DisapproveWorkOnMaintenanceTicket(int ticketId, Date date, String description) {
    String error = "";
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);

    // Ensure ticket is not null
    if (ticket == null) {
      error = "Maintenance ticket does not exist.";
      return error;
    } else if (description == null || description.trim().isEmpty()) {
      error = "Ticket description cannot be empty."; // if check return is good message
      return error;
    }

    try {
      ticket.disapproveTicket( new MaintenanceNote(date, description, ticket, assetPlus.getManager()));
      //Add a ticket note above
      AssetPlusPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }
    
    return error;
  }
}
