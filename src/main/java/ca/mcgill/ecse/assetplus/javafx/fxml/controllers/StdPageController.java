package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;


public class StdPageController {
  @FXML private Label pageTitle;
  @FXML private Button search;
  @FXML private Button toggleView;
  @FXML private Button addEntry;
  @FXML private TableView dataTable;

  public void viewTicketsButtonClicked() { 
    pageTitle.setText("Maintenance Tickets");

    TableColumn<TOMaintenanceTicket, String> ticketIdColumn = new TableColumn<>("Ticket ID");
    ticketIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));

    TableColumn<TOMaintenanceTicket, String> raisedOnColumn = new TableColumn<>("Date Raised");
    raisedOnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedOnDate().toString()));

    TableColumn<TOMaintenanceTicket, String> descriptionColumn= new TableColumn<>("Description");  
    descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

    TableColumn<TOMaintenanceTicket, String> ticketRaiserColumn = new TableColumn<>("Ticket Raiser");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedByEmail()));

    TableColumn<TOMaintenanceTicket, String> assetNameColumn = new TableColumn<>("Asset Name");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetName()));

    TableColumn<TOMaintenanceTicket, String> expectedLifespanColumn = new TableColumn<>("Expected Lifespan");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getExpectLifeSpanInDays())));



    dataTable.getColumns().add(ticketIdColumn);
    dataTable.getColumns().add(ticketRaiserColumn);
    dataTable.getColumns().add(raisedOnColumn);
    dataTable.getColumns().add(descriptionColumn);
    dataTable.getColumns().add(assetNameColumn);
    dataTable.getColumns().add(expectedLifespanColumn);
    TableColumn<TOMaintenanceTicket, String> purchaseDateColumn = new TableColumn<>("Purchase Date");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseDate().toString()));
    dataTable.getColumns().add(purchaseDateColumn);
    TableColumn<TOMaintenanceTicket, String> floorNumberColumn = new TableColumn<>("Floor Number");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getFloorNumber())));
    dataTable.getColumns().add(floorNumberColumn);
    TableColumn<TOMaintenanceTicket, String> roomNumberColumn = new TableColumn<>("Room Number");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getRoomNumber())));
    dataTable.getColumns().add(roomNumberColumn);
    TableColumn<TOMaintenanceTicket, String> statusColumn = new TableColumn<>("Status");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
    dataTable.getColumns().add(statusColumn);
    TableColumn<TOMaintenanceTicket, String> fixedByColumn = new TableColumn<>("Fixed By Email");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFixedByEmail()));
    dataTable.getColumns().add(fixedByColumn);
    TableColumn<TOMaintenanceTicket, String> timeToResolveColumn = new TableColumn<>("Time To Resolve");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeToResolve()));
    dataTable.getColumns().add(timeToResolveColumn);
    TableColumn<TOMaintenanceTicket, String> priorityColumn = new TableColumn<>("Priority");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority()));
    dataTable.getColumns().add(priorityColumn);
    TableColumn<TOMaintenanceTicket, String> requiresApprovalColumn = new TableColumn<>("Requires Manager Approval");
    ticketRaiserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getApprovalRequired())));
    dataTable.getColumns().add(requiresApprovalColumn);
  } 
}
