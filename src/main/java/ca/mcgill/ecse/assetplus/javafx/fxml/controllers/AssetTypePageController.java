package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller class for the Asset Type Page in the AssetPlus JavaFX application.
 * Handles user interactions and communicates with the underlying business logic
 * provided by {@link AssetPlusFeatureSet2Controller}.
 * @author Aurelia Bouliane (Goldrelia on github)
 */

public class AssetTypePageController {

    @FXML
    private TextField assetNameTextField;

    @FXML
    private TextField lifeSpanTextField;

    @FXML
    private Button addAssetTypeButton;

    @FXML
    private Button updateAssetTypeButton;

    @FXML
    private Button deleteAssetTypeButton;

    @FXML
    private TextField oldAssetNameTextField;

    @FXML
    private TextField newAssetNameTextField;

    @FXML
    private TextField newLifeSpanTextField;

    /**
     * Handles the "Add Asset Type" button click event.
     * Retrieves asset name and lifespan from text fields,
     * invokes {@link AssetPlusFeatureSet2Controller#addAssetType(String, int)},
     * and displays the result in an alert.
     *
     * @param event The ActionEvent triggered by the button click.
     */

    @FXML
    void addAssetType(ActionEvent event) {
        String name = assetNameTextField.getText();
        String lifeSpanStr = lifeSpanTextField.getText();

        try {
            int lifeSpan = Integer.parseInt(lifeSpanStr);
            String result = AssetPlusFeatureSet2Controller.addAssetType(name, lifeSpan);
            showAlert("Add Asset Type", result);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid lifespan. Please enter a valid number.");
        }
    }

    /**
     * Handles the "Update Asset Type" button click event.
     * Retrieves old name, new name, and new lifespan from text fields,
     * invokes {@link AssetPlusFeatureSet2Controller#updateAssetType(String, String, int)},
     * and displays the result in an alert.
     *
     * @param event The ActionEvent triggered by the button click.
     */

    @FXML
    void updateAssetType(ActionEvent event) {
        String oldName = oldAssetNameTextField.getText();
        String newName = newAssetNameTextField.getText();
        String newLifeSpanStr = newLifeSpanTextField.getText();

        try {
            int newLifeSpan = Integer.parseInt(newLifeSpanStr);
            String result = AssetPlusFeatureSet2Controller.updateAssetType(oldName, newName, newLifeSpan);
            showAlert("Update Asset Type", result);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid new lifespan. Please enter a valid number.");
        }
    }

    /**
     * Handles the "Delete Asset Type" button click event.
     * Retrieves asset name from the text field,
     * invokes {@link AssetPlusFeatureSet2Controller#deleteAssetType(String)},
     * and displays the result in an alert.
     *
     * @param event The ActionEvent triggered by the button click.
     */

    @FXML
    void deleteAssetType(ActionEvent event) {
        String name = assetNameTextField.getText();
        try {
            AssetPlusFeatureSet2Controller.deleteAssetType(name);
            showAlert("Delete Asset Type", "Asset Type deleted successfully.");
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    /**
     * Displays an information alert with the given title and content.
     *
     * @param title   The title of the alert.
     * @param content The content or message of the alert.
     */

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
