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
import java.time.LocalDate;
import java.util.List;

public class AddTicketPopUpController {

  @FXML private Button addTicketButton;
  @FXML private ChoiceBox<String> assetNumberChoiceBox;
  @FXML private Button closePopUpButton;
  @FXML private DatePicker dateRaisedDatePicker;
  @FXML private TextField descriptionTextField;
  @FXML private TextField ticketIdTextField;

  StdPageController topController;

  public AddTicketPopUpController(StdPageController headController){
    this.topController = headController;
  }
  @FXML private ChoiceBox<String> ticketRaiserEmailChoiceBox;

  @FXML
  public void populateChoiceBoxes() {
    // Asset number choice box
    List<TOSpecificAsset> assetList = AssetPlusFeatureSet3Controller.getSpecificAssets();

    ObservableList<String> assetNumberList = FXCollections.observableArrayList();
    
    assetNumberList.addAll("-- Select an asset number --");
    for (TOSpecificAsset asset : assetList) {
      assetNumberList.add(String.valueOf(asset.getAssetNumber()));
    }

    assetNumberChoiceBox.setItems(assetNumberList);
    assetNumberChoiceBox.setValue(assetNumberList.get(0));

    // Ticket raiser email choice box
    List<TOUser> userList = AssetPlusFeatureSet1Controller.getUsers();
    ObservableList<String> userEmailList = FXCollections.observableArrayList();
    userEmailList.addAll("-- Select a ticket raiser email -- ");
    userEmailList.addAll("manager@ap.com");
    for (TOUser user : userList) {
      userEmailList.add(user.getEmail());
    }
    ticketRaiserEmailChoiceBox.setItems(userEmailList);
    ticketRaiserEmailChoiceBox.setValue(userEmailList.get(0));

  }

  @FXML
  public void promptAddTicketPopUp() {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/AddMaintenanceTicketPopUp.fxml"));
      loader.setControllerFactory(param -> new AddTicketPopUpController(topController));
      Parent root = loader.load();

      AddTicketPopUpController controller = loader.getController();
      controller.populateChoiceBoxes();

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Maintenance Ticket");
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
  public void addMaintenanceTicket() {
    LocalDate dateRaisedValue = dateRaisedDatePicker.getValue();
    if (dateRaisedValue == null) {
        ViewUtils.showError("Please select a raised date");
        return;
    }

    String assetNumberValue = assetNumberChoiceBox.getValue();
    java.sql.Date dateRaised = java.sql.Date.valueOf(dateRaisedValue);
    String description = descriptionTextField.getText();
    String raiserEmail = ticketRaiserEmailChoiceBox.getValue();
    String ticketIdValue = ticketIdTextField.getText();

    try {
      int assetNumber = Integer.parseInt(assetNumberValue);
      int ticketId = Integer.parseInt(ticketIdValue);

      String error = AssetPlusFeatureSet4Controller.addMaintenanceTicket(ticketId, dateRaised, description, raiserEmail, assetNumber);
      if (!error.equals("")) {
        ViewUtils.showError(error);  
        return;
      }

      topController.refreshTable("Tickets");
      ViewUtils.closeWindow(addTicketButton);
    } catch (NumberFormatException e) {
      ViewUtils.showError("Invalid inputs provided.");
    }
  }

  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(ticketIdTextField);
  }

}

