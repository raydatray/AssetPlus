package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;

public class AssetPlusFeatureSet4Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  private AssetPlusFeatureSet4Controller() {}

  // assetNumber -1 means that no asset is specified
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {
    String error = "";

    if (description.isEmpty() || description == null){
      error = "Ticket description cannot be empty";
      return error;
    }

    try {
      User ticketRaiser = User.getWithEmail(email);
      if (ticketRaiser == null){
        error = "The ticket raiser does not exist";
        return error;
      }

      SpecificAsset specificAssetToBeAdded = null;
      if (assetNumber != -1){
        for (SpecificAsset specificAsset : assetPlus.getSpecificAssets()){
        if (assetNumber == specificAsset.getAssetNumber()){
          specificAssetToBeAdded = specificAsset;
          }
        }
      }
      if (assetNumber != -1 && specificAssetToBeAdded == null){
      error = "The asset does not exist";
      return error;
    }

    MaintenanceTicket toBeAdded = new MaintenanceTicket(id, raisedOnDate, description, assetPlus, ticketRaiser);
    boolean assetAdded = toBeAdded.setAsset(specificAssetToBeAdded);

    if (assetAdded == false){
      error = "Specific asset unable to be added";
      return error;
    }

    return error;
    } catch (Exception e) {

      var message = e.getMessage();

      if (message.startsWith("Cannot create due to duplicate id")){
        error = "Ticket id already exists";
      }

      else if (message.startsWith("Unable to create maintenanceTicket due to assetPlus")){
        error = "Cannot add MaintenanceTicket due to AssetPlus error";
      }

      else if (message.startsWith("Unable to create raisedTicket due to ticketRaiser")){
        error = "The ticket raiser does not exist";
      }

      else{
        error = "Unforseen error";
      }

      return error;
    }
  }

  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    var error = "";

    if (newDescription.isEmpty() || newDescription == null){
      error = "Ticket description cannot be empty";
    }

    if (!error.isEmpty()) {
      return error.trim();
    }

    try {
      MaintenanceTicket toBeUpdated = null;

      for (MaintenanceTicket maintenanceTicket : assetPlus.getMaintenanceTickets()){
        if (id == maintenanceTicket.getId()){
          toBeUpdated = maintenanceTicket;
        }
      }
      if (toBeUpdated == null){
        error ="Ticket not found";
      }

      SpecificAsset newAsset = null;
      if (newAssetNumber != -1){
      for (SpecificAsset specificAsset : assetPlus.getSpecificAssets()){
        if (newAssetNumber == specificAsset.getAssetNumber()){
          newAsset = specificAsset;
        }
      }
    }

    if (newAsset == null && newAssetNumber != -1){
      error = "The asset does not exist";
      return error;
    }

    User newTicketRaiser = User.getWithEmail(newEmail);

    if (newTicketRaiser == null){
      error = "The ticket raiser does not exist";
      return error;
    }

      if (toBeUpdated != null){
        boolean descriptionUpdated = toBeUpdated.setDescription(newDescription);
        boolean dateUpdated = toBeUpdated.setRaisedOnDate(newRaisedOnDate);
        boolean emailUpdated = toBeUpdated.setTicketRaiser(newTicketRaiser);
        boolean assetUpdated = toBeUpdated.setAsset(newAsset);
        if (descriptionUpdated && dateUpdated && emailUpdated && assetUpdated){
          return "";
        }
        else{
          error = "Ticket not updated";
        }
      }

      return error;

    } catch (Exception e) {
      return e.getMessage();
    }
  }

  public static void deleteMaintenanceTicket(int id) {
    try {
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id);

      if (maintenanceTicket != null){
        maintenanceTicket.delete();
      }
    } catch (Exception e) {
      throw e;
    }
  }

}
