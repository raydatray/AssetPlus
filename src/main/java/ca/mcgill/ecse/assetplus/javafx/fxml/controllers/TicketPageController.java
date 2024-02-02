package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.successful;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.Button;

public class TicketPageController {
  @FXML private TextField idTextField;
  @FXML private TextField descriptionTextField;
  @FXML private TextField assetTextField;
  @FXML private TextField ticketRaiserTextField;
  @FXML private TextField timeToResolveTextField;
  @FXML private TextField priorityTextField;
  @FXML private TextField assignToTextField;
  @FXML private TextField dissaproveNoteTextField;
  @FXML private TextField dateTextField;
  @FXML private Button assignButton;
  @FXML private Button startButton;
  @FXML private Button completeButton;
  @FXML private Button approveButton;
  @FXML private Button disapproveButton;
  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;
  @FXML private Toggle requiresApprovalToggle;

  @FXML
  public void addButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    String date = dateTextField.getText();
    String description = descriptionTextField.getText();
    String ticketRaiser = ticketRaiserTextField.getText();
    String asset = assetTextField.getText();
    int ticketId = 0;
    Date raisedOnDate = new Date(0);
    int assetNumber = -1;
    try {
      ticketId = Integer.parseInt(id);
      raisedOnDate = Date.valueOf(date);
      assetNumber = Integer.parseInt(asset);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket id");
    }
    if (successful(AssetPlusFeatureSet4Controller.addMaintenanceTicket(ticketId, raisedOnDate, description, ticketRaiser, assetNumber))) {
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }

  @FXML
  public void updateButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    String newDate = dateTextField.getText();
    String newDescription = descriptionTextField.getText();
    String newTicketRaiser = ticketRaiserTextField.getText();
    String newAsset = assetTextField.getText();
    int newTicketId = 0;
    Date newRaisedOnDate = new Date(0);
    int newAssetNumber = -1;
    try {
      newTicketId = Integer.parseInt(id);
      newRaisedOnDate = Date.valueOf(newDate);
      newAssetNumber = Integer.parseInt(newAsset);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket id");
    }
    if (successful(AssetPlusFeatureSet4Controller.updateMaintenanceTicket(newTicketId, newRaisedOnDate, newDescription, newTicketRaiser, newAssetNumber))) {
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }

  @FXML
  public void deleteButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    int ticketId = 0;
    try {
      ticketId = Integer.parseInt(id);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket id");
    }
    AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticketId);
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
  }

  @FXML
  public void assignButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    String assignTo = assignToTextField.getText();
    String timeToResolve = timeToResolveTextField.getText();
    String priority = priorityTextField.getText();
    boolean requiresApproval = requiresApprovalToggle.isSelected();
    int ticketId = 0;
    try {
      ticketId = Integer.parseInt(id);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket id");
    }
    if (successful(AssetPlusFeatureSet4Controller.AssignHotelStaffToMaintenanceTicket(ticketId, assignTo, timeToResolve, priority, requiresApproval, "manager@ap.com"))){
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }

  @FXML
  public void startButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    if (successful(AssetPlusFeatureSet4Controller.StartWorkOnMaintenanceTicket(id))){
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }

  @FXML
  public void completeButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    if (successful(AssetPlusFeatureSet4Controller.CompleteWorkOnMaintenanceTicket(id))){
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }

  @FXML
  public void approveButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    if (successful(AssetPlusFeatureSet4Controller.ApproveWorkOnMaintenanceTicket(id))){
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }

  @FXML
  public void disapproveButtonClicked(ActionEvent event) {
    String id = idTextField.getText();
    String date = dateTextField.getText();
    String noteRejection = dissaproveNoteTextField.getText();
    int ticketId = -1;
    Date rejectionDate = new Date(0);
    try {
      ticketId = Integer.parseInt(id);
      rejectionDate = Date.valueOf(date);
    } catch (Exception e) {
      ViewUtils.showError("Please input a valid ticket id");
    }
    if (successful(AssetPlusFeatureSet4Controller.DisapproveWorkOnMaintenanceTicket(ticketId, rejectionDate, noteRejection))){
        idTextField.setText("");
        descriptionTextField.setText("");
        dateTextField.setText("");
        ticketRaiserTextField.setText("");
        assetTextField.setText("");
        assignToTextField.setText("");
        dissaproveNoteTextField.setText("");
        timeToResolveTextField.setText("");
        priorityTextField.setText("");
        requiresApprovalToggle.setSelected(false);
    }
  }
}
