package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

public class AssetPlusFeatureSet3Controller {

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

        //Add Asset
        SpecificAsset mySpecificAsset = new SpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetTypeName);
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

        //Checks if the asset exists in the first place
        SpecificAsset mySpecificAsset = SpecificAsset.getSpecificAsset(assetNumber);
        if (mySpecificAsset == null) {
          return "Specific asset with this asset number does not exist.";
        }

        //Update the specific asset
        mySpecificAsset.setFloorNumber(newFloorNumber);
        mySpecificAsset.setRoomNumber(newRoomNumber);
        mySpecificAsset.setPurchaseDate(newPurchaseDate); 
        mySpecificAsset.setAssetType(newAssetTypeName);
        return "Specific asset updated successfully";
  }

  public static void deleteSpecificAsset(int assetNumber) {
    //Check if already at minimum number of specific assets
    if (!hasSpecificAssets()) {
      return "Already at minimum number of specific assets";
    }

    //Check if the asset with this number exists
    SpecificAsset mySpecificAsset = SpecificAsset.getSpecificAsset(assetNumber);
        if (mySpecificAsset == null) {
          return "Specific asset with this asset number does not exist.";
        }

   mySpecificAsset.delete();
  }

}
