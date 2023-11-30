package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
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

    try {


      String assetNumberStr = assetNumberTextField.getText();
      String floorNumberStr = floortextField.getText();
      String roomNumberStr = roomTextField.getText();
      java.sql.Date purchaseDate = Date.valueOf(purchaseDatePicker.getValue());
      String assetType = assetTypeChoiceBox.getValue();

      int assetNumber = Integer.parseInt(assetNumberStr);
      int floorNumber = Integer.parseInt(floorNumberStr);
      int roomNumber = Integer.parseInt(roomNumberStr);

      String error = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType).replaceAll("([A-Z])", "\n$1");

      
      if (!error.equals("")) {
        ViewUtils.showError(error);
      }

      topController.refreshTable("Assets");

      ViewUtils.closeWindow(addNewAssetButton);

    } catch (Exception e) {
        ViewUtils.showError(e.getMessage());
    }

      
      // Check if string is not empty... if string is empty, operation was successful
      

    }

}




//   public void promptAddAssetPopUp(Button addButton) {
//     try {
//       // Load the FXML file
//       FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddAssetPopUp.fxml"));
//       Parent root = loader.load();
//       // Create a new stage for the pop-up
//       Stage popupStage = new Stage();
//       popupStage.initModality(Modality.APPLICATION_MODAL);
//       popupStage.initStyle(StageStyle.TRANSPARENT);
//       popupStage.setTitle("Add Asset");
//       // Set the content from the FXML file
//       Scene scene = new Scene(root);
//       scene.setFill(Color.TRANSPARENT);
//       popupStage.setScene(scene);
//       // Show the pop-up
//       popupStage.show();
//     } catch (Exception e) {
//       // Maybe prompt error pop up in case or error?
//       ViewUtils.showError(e.getMessage());
//     }
//   }

//   public void handleCloseButtonClick() {
//     // Get the stage (window) from the close button
//     Stage stage = (Stage) closePopUpButton.getScene().getWindow();
//     // Close the stage
//     stage.close();
//   }

//   public void handleAddNewAssetButtonClick() {

//     String assetNumStr = assetNumberTextField.getText();
//     String floorStr = floortextField.getText();
//     String roomStr = roomTextField.getText();
//     String assetTypeStr = assetTypeTextField.getText();

//     //AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumStr, floorStr, roomStr, new Date(LocalDate.now().toEpochDay()), assetTypeStr);
//     // try {
//     //   int assetNumber = Integer.parseInt(assetNumStr);
//     // } catch (NumberFormatException e) {
//     //   showAlert("Error", "Asset number must be an integer.");
//     // }

//     // try {
//     //   int floorNumber = Integer.parseInt(floorStr);
//     // } catch (NumberFormatException e) {
//     //   showAlert("Error", "Floor number must be an integer.");
//     // }

//     // try {
//     //   int roomNumber = Integer.parseInt(roomStr);
//     // } catch (NumberFormatException e) {
//     //   showAlert("Error", "Room number must be an integer.");
//     // }

//     //AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber , roomNumber, new Date(LocalDate.now().toEpochDay()), assetTypeStr);
//     // Close the pop-up
//     handleCloseButtonClick();
//     // refresh data table

//   }
// }

