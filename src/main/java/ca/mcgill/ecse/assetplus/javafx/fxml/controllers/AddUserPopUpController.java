package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
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

public class AddUserPopUpController {

  @FXML private Button addNewUserButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField emailtextField;
  @FXML private TextField nameTextField;
  @FXML private TextField phoneNumberTextField;

  public void promptAddUserPopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddUserPopUp.fxml"));
      Parent root = loader.load();
      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add User");
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

  public void handleAddNewUserButtonClick() {

    String userEmail = emailtextField.getText();
    String userName = nameTextField.getText();
    String userPhoneNumber = phoneNumberTextField.getText();

    AssetPlusFeatureSet1Controller.addEmployeeOrGuest(userEmail, "12345", userName, userPhoneNumber, false);
    // Close the pop-up
    handleCloseButtonClick();
    // refresh data table

  }
}