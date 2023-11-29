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

public class AddTicketPopUpController {

  @FXML private Button addTicketButton;
  @FXML private TextField assetNumberTextField;
  @FXML private Button closePopUpButton;
  @FXML private TextField dateRaisedTextField;
  @FXML private TextField descriptionTextField;
  @FXML private TextField raiserEmailTextField;
  @FXML private TextField ticketIdTextField;

  @FXML
  public void promptAddTicketPopUp() {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddMaintenanceTicketPopUp.fxml"));
      Parent root = loader.load();
      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Maintenance Ticket");
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
  public void addMaintenanceTicket() throws NumberFormatException, ParseException {
    String assetNumber = assetNumberTextField.getText();
    String dateRaised = dateRaisedTextField.getText();
    String description = descriptionTextField.getText();
    String raiserEmail = raiserEmailTextField.getText();
    String ticketId = ticketIdTextField.getText();

    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
      String error = AssetPlusFeatureSet4Controller.addMaintenanceTicket(Integer.parseInt(ticketId), Date.valueOf(dateRaised), description, raiserEmail, Integer.parseInt(assetNumber));
      if (!error.equals("")) {
        ViewUtils.showError(error);
  
        ViewUtils.closeWindow(addTicketButton);
  
      }
      } catch (Exception e) {
        ViewUtils.showError(e.getMessage());
      }
      finally {
      ViewUtils.closeWindow(addTicketButton);
    }
  }

  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(ticketIdTextField);
  }

}

