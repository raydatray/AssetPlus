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
    var error = "";

    if (description.isEmpty() || description == null){
      error = "The description must not be empty.";
    }

    if (!error.isEmpty()) {
      return error.trim();
    }

    try {
      User ticketRaiser = User.getWithEmail(email);
      MaintenanceTicket toBeAdded = new MaintenanceTicket(id, raisedOnDate, description, assetPlus, ticketRaiser);
      boolean assetAdded = true;
      if (assetNumber != -1){
        for (SpecificAsset specificAsset : assetPlus.getSpecificAssets()){
        if (assetNumber == specificAsset.getAssetNumber()){
          assetAdded = toBeAdded.setAsset(specificAsset);
        }
      }
    }
    else {
      assetAdded = true;
    }
    if (assetAdded){
      return "";
    }
    else {
      error = "Asset unable to be added";
      return error;
    }

    } catch (Exception e) {

      var message = e.getMessage();

      if (message.startsWith("Cannot create due to duplicate id")){
        error = "Cannot add MaintenanceTicket due to duplicate id";
      }

      else if (message.startsWith("Unable to create maintenanceTicket due to assetPlus")){
        error = "Cannot add MaintenanceTicket due to AssetPlus error";
      }

      else if (message.startsWith("Unable to create raisedTicket due to ticketRaiser")){
        error = "Cannot add MaintenanceTicket due to issue with ticketRaiser";
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
      error = "The description must not be empty.";
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
      SpecificAsset newAsset = null;
      if (newAssetNumber != -1){
      for (SpecificAsset specificAsset : assetPlus.getSpecificAssets()){
        if (newAssetNumber == specificAsset.getAssetNumber()){
          newAsset = specificAsset;
        }
      }
    }

    User newTicketRaiser = User.getWithEmail(newEmail);

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
      else{
        error = "Ticket not found";
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
