package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

  import java.sql.Date;
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
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.*;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;

  public class AddNotePopupController {
  
      @FXML
      private Button addButton;
  
      @FXML
      private Button closeButton;
  
      @FXML
      private TextField dateTextField;
  
      @FXML
      private TextField descriptionTextField;
  
      @FXML
      private TextField emailTextField;
  
      @FXML
      private TextField ticketIDTextField;
  
      @FXML


  public void promptAddUserPopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddNotePopUp.fxml"));
      Parent root = loader.load();
      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Note");
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

      String ticketIDStr = ticketIDTextField.getText();
      String date = dateTextField.getText();
      String description = descriptionTextField.getText();
      String email = emailTextField.getText();
  
      int ticketID = 0;
      Date addedOnDate = new Date(0);
      ticketID = Integer.parseInt(ticketIDStr);
      addedOnDate = Date.valueOf(date);

      String error = AssetPlusFeatureSet7Controller.addMaintenanceNote(addedOnDate, description, ticketID, email);
      

      if (!error.equals("")) {
        ViewUtils.showError(error);
      }

    } catch (Exception e) {
      ViewUtils.showError(e.getMessage());
          
      
    }
  
  }
  
     
  
    }

  
      
  

  

