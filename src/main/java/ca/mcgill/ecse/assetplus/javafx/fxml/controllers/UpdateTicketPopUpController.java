package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.List;

public class UpdateTicketPopUpController {
  @FXML private Button closePopUpButton;
  @FXML private ChoiceBox<String> newAssetNumberChoiceBox;
  @FXML private DatePicker newDateRaisedDatePicker;
  @FXML private TextField newDescriptionTextField;
  @FXML private ChoiceBox<String> newTickerRaiserEmailChoiceBox;
  @FXML private TextField ticketIDTextField;
  @FXML private Button updateTicketButton;

  StdPageController topController;

  public UpdateTicketPopUpController(StdPageController headController) {
    this.topController = headController;
  }

  public void initializePopUpFields(int ticketID) {
    ticketIDTextField.setText(String.valueOf(ticketID));
    ticketIDTextField.setDisable(true);

    // Asset number choice box
    List<TOSpecificAsset> assetList = AssetPlusFeatureSet3Controller.getSpecificAssets();
    ObservableList<String> assetNumberList = FXCollections.observableArrayList();
    assetNumberList.addAll("-- Select an asset number --");
    for (TOSpecificAsset asset : assetList) {
      assetNumberList.add(String.valueOf(asset.getAssetNumber()));
    }
    newAssetNumberChoiceBox.setItems(assetNumberList);
    newAssetNumberChoiceBox.setValue(assetNumberList.get(0));

    // Ticket raiser email choice box
    List<TOUser> userList = AssetPlusFeatureSet1Controller.getUsers();
    ObservableList<String> userEmailList = FXCollections.observableArrayList();
    userEmailList.addAll("-- Select a ticket raiser email -- ");
    userEmailList.add("manager@ap.com");
    for (TOUser user : userList) {
      userEmailList.add(user.getEmail());
    }
    newTickerRaiserEmailChoiceBox.setItems(userEmailList);
    newTickerRaiserEmailChoiceBox.setValue(userEmailList.get(0));
  }


  @FXML
  public void promptUpdateTicketPopUp(int ticketID) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateMaintenanceTicketPopup.fxml"));
      loader.setControllerFactory(param -> new UpdateTicketPopUpController(topController));
      Parent root = loader.load();
      
      // Autofill the ticket textfield
      UpdateTicketPopUpController controller = loader.getController();
      controller.initializePopUpFields(ticketID); 

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Update Maintenance Ticket");
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
  public void handleUpdateTicketButtonClick() {  
    try {
      String assetNumberValue = newAssetNumberChoiceBox.getValue();
      java.sql.Date newDateRaised = java.sql.Date.valueOf(newDateRaisedDatePicker.getValue());
      String newDescription = newDescriptionTextField.getText();
      String newRaiserEmail = newTickerRaiserEmailChoiceBox.getValue();
      String ticketIdValue = ticketIDTextField.getText();

      String error = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(Integer.parseInt(ticketIdValue), newDateRaised, newDescription, newRaiserEmail, Integer.parseInt(assetNumberValue));
      if (!error.equals("")) {
        ViewUtils.showError(error);
        return;
      }

      topController.refreshTable("Tickets");
      ViewUtils.closeWindow(updateTicketButton);
    } catch (Exception e) {
      ViewUtils.showError("Invalid inputs provided.");
    }
  }

  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(updateTicketButton);
  }

}

