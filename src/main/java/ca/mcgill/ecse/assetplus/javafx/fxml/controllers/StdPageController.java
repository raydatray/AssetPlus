package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class StdPageController {
  @FXML private Label pageTitle;
  @FXML private Button search;
  @FXML private Button toggleView;
  @FXML private Button addButton;

  @FXML private Button assetsButton;
  @FXML private Button assetsTypeButton;
  @FXML private Button usersButton;
  @FXML private Button ticketsButton;
  @FXML private Button statusButton;
  @FXML private Button settingsButton;
  @FXML private Button logoutButton;
  
  @FXML private TableView dataTable;

  //have a flag variable for current page

  @FXML 
  public void assetButtonClicked(ActionEvent event){
    pageTitle.setText("Assets");
    addButton.setText("Add Asset");

    dataTable.getColumns().clear();
    dataTable.getItems().clear();
    
    List<TOSpecificAsset> specificAssets= AssetPlusFeatureSet3Controller.getSpecificAssets();
    dataTable.getItems().addAll(specificAssets);

    TableColumn<TOAssetType, String> assetNumColumn = new TableColumn<>("Asset Number");
    assetNumColumn.setCellValueFactory(new PropertyValueFactory<>("assetNumber"));
    dataTable.getColumns().add(assetNumColumn);

    TableColumn<TOAssetType, String> assetTypeColumn = new TableColumn<>("Asset Type");
    assetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
    dataTable.getColumns().add(assetTypeColumn);

    TableColumn<TOAssetType, String> floorNumColumn = new TableColumn<>("Floor Number");
    floorNumColumn.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
    dataTable.getColumns().add(floorNumColumn); 

    TableColumn<TOAssetType, String> roomNumColumn = new TableColumn<>("Room Number");
    roomNumColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    dataTable.getColumns().add(roomNumColumn); 

    TableColumn<TOAssetType, String> purchaseDateColumn = new TableColumn<>("Purchase Date");
    purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
    dataTable.getColumns().add(purchaseDateColumn); 
  }

  @FXML
  public void assetTypeButtonClicked(ActionEvent event){
    pageTitle.setText("Asset Types");
    addButton.setText("Add Type");

    dataTable.getColumns().clear();
    dataTable.getItems().clear();
    
    List<TOAssetType> assetTypes= AssetPlusFeatureSet2Controller.getAssetTypes();
    dataTable.getItems().addAll(assetTypes);

    TableColumn<TOAssetType, String> assetNameColumn = new TableColumn<>("Name");
    assetNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    dataTable.getColumns().add(assetNameColumn);

    TableColumn<TOAssetType, String> expectedLifeColumn = new TableColumn<>("Expected Lifespan");
    expectedLifeColumn.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
    dataTable.getColumns().add(expectedLifeColumn);
  }

  @FXML 
  public void usersButtonClicked(ActionEvent event) {
    pageTitle.setText("Users");
    addButton.setText("Add User");

    AddUserPopUpController popUpController = new AddUserPopUpController();
    addButton.setOnAction(e -> popUpController.promptAddUserPopUp(addButton));

    dataTable.getColumns().clear();
    dataTable.getItems().clear();
    
    List<TOUser> userList = AssetPlusFeatureSet1Controller.getUsers();
    dataTable.getItems().addAll(userList);

    TableColumn<TOAssetType, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    dataTable.getColumns().add(nameColumn);

    TableColumn<TOAssetType, String> emailColumn = new TableColumn<>("Email");
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    dataTable.getColumns().add(emailColumn);

    TableColumn<TOAssetType, String> phoneColumn = new TableColumn<>("Phone");
    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    dataTable.getColumns().add(phoneColumn);
  }
  
  @FXML
  public void viewTicketsButtonClicked(ActionEvent event) { 
    pageTitle.setText("Maintenance Tickets");
    addButton.setText("Add Ticket");

    dataTable.getColumns().clear();
    dataTable.getItems().clear();

    List<TOMaintenanceTicket> tickets= AssetPlusFeatureSet6Controller.getTickets();
    dataTable.getItems().addAll(tickets);

    TableColumn<TOMaintenanceTicket, String> ticketIdColumn = new TableColumn<>("Ticket ID");
    ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    dataTable.getColumns().add(ticketIdColumn);

    TableColumn<TOMaintenanceTicket, String> priorityColumn = new TableColumn<>("Priority");
    priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
    dataTable.getColumns().add(priorityColumn);

    TableColumn<TOMaintenanceTicket, String> statusColumn = new TableColumn<>("Status");
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    dataTable.getColumns().add(statusColumn);

    TableColumn<TOMaintenanceTicket, String> timeColumn = new TableColumn<>("Time To Resolve");
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeToResolve"));
    dataTable.getColumns().add(timeColumn);

    TableColumn<TOMaintenanceTicket, String> approvalColumn = new TableColumn<>("Approval Required");
    approvalColumn.setCellValueFactory(new PropertyValueFactory<>("approvalRequired"));
    dataTable.getColumns().add(approvalColumn);

    TableColumn<TOMaintenanceTicket, String> descriptionColumn = new TableColumn<>("Description");
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    dataTable.getColumns().add(descriptionColumn);

    TableColumn<TOMaintenanceTicket, String> raisedDateColumn = new TableColumn<>("Raised Date");
    raisedDateColumn.setCellValueFactory(new PropertyValueFactory<>("raisedOnDate"));
    dataTable.getColumns().add(raisedDateColumn);

    TableColumn<TOMaintenanceTicket, String> raiserColumn = new TableColumn<>("Raiser");
    raiserColumn.setCellValueFactory(new PropertyValueFactory<>("raisedByEmail"));
    dataTable.getColumns().add(raiserColumn);

    TableColumn<TOMaintenanceTicket, String> assetColumn = new TableColumn<>("Asset Name");
    assetColumn.setCellValueFactory(new PropertyValueFactory<>("assetName"));
    dataTable.getColumns().add(assetColumn);

    TableColumn<TOMaintenanceTicket, String> expectedLifeColumn = new TableColumn<>("Expected Lifespan");
    expectedLifeColumn.setCellValueFactory(new PropertyValueFactory<>("expectLifeSpanInDays"));
    dataTable.getColumns().add(expectedLifeColumn);

    TableColumn<TOMaintenanceTicket, String> purchaseDateColumn = new TableColumn<>("Purchase Date");
    purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
    dataTable.getColumns().add(purchaseDateColumn);

    TableColumn<TOMaintenanceTicket, String> floorNumberColumn = new TableColumn<>("Floor Number");
    floorNumberColumn.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
    dataTable.getColumns().add(floorNumberColumn);


    TableColumn<TOMaintenanceTicket, String> roomNumberColumn = new TableColumn<>("Room Number");
    roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    dataTable.getColumns().add(roomNumberColumn);

    TableColumn<TOMaintenanceTicket, String> fixerColumn = new TableColumn<>("Fixer");
    fixerColumn.setCellValueFactory(new PropertyValueFactory<>("fixedByEmail"));
    dataTable.getColumns().add(fixerColumn);

    //NOTES AND IMAGES
  } 
  
}
