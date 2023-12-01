package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
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

public class UpdateAssetTypePopUpController {
  @FXML private Button closeButton;
  @FXML private TextField expectedLifeSpanTextField;
  @FXML private TextField newAssetNameTextField;
  @FXML private TextField oldAssetNameTextField;
  @FXML private Button updateAssetButton;

  StdPageController topController;

  public UpdateAssetTypePopUpController(StdPageController headController){
    this.topController = headController;
  }

  @FXML
  public void closePopUp() {
    ViewUtils.closeWindow(closeButton);
  }

  @FXML
  public void updateSpecificAsset() {
    String oldName = oldAssetNameTextField.getText();
    String newName = newAssetNameTextField.getText();
    String newExpectedLifeSpan = expectedLifeSpanTextField.getText();

    if (oldName.isEmpty() || newName.isEmpty() || newExpectedLifeSpan.isEmpty()) {
      ViewUtils.showError("Please fill in all the fields");
      return;
    }

    try {
      int newExpectedLifeSpanInt = Integer.parseInt(newExpectedLifeSpan);

      String error = AssetPlusFeatureSet2Controller.updateAssetType(oldName, newName, newExpectedLifeSpanInt);

      if (error != "") {
        ViewUtils.showError(error);
        return;
      }

      topController.refreshTable("AssetTypes");
      ViewUtils.closeWindow(updateAssetButton);
    } catch (NumberFormatException e) {
      ViewUtils.showError("Expected life span must be a number");
    } 
  }

  @FXML
  public void promptUpdateAssetTypePopUp(String oldAssetName) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/UpdateAssetTypePopUp.fxml"));
      loader.setControllerFactory(param -> new UpdateAssetTypePopUpController(topController));
      Parent root = loader.load();
      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Maintenance Ticket");
      // Set the content from the FXML file
      Scene scene = new Scene(root);
      scene.setFill(Color.TRANSPARENT);
      popupStage.setScene(scene);
      // Inject auto-fill
      UpdateAssetTypePopUpController controller = loader.getController();
      controller.setOldName(oldAssetName);
      // Show the pop-up
      popupStage.show();
    } catch (Exception e) {
      // Maybe prompt error pop up in case or error?
      ViewUtils.showError(e.getMessage());
    } 
  }

  public void setOldName(String oldName) {
    if (oldName != null) {
        oldAssetNameTextField.setText(oldName);
        oldAssetNameTextField.setDisable(true);
    }
  }
}
