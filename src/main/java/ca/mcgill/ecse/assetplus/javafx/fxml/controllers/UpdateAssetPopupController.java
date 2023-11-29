package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
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

public class UpdateAssetPopupController {
  
  @FXML private Button updateAssetButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField assetNumberTextField;
  @FXML private TextField floortextField;
  @FXML private TextField roomTextField;
  @FXML private TextField purchaseDateTextField;
  @FXML private TextField assetTypeTextField;

  public void setAssetNumber(String assetNumber) {
    if (assetNumberTextField != null) {
      assetNumberTextField.setText(assetNumber);
      assetNumberTextField.setDisable(true);
      System.out.println(assetNumberTextField.getText());
      return;
    }
    System.out.println("someting wong");
  }

  public void promptUpdateAssetPopUp(Button updateButton, String assetNumber) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateAssetPopUp.fxml"));
      Parent root = loader.load();

      // Autofill the assetNumber textfield
      UpdateAssetPopupController controller = loader.getController();
      controller.setAssetNumber(assetNumber);     //TODO assetNumber is a string or int

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
      String purchaseDateStr = purchaseDateTextField.getText();
      String assetType = assetTypeTextField.getText();

      int assetNumber = Integer.parseInt(assetNumberStr);
      int floorNumber = Integer.parseInt(floorNumberStr);
      int roomNumber = Integer.parseInt(roomNumberStr);
      Date purchaseDate = Date.valueOf(purchaseDateStr); 

      String error = AssetPlusFeatureSet3Controller.updateSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType).replaceAll("([A-Z])", "\n$1");

      
      if (!error.equals("")) {
        ViewUtils.showError(error);
      }

      ViewUtils.closeWindow(assetTypeTextField);

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
