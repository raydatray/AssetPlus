package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

/**
 * The AssetPlusFeatureSet2Controller class provides methods for managing Asset Types.
 * Completed by Aurelia Bouliane (Goldrelia on github)
 */

public class AssetPlusFeatureSet2Controller {

  /**
     * Adds a new Asset Type with the specified name and expected lifespan.
     *
     * @param name                  The name of the new Asset Type. Must not be empty or null.
     * @param expectedLifeSpanInDays The expected lifespan of the new Asset Type, in days. Must be greater than zero.
     * @return                      A message indicating the success or failure of the operation.
     */

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static String addAssetType(String name, int expectedLifeSpanInDays) {
    // Implementation of the method
    String error = "";

    // Ensure that name is not empty or null
    if (name == null || name.trim().isEmpty()) {
      error = "The name must not be empty";
      return error;
    }

    // Check if expected life is greater than zero
    if (expectedLifeSpanInDays <= 0) {
      error = "The expected life span must be greater than 0 days";
      return error;
    }
    
   
    //  Check if asset type already exists
    AssetType assetType = AssetType.getWithName(name);
    if (assetType != null) {
      error = "The asset type already exists";
      return error;
    }

      // Add Asset Type
    try {
      AssetType newAssetType =  new AssetType(name, expectedLifeSpanInDays, assetPlus);
      AssetPlusPersistence.save();
      return error;
    } catch (Exception e) {
      error = e.getMessage();
    }
    return error;
  }

  /**
     * Updates an existing Asset Type with a new name and expected lifespan.
     *
     * @param oldName                The current name of the Asset Type to be updated. Must not be empty or null.
     * @param newName                The new name for the Asset Type. Must not be empty or null, and should not match the old name.
     * @param newExpectedLifeSpanInDays The new expected lifespan for the Asset Type, in days. Must be greater than zero.
     * @return                        A message indicating the success or failure of the operation.
     */

  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
    // Implementation of the method
    String error = "";

    // Check if newExpectedLifeSpanInDays is greater than 0
    if (newExpectedLifeSpanInDays <= 0) {
      error = "The expected life span must be greater than 0 days";
      return error;
    }

    // Ensure new name is not null or empty
    if (newName == null || newName.trim().isEmpty()) {
      error = "The name must not be empty";
      return error;
    }

    // Ensure old name is not null or empty
    if (oldName == null || oldName.trim().isEmpty()) {
      error = "The name must not be empty";
      return error;
    }

    try {
      // Check if Asset Type with the old name already exists
      AssetType assetType = AssetType.getWithName(oldName);
      if (assetType == null) {
        error = "The asset type does not exist";
        return error;
      }

      // Check if Asset Type with the new name already exists and check if oldName and newName not equal to each other
      AssetType newAssetType = AssetType.getWithName(newName);
      if (newAssetType != null && !oldName.equals(newName)) {
        error = "The asset type already exists";
        return error;
      }

      // Update the Asset Type
      assetType.setName(newName);
      assetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
      
      AssetPlusPersistence.save();
    } catch (Exception e) {
      error = e.getMessage();
    }
    return error;
  }

  /**
     * Deletes an Asset Type with the specified name.
     *
     * @param name The name of the Asset Type to be deleted. Must not be empty or null.
     * @throws IllegalArgumentException If the Asset Type with the specified name does not exist.
     */

  public static void deleteAssetType(String name) {
    // Implementation of the method

    // Check if name is null or empty
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("The name must not be empty");
    }

    try {
       AssetType assetType = AssetType.getWithName(name);

       List<SpecificAsset> specificAssetsToDelete = new ArrayList<SpecificAsset>();

      for (SpecificAsset asset : assetPlus.getSpecificAssets()) {
        if (asset.getAssetType() != null && asset.getAssetType().equals(assetType)) {
          specificAssetsToDelete.add(asset);
        }
      }

      for (SpecificAsset asset : specificAssetsToDelete) {
        int assetNumber = asset.getAssetNumber();
        AssetPlusFeatureSet3Controller.deleteSpecificAsset(assetNumber);
      }
      
       if (assetType != null) {
      //   for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()){
      //     SpecificAsset specificAsset = ticket.getAsset();
      //     if (specificAsset != null){
      //     if (name.equals(specificAsset.getAssetType().getName())){
      //       AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticket.getId());
      //     }}
      //   }
        assetType.delete();}
      AssetPlusPersistence.save();
    } catch (Exception e) {
      throw e;
    }
  }

  public static List<TOAssetType> getAssetTypes() {

    List<TOAssetType> TOAssetTypeList = new ArrayList<>();
    try {

			for (AssetType assetType: assetPlus.getAssetTypes()) {

      TOAssetType transferAssetType = new TOAssetType(assetType.getName(), assetType.getExpectedLifeSpan());

      TOAssetTypeList.add(transferAssetType);
    }
			AssetPlusPersistence.save();
		} catch (Exception e) {
			throw e;
		}
    return TOAssetTypeList;
}
}
