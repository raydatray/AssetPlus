package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarController {
  @FXML private Button ticketButton;

  @FXML
  public void addButtonClicked(ActionEvent event) {
    StdPageController t = new StdPageController();
    t.viewTicketsButtonClicked();
  }
}
