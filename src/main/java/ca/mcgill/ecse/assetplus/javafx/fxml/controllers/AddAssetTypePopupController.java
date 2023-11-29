package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
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

public class AddAssetTypePopupController {
  
  @FXML private Button addNewAssetTypeButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField assetNameTextField;
  @FXML private TextField expectedLifeSpanTextField;

  public void promptAddAssetTypePopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddAssetTypePopup.fxml"));
      Parent root = loader.load();
      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Asset Type");
      // Set the content from the FXML file
      Scene scene = new Scene(root);
      scene.setFill(Color.TRANSPARENT);
      popupStage.setScene(scene);
      // Show the pop-up
      popupStage.show();
    } catch (Exception e) {
      // Maybe prompt error pop up in case or error?
      ViewUtils.showError(e.getMessage());
    }
  }

  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(closePopUpButton);
  }

  public void handleAddNewAssetTypeButtonClick() {

    try{
      String assetName = assetNameTextField.getText();
      int expectedLifeSpan = Integer.parseInt(expectedLifeSpanTextField.getText());

      String error = AssetPlusFeatureSet2Controller.addAssetType(assetName, expectedLifeSpan);

      // Check if string is not empty... if string is empty, operation was successful
      if (!error.equals("")) {
        ViewUtils.showError(error);
      }

      ViewUtils.closeWindow(assetNameTextField);

    } catch (Exception e) {
      ViewUtils.showError(e.getMessage());
    }
  }
}
