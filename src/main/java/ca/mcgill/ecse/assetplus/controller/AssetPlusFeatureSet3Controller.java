package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet3Controller {

/**
     * Adds a new Specific Asset with the specified asset number, floor number, room number, purchase data and asset type name
     *
     * @param assetNumber                  The number of the Asset. Must be greater or equal to 1.
     * @param floorNumber                  The floor on which the Asset is placed. Must be greater or equal to 0.
     * @param roomNumber                   The room in which the Asset is placed. Must be greater or equal to -1. -1 specifies no room.
     * @param purchaseDate                 The purchase date of the Asset.
     * @param asseTypeName                 The type of the Asset. Must be of an existing type.
     * @return                      A message indicating the success or failure of the operation.
     */

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {
        String error = "";
        //Checks if assetNumber is greater or equal to one
        if (assetNumber < 1) {
          error = "The asset number shall not be less than 1";
          return error;
        }

        //Checks if floorNumber is greater or equal to one
        if (floorNumber < 0) {
          error = "The floor number shall not be less than 0";
          return error;
        }

        //Checks if roomNumber is greater than or equal to -1
        if (roomNumber < -1) {
          error = "The room number shall not be less than -1";
          return error;
        }

        try {
          //Error caught -> Specific asset has to be an asset in the first place
          AssetType assetToAddTo = AssetType.getWithName(assetTypeName);
          if (assetToAddTo == null){
            error = "The asset type does not exist";
            return error;
          }

          //Add Asset
          SpecificAsset mySpecificAsset = new SpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetPlus, assetToAddTo);
         
          AssetPlusPersistence.save();
        } catch (Exception e) {
          error = e.getMessage();
        }
        
        return error;
  }

  /**
     * Updates a Specific Asset with the same asset number, new floor number, new room number, new purchase data and new asset type name.
     *
     * @param assetNumber                  The number of the Asset. Must be greater or equal to 1.
     * @param newFloorNumber               The new floor on which the Asset is placed. Must be greater or equal to 0.
     * @param newRoomNumber                The new room in which the Asset is placed. Must be greater or equal to -1. -1 specifies no room.
     * @param newPurchaseDate              The new purchase date of the Asset.
     * @param newAsseTypeName              The new type of the Asset. Must be of an existing type.
     * @return                      A message indicating the success or failure of the operation.
     */

  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        String error = "";
        //Checks if assetNumber is greater or equal to one
        if (assetNumber < 1) {
          error = "The asset number shall not be less than 1";
          return error;
        }

        //Checks if floorNumber is greater or equal to one
        if (newFloorNumber < 0) {
          error = "The floor number shall not be less than 0";
          return error;
        }

        //Checks if roomNumber is greater than or equal to -1
        if (newRoomNumber < -1) {
          error = "The room number shall not be less than -1";
          return error;
        }

        //Error caught -> you have to check within assets, if this specificasse exists

        try {
          //Case 1: Retain the same assetNumber, 
          AssetType assetTypeToUpdate = AssetType.getWithName(newAssetTypeName);
          if (assetTypeToUpdate == null){
            error = "The asset type does not exist";
            return error;
          }

          SpecificAsset specificAssetToUpdate = SpecificAsset.getWithAssetNumber(assetNumber);
          if (specificAssetToUpdate == null) {
            error = "The asset number does not exist";
            return error;
          }

          specificAssetToUpdate.setFloorNumber(newFloorNumber);
          specificAssetToUpdate.setRoomNumber(newRoomNumber);
          specificAssetToUpdate.setPurchaseDate(newPurchaseDate);
          specificAssetToUpdate.setAssetType(assetTypeToUpdate);
          
          AssetPlusPersistence.save();
        } catch (Exception e) {
          error = e.getMessage();
        }
      
        return error;
    }

  /**
     * Deletes a Specific Asset with the specified asset number.
     *
     * @param assetNumber The number of the Specific Asset to be deleted. Must not be less than 1.
     * @throws Exception If the Asset Type with the specified name does not exist.
     */  
  public static void deleteSpecificAsset(int assetNumber) {
    //Delete a Specific Asset by assetNumber
     try {
      SpecificAsset assetToDelete = SpecificAsset.getWithAssetNumber(assetNumber); // Get the Specific Asset with the corresponding asset number

      List<MaintenanceTicket> ticketsToDelete = new ArrayList<MaintenanceTicket>();

      for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()) {
        if (ticket.getAsset() == assetToDelete) {
          ticketsToDelete.add(ticket);
        }
      }

      for (MaintenanceTicket ticket : ticketsToDelete) {
        ticket.delete();
      }

      if (assetToDelete != null){ // Check if the Specific Asset exists
        assetToDelete.delete(); //Delete the Specific Asset
      }

      AssetPlusPersistence.save();
    } catch (Exception e) { //Catch any exceptions
      throw e; //Throw the exception. This is required to delete any specific assets that do not exist
    }
  }

  public static List<TOSpecificAsset> getSpecificAssets() {

    List<TOSpecificAsset> TOSpecificAssetList = new ArrayList<>();
    try {

			for (SpecificAsset asset: assetPlus.getSpecificAssets()) {

      TOSpecificAsset transferSpecificAsset = new TOSpecificAsset(asset.getAssetNumber(), asset.getFloorNumber(), asset.getRoomNumber(), asset.getPurchaseDate(), asset.getAssetType().getName());

      TOSpecificAssetList.add(transferSpecificAsset);
    }
			AssetPlusPersistence.save();
		} catch (Exception e) {
			throw e;
		}
    return TOSpecificAssetList;
}

}
