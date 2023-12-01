package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import javafx.event.ActionEvent;
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

public class AddImageToTicketPopUpController {
    
  @FXML
  private Button addButton;

  @FXML
  private Button closeButton;

  @FXML
  private TextField imageURLTextField;

  @FXML
  private TextField ticketIDTextField;

  @FXML
  
  public void setTicketID(int ticketID) {
      if (ticketIDTextField != null) {
          ticketIDTextField.setText(String.valueOf(ticketID));
          ticketIDTextField.setDisable(true);
      }
  }
  
  public void promptAddImagePopUp(Button addButton, int ticketID) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass()
              .getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddImageToMaintenanceTicket.fxml"));
      Parent root = loader.load();

      // Autofill the ticket textfield
      AddImageToTicketPopUpController controller = loader.getController();
      controller.setTicketID(ticketID);

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Image");
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
    ViewUtils.closeWindow(closeButton);
  }

  public void addButtonClicked(ActionEvent event) {
        
    try {

      String ticketID = ticketIDTextField.getText();
      String imageURL = imageURLTextField.getText();

      String error = AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(imageURL, Integer.parseInt(ticketID));


      if (!error.equals("")) {
        ViewUtils.showError(error);
        return;
      }

      ViewUtils.closeWindow(addButton);


    } catch (Exception e) {
      ViewUtils.showError("Invalid inputs provided.");
          
      
    }
  
  }
  
     
  
}

  
      
  

  

