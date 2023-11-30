package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateAssetPopupController {
  
  @FXML private Button updateAssetButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField assetNumberTextField;
  @FXML private TextField floortextField;
  @FXML private TextField roomTextField;
  @FXML private DatePicker purchaseDatePicker;
  @FXML private ChoiceBox<String> assetTypeChoiceBox;

  public void setAssetNumber(String assetNumber) {
    if (assetNumberTextField != null) {
      assetNumberTextField.setText(assetNumber);
      assetNumberTextField.setDisable(true);
    }
  }

  public void populateAssetTypeChoiceBox() {
    if (assetTypeChoiceBox != null) {
      List<TOAssetType> assetTypeList = AssetPlusFeatureSet2Controller.getAssetTypes();

      ObservableList<String> optionsList = FXCollections.observableArrayList();
      
      optionsList.addAll("Select an asset type");

      // Loop through the assetTypeList and add names to optionsList
      for (TOAssetType assetType : assetTypeList) {
        optionsList.add(assetType.getName());
      }

      assetTypeChoiceBox.setItems(optionsList);
      assetTypeChoiceBox.setValue(optionsList.get(0));
    }
  }

  public void promptUpdateAssetPopUp(Button updateButton, String assetNumber) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateAssetPopUp.fxml"));
      Parent root = loader.load();

      // Autofill the assetNumber textfield - Populate the choice box
      UpdateAssetPopupController controller = loader.getController();
      controller.populateAssetTypeChoiceBox();
      controller.setAssetNumber(assetNumber);     

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Update Asset");
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

  public void handleUpdateAssetButtonClick() {

    try {

      String assetNumberStr = assetNumberTextField.getText();
      String floorNumberStr = floortextField.getText();
      String roomNumberStr = roomTextField.getText();
      java.sql.Date purchaseDate = Date.valueOf(purchaseDatePicker.getValue());
      String assetType = assetTypeChoiceBox.getValue();

      int assetNumber = Integer.parseInt(assetNumberStr);
      int floorNumber = Integer.parseInt(floorNumberStr);
      int roomNumber = Integer.parseInt(roomNumberStr);

      String error = AssetPlusFeatureSet3Controller.updateSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType).replaceAll("([A-Z])", "\n$1");

      
      if (!error.equals("")) {
        ViewUtils.showError(error);
      }

      ViewUtils.closeWindow(updateAssetButton);

    } catch (Exception e) {
        ViewUtils.showError(e.getMessage());
    }
  }
}