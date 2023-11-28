package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.sql.Date;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.successful;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


public class NotePageController {
  @FXML private TextField noteIDTextField;
  @FXML private TextField ticketIDTextField;
  @FXML private TextField dateTextField;
  @FXML private TextField descriptionTextField;
  @FXML private TextField emailTextField;

  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;


  // add
  @FXML
  public void addButtonClicked(ActionEvent event) {
    // String noteId = noteIdTextField.getText();
    String ticketIDStr = ticketIDTextField.getText();
    String date = dateTextField.getText();
    String description = descriptionTextField.getText();
    String email = emailTextField.getText();
  
    int ticketID = 0;
    Date raisedOnDate = new Date(0);

    try {
      ticketID = Integer.parseInt(ticketIDStr);
      raisedOnDate = Date.valueOf(date);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket id");
    }

    // how to get existing ticket id?
    if (successful(AssetPlusFeatureSet7Controller.addMaintenanceNote(raisedOnDate, description, ticketID, email))) {
      
        // noteIdTextField.setText("");
        ticketIDTextField.setText("");
        dateTextField.setText("");
        descriptionTextField.setText("");
        emailTextField.setText("");
       
    }
  }

  // update
  @FXML
  public void updateButtonClicked(ActionEvent event) {
    String noteIdStr = noteIDTextField.getText();
    String ticketIDStr = ticketIDTextField.getText();
    String date = dateTextField.getText();
    String newDescription = descriptionTextField.getText();
    String newEmail = emailTextField.getText();
  
    int ticketID = 0;
    int noteID = 0;
    Date newDate = new Date(0);

    try {
      ticketID = Integer.parseInt(ticketIDStr);
      noteID = Integer.parseInt(noteIdStr);
      newDate = Date.valueOf(date);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket or note ID");
    }

    if (successful(AssetPlusFeatureSet7Controller.updateMaintenanceNote(ticketID, noteID, newDate, newDescription, newEmail))) {
        ticketIDTextField.setText("");
        noteIDTextField.setText("");
        dateTextField.setText("");
        descriptionTextField.setText("");
        emailTextField.setText("");
       
    }
  }

  // delete
  @FXML
  public void deleteButtonClicked(ActionEvent event) {

    String ticketIDStr = ticketIDTextField.getText();
    String noteIdStr = noteIDTextField.getText();


    int ticketID = 0;
    int noteID = 0;
    try {
      ticketID = Integer.parseInt(ticketIDStr);
      noteID = Integer.parseInt(noteIdStr);
      } catch (Exception e) {
        ViewUtils.showError("Please intput a valid ticket or note ID");
      } 

    AssetPlusFeatureSet7Controller.deleteMaintenanceNote(ticketID, noteID);

    ticketIDTextField.setText("");
    noteIDTextField.setText("");
    dateTextField.setText("");
    descriptionTextField.setText("");
    emailTextField.setText("");

  }

}
