package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateUserPopUpController {

  @FXML private Button updateUserButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField emailtextField;
  @FXML private TextField nameTextField;
  @FXML private TextField phoneNumberTextField;
  @FXML private PasswordField passwordTextField;

  public void setEmail(String email) {
    if (emailtextField != null) {
        emailtextField.setText(email);
        emailtextField.setDisable(true);
    }
  }

  public void promptUpdatePopUp(String email) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateUserPopUp.fxml"));
      Parent root = loader.load();

      // Autofill the email textfield
      UpdateUserPopUpController controller = loader.getController();
      controller.setEmail(email);

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Update User");
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

  public void handleUpdateUserButtonClick() {

    String userEmail = emailtextField.getText();
    String userName = nameTextField.getText();
    String userPassword = passwordTextField.getText();
    String userPhoneNumber = phoneNumberTextField.getText();

    String error = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(userEmail, userPassword, userName, userPhoneNumber).replaceAll("([A-Z])", "\n$1");

    // Check if string is not empty... if string is empty, operation was successful
    if (!error.equals("")) {
      ViewUtils.showError(error);
    }

    ViewUtils.closeWindow(emailtextField);
  }
}