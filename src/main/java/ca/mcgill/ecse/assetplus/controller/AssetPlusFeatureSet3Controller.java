package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet3Controller {

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

        //Error caught -> Specific asset has to be an asset in the first place
        AssetType assetToAddTo = AssetType.getWithName(assetTypeName);
        if (assetToAddTo == null){
          error = "The asset type does not exist";
          return error;
        }

        //Add Asset
        SpecificAsset mySpecificAsset = new SpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetPlus, assetToAddTo);
        return error;
  }

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
        return "";
    }

  public static void deleteSpecificAsset(int assetNumber) {
    //Delete a specific asset by assetNumber
    for (SpecificAsset assetToDelete: assetPlus.getSpecificAssets()){
      if(assetToDelete.getAssetNumber() == assetNumber){
        assetToDelete.delete();
      }
    }
  }

}
