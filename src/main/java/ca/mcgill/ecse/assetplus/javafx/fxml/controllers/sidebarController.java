package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class sidebarController {

  private StackPane contentArea;
  


  public void goToAssets(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/SpecificAssetPage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);

  }

  public void goToAssetTypes(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/AssetTypePage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToEmployees(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/EmployeePage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToGuests(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/GuestsPage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToTickets(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/TicketsPage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToStatus(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/TicketStatusPage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToAccount(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/AccountPage,fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToSettings(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/SettingsPage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

  public void goToLogout(ActionEvent event){
    Parent fxml = FXMLLoader.load(getClass().getResource("../pages/LogoutPage.fxml"));
    contentArea.getChildren().removeAll();
    contentArea.getChildren().setAll(fxml);
  }

}
