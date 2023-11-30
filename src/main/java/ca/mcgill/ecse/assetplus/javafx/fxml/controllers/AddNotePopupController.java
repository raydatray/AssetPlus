package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AddNotePopupController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField ticketIDTextField;

    public void setTicketID(int ticketID) {
        if (ticketIDTextField != null) {
            ticketIDTextField.setText(String.valueOf(ticketID));
        }
    }

    @FXML
    public void promptAddNotePopUp(Button updateButton, int ticketID) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/.AddNotePopUpfxml"));
            Parent root = loader.load();

            // Autofill the ticket textfield
            AddNotePopupController controller = loader.getController();
            controller.setTicketID(ticketID);

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.setTitle("Add Note to Ticket");
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
    public void addButtonClicked() throws NumberFormatException, ParseException {

        String ticketId = ticketIDTextField.getText();

        java.sql.Date date = Date.valueOf(datePicker.getValue());
        String email = emailTextField.getText();
        String description = descriptionTextField.getText();

        try {
            String error = AssetPlusFeatureSet7Controller.addMaintenanceNote(date, description, Integer.parseInt(ticketId), email);

            if (!error.equals("")) {
                ViewUtils.showError(error);

                ViewUtils.closeWindow(addButton);

            }
        } catch (Exception e) {
            ViewUtils.showError(e.getMessage());
        } finally {
            ViewUtils.closeWindow(addButton);
        }
    }

    public void handleCloseButtonClick() {
        ViewUtils.closeWindow(addButton);
    }

}


