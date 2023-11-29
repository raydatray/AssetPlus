package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class UpdateManagerPasswordController {

  @FXML
  private TextField newPasswordField;

  @FXML
  private void handleResetPassword() {
    String newPassword = newPasswordField.getText();

    Boolean hasCompleteFields = !newPassword.isEmpty();

    if (!hasCompleteFields) {
      showAlert( "Error Updating Password", "Please fill in all fields.");
      return;
    }

    try {
      String updateManagerResult = AssetPlusFeatureSet1Controller.updateManager(newPassword);
      showAlert("Update Manager Password", updateManagerResult);
    } catch (Exception e) {
      showAlert("Error", e.getMessage()); 
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
      
