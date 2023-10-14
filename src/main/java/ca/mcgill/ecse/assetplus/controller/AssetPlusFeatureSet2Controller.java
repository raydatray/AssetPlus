package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.AssetType;

public class AssetPlusFeatureSet2Controller {

  public static String addAssetType(String name, int expectedLifeSpanInDays) {
    // Ensure that name is not empty or null
    if (name == null || name.trim().isEmpty()) {
      return "AssetType name cannot be empty or null. Please enter another Asset.";
    }

    // Check if expected life is greater than zero
    if (expectedLifeSpanInDays <= 0) {
      return "Expected life span must be greater than zero. Please enter another Asset.";
    }

    // Add Asset Type
    AssetType assetType = AssetType.getWithName(name);
    AssetType newAssetType =  new AssetType(name, expectedLifeSpanInDays, assetType.getAssetPlus());
    return "AssetType added successfully!";
  }

  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
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


    // Ensure that an Asset Type with the new name does not exist already and make sure not updating the new name to the same name it already has
    AssetType existingAssetType = AssetType.getWithName(newName);
    if (existingAssetType != null && existingAssetType != assetType) {
      return "An AssetType with the new name already exists. Please enter another new name.";
    }

    // Update the Asset Type
    assetType.setName(newName);
    assetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
    return "AssetType updated successfully!";
  }

  public static void deleteAssetType(String name) {
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