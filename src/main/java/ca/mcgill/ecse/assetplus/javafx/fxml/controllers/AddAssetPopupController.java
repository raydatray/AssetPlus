package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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

public class AddAssetPopupController{
  
  @FXML private Button addNewAssetButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField assetNumberTextField;
  @FXML private TextField floortextField;
  @FXML private TextField roomTextField;
  @FXML private DatePicker purchaseDatePicker;
  @FXML private ChoiceBox<String> assetTypeChoiceBox;

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
  StdPageController topController;

  public AddAssetPopupController(StdPageController headController){
    this.topController = headController;
  }

  public void promptAddAssetPopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddAssetPopUp.fxml"));
      loader.setControllerFactory(param -> new AddAssetPopupController(topController));
      Parent root = loader.load();

      // Populate the choice box
      AddAssetPopupController controller = loader.getController();
      controller.populateAssetTypeChoiceBox();

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
      ViewUtils.showError(e.getMessage());
    }
  }

  public void handleCloseButtonClick() {
      ViewUtils.closeWindow(closePopUpButton);
    }

  public void handleAddNewAssetButtonClick() {
    LocalDate purchaseDateValue = purchaseDatePicker.getValue();
    if (purchaseDateValue == null) {
        ViewUtils.showError("Please select a purchase date");
    }

    java.sql.Date purchaseDate = java.sql.Date.valueOf(purchaseDateValue);

    String assetNumberStr = assetNumberTextField.getText();
    String floorNumberStr = floortextField.getText();
    String roomNumberStr = roomTextField.getText();
    String assetType = assetTypeChoiceBox.getValue();

    if (assetNumberStr.isEmpty() || floorNumberStr.isEmpty() || roomNumberStr.isEmpty() || assetType == null) {
      ViewUtils.showError("Please fill in all the fields");
    }

    try {
      int assetNumber = Integer.parseInt(assetNumberStr);
      int floorNumber = Integer.parseInt(floorNumberStr);
      int roomNumber = Integer.parseInt(roomNumberStr);

      String error = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType).replaceAll("([A-Z])", "\n$1");

      if (!error.equals("")) {
        ViewUtils.showError(error);
      }

    } catch (NumberFormatException e) {
      ViewUtils.showError("Please enter valid numbers for asset, floor, and room");
    }
    
    topController.refreshTable("Assets");

    ViewUtils.closeWindow(addNewAssetButton);
    } 
}