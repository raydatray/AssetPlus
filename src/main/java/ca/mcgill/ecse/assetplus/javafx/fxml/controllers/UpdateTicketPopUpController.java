package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateTicketPopUpController {

  @FXML private Button updateTicketButton;
  @FXML private Button closePopUpButton;

  @FXML private TextField ticketIDTextField;
  @FXML private TextField newDateRaisedTextField;
  @FXML private TextField newRaiserEmailTextField;
  @FXML private TextField newAssetNumberTextField;
  @FXML private TextField newDescriptionTextField;


  public void setTicketID(int ticketID) {
    if (ticketIDTextField != null) {
        ticketIDTextField.setText(String.valueOf(ticketID));
    }
  }


  @FXML
  public void promptUpdateTicketPopUp(Button updateButton, int ticketID) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateMaintenanceTicketPopup.fxml"));
      Parent root = loader.load();
      
      // Autofill the ticket textfield
      UpdateTicketPopUpController controller = loader.getController();
      controller.setTicketID(ticketID); 

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Update Maintenance Ticket");
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

  @FXML
  public void handleUpdateTicketButtonClick() throws NumberFormatException, ParseException {

    String ticketId = ticketIDTextField.getText();

    String newDateRaised = newDateRaisedTextField.getText();
    String newDescription = newDescriptionTextField.getText();
    String newRaiserEmail = newRaiserEmailTextField.getText();
    String newAssetNumber = newAssetNumberTextField.getText();

  
    try {
      String error = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(Integer.parseInt(ticketId), Date.valueOf(newDateRaised), newDescription, newRaiserEmail, Integer.parseInt(newAssetNumber));
      if (!error.equals("")) {
        ViewUtils.showError(error);
  
        ViewUtils.closeWindow(updateTicketButton);
  
      }
      } catch (Exception e) {
        ViewUtils.showError(e.getMessage());
      }
      finally {
      ViewUtils.closeWindow(updateTicketButton);
    }
  }

  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(updateTicketButton);
  }

}

