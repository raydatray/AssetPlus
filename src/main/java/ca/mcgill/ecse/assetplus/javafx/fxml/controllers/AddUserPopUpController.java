package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddUserPopUpController {

  @FXML private Button addNewUserButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField emailtextField;
  @FXML private TextField passwordTextField;
  @FXML private TextField nameTextField;
  @FXML private TextField phoneNumberTextField;
  @FXML private ChoiceBox<String> userTypeChoiceBox;

  StdPageController topController;

  public AddUserPopUpController(StdPageController headController) {
    this.topController = headController;
  }

  public void promptAddUserPopUp() {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddUserPopUp.fxml"));
      loader.setControllerFactory(param -> new AddUserPopUpController(topController));
      Parent root = loader.load();

      AddUserPopUpController controller = loader.getController();
      controller.userTypeChoiceBox.getItems().addAll("Guest", "Hotel Staff");
      controller.userTypeChoiceBox.setValue("Guest");

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
      ViewUtils.showError(e.getMessage());
    }
  }

  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(closePopUpButton);
  }

  public void handleAddNewUserButtonClick() {
    try{
      String userEmail = emailtextField.getText();
      String userName = nameTextField.getText();
      String userPhoneNumber = phoneNumberTextField.getText();
      String userPassword = passwordTextField.getText();
      Boolean isEmployee = userTypeChoiceBox.getValue() == "Hotel Staff" ? true : false;

      String error = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(userEmail, userPassword, userName, userPhoneNumber, isEmployee).replaceAll("([A-Z])", "\n$1");
      
      // Check if string is not empty... if string is empty, operation was successful
      if (!error.equals("")) {
        ViewUtils.showError(error);
        return;
      }
      
      topController.refreshTable("Users");

      ViewUtils.closeWindow(emailtextField);
    } catch (Exception e) {
      ViewUtils.showError(e.getMessage());
    }
  }
}