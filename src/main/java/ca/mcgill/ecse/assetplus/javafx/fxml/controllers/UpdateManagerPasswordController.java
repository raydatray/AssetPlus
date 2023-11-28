package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class UpdateManagerPasswordController {
  @FXML
  private TextField currentPasswordField;

  @FXML
  private TextField newPasswordField;

  @FXML
  private TextField confirmPassworField;

  @FXML
  private void handleResetPassword() {
    String currentPassword = currentPasswordField.getText();
    String newPassword = newPasswordField.getText();
    String confirmPassword = confirmPassworField.getText();

    Boolean isCurrentPasswordCorrect = AssetPlusFeatureSet1Controller.isManagerPasswordCorrect(currentPassword);
    Boolean hasCompleteFields = !currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty();
    Boolean isPasswordMatching = newPassword.equals(confirmPassword);

    if (!isCurrentPasswordCorrect) {
      showAlert("Error Updating Password", "Current password is incorrect.");
      return;
    } 

    if (!hasCompleteFields) {
      showAlert( "Error Updating Password", "Please fill in all fields.");
      return;
    }

    if (!isPasswordMatching) {
      showAlert("Error Updating Password", "New password and confirm password do not match.");
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
      
