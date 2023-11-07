package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;

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

    return error; // If there are any problems, an error message will be returned, if not an empty string will be returned
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

      return error; // Return the error message

    } catch (Exception e) { // Catch any exceptions that would crash the system
      return e.getMessage(); // Return the exception message
    }
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
    } catch (Exception e) { //Catch any exceptions
      throw e; //Throw the exception. This is required to delete any tickets that do not exist
    }
  }

  // need to do input verification that ticketId exists, that staffMemberEmail exists, that timeToResolve exists as a TimeEstimate, that priority exists as a PriorityLevel, that requiresManagerApproval is only true or false since it will be a boolean, and that managerEmail is valid
  public static String AssignHotelStaffToMaintenanceTicket(String ticketId, String staffMemberEmail, String timeToResolve, String priority, String requiresManagerApproval, String managerEmail) {
    return "String";
  }

  // check that ticketId exists
  public static String StartWorkOnMaintenanceTicket(String ticketId) {
    return "String";
  }

  // check that ticketId exists
  public static String CompleteWorkOnMaintenanceTicket(String ticketId) {
    return "String";
  }

  // check that ticketId exists
  public static String ApproveWorkOnMaintenanceTicket(String ticketId) {
    return "String";
  }

  //check that ticketId exists, make sure date is valid, make sure description isnt empty, then it will always be manager who is the noteTaker when you create the MaintenanceNote
  public static String DisapproveWorkOnMaintenanceTicket(String ticketId, String date, String description) {
    return "String";
  }
}
