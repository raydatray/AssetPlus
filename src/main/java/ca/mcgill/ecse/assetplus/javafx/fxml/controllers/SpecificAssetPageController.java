package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.successful;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.Button;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controller class for the Specific Asset Page in the AssetPlus JavaFX application.
 * @author Houman Azari (HoomanAzari on github)
 */

public class SpecificAssetPageController {
  @FXML private TextField assetNumberTextField;
  @FXML private TextField floorNumberTextField;
  @FXML private TextField roomNumberTextField;
  @FXML private TextField purchaseDateTextField;
  @FXML private TextField assetTypeNameTextField;
  
  @FXML
  void addSpecificAsset(ActionEvent event) {
      String assetNumStr = assetNumberTextField.getText();
      String floorNumStr = floorNumberTextField.getText();
      String roomNumStr = roomNumberTextField.getText();
      String purchaseDateStr = purchaseDateTextField.getText();
      String assetTypeName = assetTypeNameTextField.getText();
    
      try {
          int assetNumber = Integer.parseInt(assetNumStr);
          int floorNumber = Integer.parseInt(floorNumStr);
          int roomNumber = Integer.parseInt(roomNumStr);
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date purchaseDate = sdf.parse(purchaseDateStr);
          String result = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNum, floorNumber, roomNumber, purchaseDate, assetTypeName);
          showAlert("Add Specific Asset", result);
      } catch (NumberFormatException e) {
          showAlert("Error", result);
      }
  }


  /**
     * Displays an information alert with the given title and content.
     * @param title   The title of the alert.
     * @param content The content or message of the alert.
     */

     private void showAlert(String title, String content) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.showAndWait();
  }
}




  