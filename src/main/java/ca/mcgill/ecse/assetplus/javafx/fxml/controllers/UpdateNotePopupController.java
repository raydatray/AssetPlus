package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateNotePopupController {
  
  @FXML private Button updateNoteButton;
  @FXML private Button closePopUpButton;
  @FXML private TextField ticketIDTextField;
  @FXML private TextField noteIDTextField;
  @FXML private DatePicker newNoteDatePicker;
  @FXML private TextField newEmailTextField;
  @FXML private TextField newDescriptionTextField;

  public void setTicketID(int ticketID) {
    if (ticketIDTextField != null) {
        ticketIDTextField.setText(String.valueOf(ticketID));
        ticketIDTextField.setDisable(true);
    }
  }

  public void setNoteID(int noteID) {
    if (noteIDTextField != null) {
        noteIDTextField.setText(String.valueOf(noteID));
        noteIDTextField.setDisable(true);
    }
  }

  public void promptUpdateNotePopUp(int ticketID, int noteID) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateNotePopUp.fxml"));
      Parent root = loader.load();

      // Autofill the email textfield
      UpdateNotePopupController controller = loader.getController();
      controller.setTicketID(ticketID);
      controller.setNoteID(noteID);

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
      String email = newEmailTextField.getText();
      String description = newDescriptionTextField.getText();

      int ticketID = Integer.parseInt(ticketIDStr);
      int noteID = Integer.parseInt(noteIDStr);

      String error = AssetPlusFeatureSet7Controller.updateMaintenanceNote(ticketID, noteID, noteDate, description, email);
      
      if (!error.equals("")) {
        ViewUtils.showError(error);
    }

    ViewUtils.closeWindow(noteIDTextField);
  } catch (Exception e) {
      ViewUtils.showError(e.getMessage());
    }
  }
}
