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
        //Checks if assetNumber is greater or equal to one
        if (assetNumber < 1) {
          return "Asset number must be greater than or equal to one.";
        }

        //Checks if floorNumber is greater or equal to one
        if (floorNumber < 0) {
          return "Floor number must be greater or equal to zero.";
        }

        //Checks if roomNumber is greater than or equal to -1
        if (roomNumber < -1) {
          return "Room number must be greater than or equal to minus one.";
        }
        
        //Error caught -> Specific asset has to be an asset in the first place
        AssetType assetToAddTo = AssetType.getWithName(assetTypeName);
        //THIS WILL NOT WORK IF YOUR ASSETTYPENAME IS NOT A REAL ONE

        //Add Asset
        SpecificAsset mySpecificAsset = new SpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetPlus, assetToAddTo);
        return "Specific asset added successfully!";
  }

  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        //Checks if assetNumber is greater or equal to one
        if (assetNumber < 1) {
          return "Asset number must be greater than or equal to one.";
        }

        //Checks if floorNumber is greater or equal to one
        if (newFloorNumber < 0) {
          return "New floor number must be greater or equal to zero.";
        }

        //Checks if roomNumber is greater than or equal to -1
        if (newRoomNumber < -1) {
          return "New room number must be greater than or equal to minus one.";
        }

        //Error caught -> you have to check within assets, if this specificasse exists

        
        //Case 1: Retain the same assetNumber, 
        AssetType assetToUpdate = AssetType.getWithName(newAssetTypeName);

        for (SpecificAsset specificAssetToUpdate : assetToUpdate.getSpecificAssets()){
          if (specificAssetToUpdate.getAssetNumber() == assetNumber){
            specificAssetToUpdate.setFloorNumber(newFloorNumber);
            specificAssetToUpdate.setRoomNumber(newRoomNumber);
            specificAssetToUpdate.setPurchaseDate(newPurchaseDate);
            return "Specific asset updated";
          }
        }

        return "Specific asset not updated";
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
