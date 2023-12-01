package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.text.ParseException;
import java.util.List;

public class AssignTicketPopUpController {
    @FXML private Button closePopUpButton;
    @FXML private Button confirmButton;
    @FXML private ChoiceBox<String> priorityLevelChoiceBox;
    @FXML private ChoiceBox<String> requiresApprovalChoiceBox;
    @FXML private ChoiceBox<String> selectEmployeeChoiceBox;
    @FXML private TextField ticketIdTextField;
    @FXML private ChoiceBox<String> timeToResolveChoiceBox;

    StdPageController topController;

    public AssignTicketPopUpController(StdPageController headController){
        this.topController = headController;
    }

    public void setFieldsAndChoiceBoxes(int ticketID) {
        ticketIdTextField.setText(String.valueOf(ticketID));
        ticketIdTextField.setDisable(true);
        
        requiresApprovalChoiceBox.getItems().addAll("Yes", "No");
        requiresApprovalChoiceBox.setValue("No");

        priorityLevelChoiceBox.getItems().addAll("Urgent", "Normal", "Low");
        priorityLevelChoiceBox.setValue("Normal");

        timeToResolveChoiceBox.getItems().addAll("LessThanADay", "OneToThreeDays", "ThreeToSevenDays", "OneToThreeWeeks", "ThreeOrMoreWeeks");
        timeToResolveChoiceBox.setValue("LessThanADay");

        List<TOUser> userList = AssetPlusFeatureSet1Controller.getUsers();

        ObservableList<String> hotelStaffList = FXCollections.observableArrayList();
        
        hotelStaffList.add("-- Select a hotel staff --");
        hotelStaffList.add("manager@ap.com");

        for (TOUser user : userList) {
            if (user.getEmail().endsWith("ap.com")) {
            hotelStaffList.add(user.getEmail());
            }
        }     
        
        selectEmployeeChoiceBox.setItems(hotelStaffList);
        selectEmployeeChoiceBox.setValue(hotelStaffList.get(0));
    }

    @FXML
    public void promptAssignTicketPopUp(Button updateButton, int ticketID) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AssignMaintenanceTicketPopup.fxml"));
            loader.setControllerFactory(param -> new AssignTicketPopUpController(topController));
            Parent root = loader.load();

            // Autofill the ticket textfield
            AssignTicketPopUpController controller = loader.getController();
            controller.setFieldsAndChoiceBoxes(ticketID);

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
    public void confirmAssignEmployee() throws NumberFormatException, ParseException {

        String ticketId = ticketIdTextField.getText();

        String staffMemberEmail = selectEmployeeChoiceBox.getValue();
        String timeToResolve = timeToResolveChoiceBox.getValue();
        String priority = priorityLevelChoiceBox.getValue();
        boolean requiresManagerApproval = requiresApprovalChoiceBox.getValue() == "Yes" ? true : false;

        try {
            String error = AssetPlusFeatureSet4Controller.AssignHotelStaffToMaintenanceTicket(
                    Integer.parseInt(ticketId), staffMemberEmail, timeToResolve, priority, requiresManagerApproval,
                    "manager@ap.com");

            if (!error.equals("")) {
                ViewUtils.showError(error);
                return;
            }

            topController.refreshTable("Status");
            ViewUtils.closeWindow(closePopUpButton);
        } catch (Exception e) {
            ViewUtils.showError("Invalid inputs provided.");
        } 
    }

    public void closePopUp() {
        ViewUtils.closeWindow(closePopUpButton);
    }

}
