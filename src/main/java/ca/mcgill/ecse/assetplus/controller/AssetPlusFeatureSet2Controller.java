package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;

/**
 * The AssetPlusFeatureSet2Controller class provides methods for managing Asset Types.
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

    // Ensure that name is not empty or null
    if (name == null || name.trim().isEmpty()) {
      return "AssetType name cannot be empty or null. Please enter another Asset.";
    }

    // Check if expected life is greater than zero
    if (expectedLifeSpanInDays <= 0) {
      return "Expected life span must be greater than zero. Please enter another Asset.";
    }

    // Add Asset Type
    AssetType newAssetType =  new AssetType(name, expectedLifeSpanInDays, assetPlus);
    return "AssetType added successfully!";
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

    // Ensure old name is not null or empty
    if (oldName == null || oldName.trim().isEmpty()) {
      return "Old name must not be empty or null. Please enter another old name.";
    }

    // Ensure new name is not null or empty
    if (newName == null || newName.trim().isEmpty()) {
      return "New name must not be empty or null. Please enter another new name.";
    }

    // Check if newExpectedLifeSpanInDays is greater than 0
    if (newExpectedLifeSpanInDays <= 0) {
      return "New expected life span must be greater than zero";
    }

    // Check if Asset Type with the old name already exists
    AssetType assetType = AssetType.getWithName(oldName);
    if (assetType == null) {
      return "AssetType with the oldname does not exist. Please enter another old name.";
    }

    // Update the Asset Type
    assetType.setName(newName);
    assetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
    return "AssetType updated successfully!";
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
      throw new IllegalArgumentException("Name must not be empty or null. Please enter another name.");
    }

    // Check if Asset Type exists
    AssetType assetType = AssetType.getWithName(name);
    if (assetType == null) {
      throw new IllegalArgumentException("AssetType with the given name does not exist. Please enter another name.");
    }

    // Delete Asset Type
    assetType.delete();
  }

}