package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOUser;
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

public class UpdateNotePopupController {
  @FXML private Button closePopUpButton;
  @FXML private TextField newDescriptionTextField;
  @FXML private DatePicker newNoteDatePicker;
  @FXML private TextField noteIDTextField;
  @FXML private TextField ticketIDTextField;
  @FXML private Button updateNoteButton;
  @FXML private ChoiceBox<String> newEmailChoiceBox;

  NewNotePageController topController;

  public UpdateNotePopupController(NewNotePageController headController){
    this.topController = headController;
  }

  public void setFields(int ticketID, int noteID) {
    ticketIDTextField.setText(String.valueOf(ticketID));
    ticketIDTextField.setDisable(true);

    noteIDTextField.setText(String.valueOf(noteID));
    noteIDTextField.setDisable(true);

    List<TOUser> userList = AssetPlusFeatureSet1Controller.getUsers();
    ObservableList<String> hotelStaffList = FXCollections.observableArrayList();
    hotelStaffList.add("-- Select a hotel staff --");
    hotelStaffList.add("manager@ap.com");
    for (TOUser user : userList) {
        if (user.getEmail().endsWith("ap.com")) {
        hotelStaffList.add(user.getEmail());
        }
    }     
    newEmailChoiceBox.setItems(hotelStaffList);
    newEmailChoiceBox.setValue(hotelStaffList.get(0));
  }

  public void promptUpdateNotePopUp(int ticketID, int noteID) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateNotePop.fxml"));
      loader.setControllerFactory(param -> new UpdateNotePopupController(topController));
      Parent root = loader.load();

      // Autofill the email textfield
      UpdateNotePopupController controller = loader.getController();
      controller.setFields(ticketID, noteID);

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Update Note");
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

  public void handleUpdateNoteButtonClick() {

    try {
      String ticketIDStr = ticketIDTextField.getText();
      String noteIDStr = noteIDTextField.getText();
      java.sql.Date noteDate = Date.valueOf(newNoteDatePicker.getValue());
      String email = newEmailChoiceBox.getValue();
      String description = newDescriptionTextField.getText();

      int ticketID = Integer.parseInt(ticketIDStr);
      int noteID = Integer.parseInt(noteIDStr);

      String error = AssetPlusFeatureSet7Controller.updateMaintenanceNote(ticketID, noteID, noteDate, description, email);
      
      if (!error.equals("")) {
        ViewUtils.showError(error);
      }


    topController.refreshMTicket(); //ASK FOR REFRESHED TICKET
    topController.refreshTable(); //REFRESH THE NOTES TABLE
    topController.getMainController().refreshTable("Tickets"); //REFRESH THE MAIN PAGE TABLE 

    ViewUtils.closeWindow(noteIDTextField);
  } catch (Exception e) {
      ViewUtils.showError(e.getMessage());
      ViewUtils.closeWindow(noteIDTextField);
  } finally {
    ViewUtils.closeWindow(closePopUpButton);
  }
     

  }
}
