package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DisapproveTicketPopUpController {
  @FXML TextField ticketIdTextField;
  @FXML DatePicker dateRaisedDatePicker;
  @FXML TextArea noteTextArea;
  @FXML Button disapproveTicketButton;
  @FXML Button closePopUpButton;

  StdPageController topController;

  public DisapproveTicketPopUpController(StdPageController headController){
    this.topController = headController;
  }

  @FXML
  public void promptDisapproveTicketPopUp(int ticketId) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/DisapproveTicket.fxml"));
      loader.setControllerFactory(param -> new DisapproveTicketPopUpController(topController));
      Parent root = loader.load();
      // Fill in ticket id field
      DisapproveTicketPopUpController controller = loader.getController();
      controller.setAutoFillFields(ticketId);

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Disapprove Ticket");
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


  //wtf why?????

  @FXML
  public void closePopUp() {
    ViewUtils.closeWindow(closePopUpButton);
  }

  @FXML
  public void setAutoFillFields(int ticketId) {
    ticketIdTextField.setText(String.valueOf(ticketId));
    ticketIdTextField.setDisable(true);
  }

  @FXML 
  public void disapproveTicket() {
    int ticketId = Integer.parseInt(ticketIdTextField.getText());
    java.sql.Date date = java.sql.Date.valueOf(dateRaisedDatePicker.getValue());
    String description = noteTextArea.getText();

    String error = AssetPlusFeatureSet4Controller.DisapproveWorkOnMaintenanceTicket(ticketId, date, description);

    if (error != "") {
      ViewUtils.showError(error);
    }
    
    topController.refreshTable("Status");

    closePopUp();
  }
}
