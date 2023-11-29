package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

  import java.sql.Date;
  import javafx.event.ActionEvent;
  import javafx.fxml.FXML;
  import javafx.scene.control.Button;
  import javafx.scene.control.TextField;
  import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.*;

  import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;

  public class NotePopupAddController {
  
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
      void addButtonClicked(ActionEvent event) {
          
        String ticketIDStr = ticketIDTextField.getText();
        String date = dateTextField.getText();
        String description = descriptionTextField.getText();
        String email = emailTextField.getText();
      
        int ticketID = 0;
        Date addedOnDate = new Date(0);

        try {
          ticketID = Integer.parseInt(ticketIDStr);
          addedOnDate = Date.valueOf(date);
        } catch (Exception e) {
          ViewUtils.showError(e.getMessage());
        }

        if (successful(AssetPlusFeatureSet7Controller.addMaintenanceNote(addedOnDate, description, ticketID, email))) {
            
          
        }
      
      }
  
      @FXML
      void closeButtonClicked(ActionEvent event) {
        ViewUtils.closeWindow(closeButton);

      }
  
    }

  
      
  

  

