package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.*;
import javafx.util.Callback;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
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

  private SVGPath deleteIcon;
  private SVGPath editIcon;


  // public void initialize() {
  //   deleteIcon = new SVGPath();
  //   deleteIcon.setContent("M14.6115 11.582H16.2173V10.0683C16.2173 9.63585 16.5278 9.34177 17.0068 9.34177H20.1828C20.6618 9.34177 20.9723 9.63585 20.9723 10.0683V11.582H22.5691V9.98183C22.5691 8.67575 21.6908 7.87134 20.2714 7.87134H16.9092C15.4898 7.87134 14.6115 8.67575 14.6115 9.98183V11.582ZM10.6727 12.6113H26.5258C26.8895 12.6113 27.1911 12.3086 27.1911 11.9539C27.1911 11.5993 26.8895 11.3052 26.5258 11.3052H10.6727C10.3178 11.3052 10.0073 11.5993 10.0073 11.9539C10.0073 12.3172 10.3178 12.6113 10.6727 12.6113ZM25.2039 12.4296H12.0034L12.58 25.2483C12.6244 26.3209 13.2897 26.9176 14.3809 26.9176H22.8176C23.9176 26.9176 24.5829 26.3209 24.6273 25.2483L25.2039 12.4296ZM15.7914 24.4438C15.4721 24.4438 15.2503 24.2449 15.2414 23.9336L14.9664 14.5142C14.9575 14.2115 15.1793 14.0039 15.5253 14.0039C15.8358 14.0039 16.0664 14.2028 16.0753 14.5055L16.3415 23.9336C16.3503 24.2363 16.1285 24.4438 15.7914 24.4438ZM18.6036 24.4438C18.2665 24.4438 18.027 24.2363 18.027 23.9336V14.5142C18.027 14.2115 18.2665 14.0039 18.6036 14.0039C18.9408 14.0039 19.1714 14.2115 19.1714 14.5142V23.9336C19.1714 24.2363 18.9408 24.4438 18.6036 24.4438ZM21.407 24.4438C21.0698 24.4438 20.8481 24.2363 20.857 23.9336L21.1231 14.5142C21.132 14.2028 21.3626 14.0039 21.6731 14.0039C22.0103 14.0039 22.2409 14.2115 22.232 14.5142L21.957 23.9336C21.9481 24.2449 21.7264 24.4438 21.407 24.4438Z");

  //   editIcon = new SVGPath();
  //   editIcon.setContent("M3.94903 14.4353H16.7504C17.1407 14.4353 17.4601 14.1239 17.4601 13.7433C17.4601 13.3627 17.1407 13.0427 16.7504 13.0427H5.38619L3.94903 14.4353ZM3.0619 13.6395L12.7405 4.21151L11.188 2.68919L1.50055 12.1259L0.65777 14.046C0.577928 14.2536 0.799711 14.4958 1.01262 14.4094L3.0619 13.6395ZM13.5212 3.46765L14.4172 2.61134C14.8697 2.17021 14.8874 1.69449 14.4881 1.29661L14.1865 1.00252C13.7874 0.613295 13.2995 0.656544 12.847 1.08037L11.951 1.94533L13.5212 3.46765Z");

  // }
  
 



  private void addButtonToTable(String buttonType) {

        TableColumn<Object, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<TableColumn<Object, Void>, TableCell<Object, Void>>() {
            
          @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                final TableCell<Object, Void> cell = new TableCell<Object, Void>() {

                    private Button btn = new Button(buttonType);

                    { 
                      if (buttonType == "Delete"){
                        btn.setOnAction((ActionEvent event) -> {
                            Object data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);        
                            AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(((TOMaintenanceTicket) data).getId());        
                            
                            ActionEvent refresh = new ActionEvent();
                            viewTicketsButtonClicked(refresh);
                            
                        });
                      }
                        
                        
                        btn.setStyle("-fx-background-color: #222222; ");
                    }
                    
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        dataTable.getColumns().add(colBtn);

    }



  @FXML 
  public void assetButtonClicked(ActionEvent event){
    //Set the title text and the add entry button
    pageTitle.setText("Assets");
    addButton.setText("Add Asset");

    //selectedPage


    //Clear 
    dataTable.getColumns().clear();
    dataTable.getItems().clear();


    AddAssetPopupController popUpController = new AddAssetPopupController();
    addButton.setOnAction(e -> popUpController.promptAddAssetPopUp(addButton));
    
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

    addButtonToTable("Edit");
    addButtonToTable("Delete");

    System.out.println("X");
  } 
  
}
