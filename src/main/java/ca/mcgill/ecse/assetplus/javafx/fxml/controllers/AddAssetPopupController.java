package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.time.LocalDate;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddAssetPopupController {
  
  @FXML private Button addNewAssetButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField assetNumberTextField;
  @FXML private TextField floortextField;
  @FXML private TextField roomTextField;
  @FXML private TextField assetTypeTextField;

  public void promptAddAssetPopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddAssetPopUp.fxml"));
      Parent root = loader.load();
      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Asset");
      // Set the content from the FXML file
      Scene scene = new Scene(root);
      scene.setFill(Color.TRANSPARENT);
      popupStage.setScene(scene);
      // Show the pop-up
      popupStage.show();
    } catch (Exception e) {
      // Maybe prompt error pop up in case or error?
      System.err.println("Error loading FXML: " + e.getMessage());
    }
  }

  public void handleCloseButtonClick() {
    // Get the stage (window) from the close button
    Stage stage = (Stage) closePopUpButton.getScene().getWindow();
    // Close the stage
    stage.close();
  }

  public void handleAddNewAssetButtonClick() {

    String assetNumStr = assetNumberTextField.getText();
    String floorStr = floortextField.getText();
    String roomStr = roomTextField.getText();
    String assetTypeStr = assetTypeTextField.getText();

    //AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumStr, floorStr, roomStr, new Date(LocalDate.now().toEpochDay()), assetTypeStr);
    // try {
    //   int assetNumber = Integer.parseInt(assetNumStr);
    // } catch (NumberFormatException e) {
    //   showAlert("Error", "Asset number must be an integer.");
    // }

    // try {
    //   int floorNumber = Integer.parseInt(floorStr);
    // } catch (NumberFormatException e) {
    //   showAlert("Error", "Floor number must be an integer.");
    // }

    // try {
    //   int roomNumber = Integer.parseInt(roomStr);
    // } catch (NumberFormatException e) {
    //   showAlert("Error", "Room number must be an integer.");
    // }

    //AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber , roomNumber, new Date(LocalDate.now().toEpochDay()), assetTypeStr);
    // Close the pop-up
    handleCloseButtonClick();
    // refresh data table

  }
}

