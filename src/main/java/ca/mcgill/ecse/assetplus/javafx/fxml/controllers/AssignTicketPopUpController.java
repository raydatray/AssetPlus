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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class AssignTicketPopUpController {

    @FXML
    private Button assignTicketButton;

    @FXML
    private Button closePopUpButton;

    @FXML
    private ToggleButton requiresManagerApprovalToggle;

    @FXML
    private TextField staffMemberEmailTextField;

    @FXML
    private TextField ticketIDTextField;

    @FXML
    private TextField timeToResolveTextField;

    @FXML
    private TextField priorityLevelTextField;

    public void setTicketID(int ticketID) {
        if (ticketIDTextField != null) {
            ticketIDTextField.setText(String.valueOf(ticketID));
        }
    }

    @FXML
    public void promptAssignTicketPopUp(Button updateButton, int ticketID) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AssignMaintenanceTicketPopup.fxml"));
            Parent root = loader.load();

            // Autofill the ticket textfield
            AssignTicketPopUpController controller = loader.getController();
            controller.setTicketID(ticketID);

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.setTitle("Assign Maintenance Ticket");
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
    public void handleAssignButtonClick() throws NumberFormatException, ParseException {

        String ticketId = ticketIDTextField.getText();

        String staffMemberEmail = staffMemberEmailTextField.getText();
        String timeToResolve = timeToResolveTextField.getText();
        String priority = priorityLevelTextField.getText();
        boolean requiresManagerApproval = requiresManagerApprovalToggle.isSelected();

        try {
            String error = AssetPlusFeatureSet4Controller.AssignHotelStaffToMaintenanceTicket(
                    Integer.parseInt(ticketId), staffMemberEmail, timeToResolve, priority, requiresManagerApproval,
                    "manager@ap.com");

            if (!error.equals("")) {
                ViewUtils.showError(error);

                ViewUtils.closeWindow(assignTicketButton);

            }
        } catch (Exception e) {
            ViewUtils.showError(e.getMessage());
        } finally {
            ViewUtils.closeWindow(assignTicketButton);
        }
    }

    public void handleCloseButtonClick() {
        ViewUtils.closeWindow(assignTicketButton);
    }

}
