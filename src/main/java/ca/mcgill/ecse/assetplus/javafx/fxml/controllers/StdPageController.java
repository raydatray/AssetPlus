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

  private String currentPage;

  private SVGPath deleteIcon;
  private SVGPath editIcon;

  public void refreshTable(String currentPage){
    ActionEvent refresh = new ActionEvent();

    switch(currentPage) {
      case "Assets":
        assetButtonClicked(refresh);
        break;
      case "AssetTypes":
        assetTypeButtonClicked(refresh);
        break;
      case "Users":
        usersButtonClicked(refresh);
        break;
      case "Tickets":
        viewTicketsButtonClicked(refresh);
        break;
      case "Status":
        statusButtonClicked(refresh);
        break;
    }
  }


  private void addButtonToTable(String buttonType, String currentPage) {

        TableColumn<Object, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<TableColumn<Object, Void>, TableCell<Object, Void>>() {
            
          @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                final TableCell<Object, Void> cell = new TableCell<Object, Void>() {

                    private Button btn = new Button(buttonType);

                    { 

                      switch(buttonType){
                        case "Delete":

                          btn.setOnAction((ActionEvent event) -> {
                            Object data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);

                            ActionEvent refresh = new ActionEvent();
                            
                            switch(currentPage){
                              case "Assets":
                                AssetPlusFeatureSet3Controller.deleteSpecificAsset(((TOSpecificAsset) data).getAssetNumber());        
                                                                
                                assetButtonClicked(refresh);
                                break;
                              case "AssetTypes":
                                AssetPlusFeatureSet2Controller.deleteAssetType(((TOAssetType) data).getName());        
                                                           
                                assetTypeButtonClicked(refresh);
                                break;

                              case "Users":
                                AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(((TOUser) data).getEmail());        
                                
                                usersButtonClicked(refresh);
                                break;
                              
                              case "Tickets":
                                AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(((TOMaintenanceTicket) data).getId());        
                                                                
                                viewTicketsButtonClicked(refresh);
                              break;
                            }
                          });
                          break;

                        case "Edit":
                          btn.setOnAction((ActionEvent event) -> {
                            Object data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);

                            ActionEvent refresh = new ActionEvent();
                            
                            switch(currentPage){
                              case "Assets":
                                //send the data to the contoller method!!
                                                                
                                assetButtonClicked(refresh);
                                break;
                              case "AssetTypes":
                                //send the data to the contoller method!!
                                                           
                                assetTypeButtonClicked(refresh);
                                break;

                              case "Users":
                                TOUser selectedUser = (TOUser) data;
                                String selectedUserEmail = selectedUser.getEmail();
                                Boolean isValidUserAndEmail = (selectedUser != null && selectedUserEmail != null);

                                UpdateUserPopUpController popUpUpdateUserController = new UpdateUserPopUpController();
                          
                                // Check if the data is a TOUser instance and if TOUset has an email
                                // If so, pass it to the updateUserPopUpController
                                if (isValidUserAndEmail) {
                                  popUpUpdateUserController.promptUpdatePopUp(addButton, selectedUserEmail);
                                } else {
                                  // Handle the case where data is not a TOUser or doesn't have an email
                                  ViewUtils.showError("Invalid user data.");
                                }

                                usersButtonClicked(refresh);
                                break;
                              
                              case "Tickets":
                                //send the data to the contoller method!!
                                TOMaintenanceTicket selectedTicket = (TOMaintenanceTicket) data;
                                int selectedTicketID = selectedTicket.getId();
                                Boolean isValidTicketID = (selectedTicket != null && selectedTicketID != 0);

                                UpdateTicketPopUpController popUpUpdateTicketController = new UpdateTicketPopUpController();
                          
                                // Check if the data is a TOMaintenanceTicket instance and if TOMaintenanceTicket has an id
                                // If so, pass it to the updateTicketPopUpController
                                if (isValidTicketID) {
                                  popUpUpdateTicketController.promptUpdateTicketPopUp(addButton, selectedTicketID);
                                } else {
                                  // Handle the case where data is not a TOUser or doesn't have an email
                                  ViewUtils.showError("Invalid user data.");
                                }

                                                
                                viewTicketsButtonClicked(refresh);
                                break;
                            }
                          });
                          break;
                        
                        case "Assign":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              //send the data to the contoller method!!
                              
                              ActionEvent refresh = new ActionEvent();
                              viewTicketsButtonClicked(refresh);
                          
                          });
                          break;

                        case "Start":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        

                              AssetPlusFeatureSet4Controller.StartWorkOnMaintenanceTicket(Integer.toString(((TOMaintenanceTicket) data).getId()));
                                                         
                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;

                        case "Approve":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              AssetPlusFeatureSet4Controller.ApproveWorkOnMaintenanceTicket(Integer.toString(((TOMaintenanceTicket) data).getId()));
                              
                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;

                        case "Disapprove":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              //send the data to the contoller method!!

                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;

                        case "Complete":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              AssetPlusFeatureSet4Controller.CompleteWorkOnMaintenanceTicket(Integer.toString(((TOMaintenanceTicket) data).getId()));
                              
                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;

                        case "Images":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              //send the data to the contoller method!!
                              
                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;

                        case "Notes":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              //send the data to the contoller method!!
                              
                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;
                      }

                      // if (buttonType.equals("Delete")){

                      //   btn.setOnAction((ActionEvent event) -> {
                      //       Object data = getTableView().getItems().get(getIndex());
                      //       System.out.println("selectedData: " + data);        
                      //       AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(((TOMaintenanceTicket) data).getId());        
                            
                      //       ActionEvent refresh = new ActionEvent();
                      //       viewTicketsButtonClicked(refresh);
                        
                      //   });

                      // } else if (buttonType.equals("Edit")){

                      //   btn.setOnAction((ActionEvent event) -> {
                      //       Object data = getTableView().getItems().get(getIndex());
                      //       System.out.println("selectedData: " + data);        
                            
                      //       //wtv alice & aure makes -> data
                            
                      //       ActionEvent refresh = new ActionEvent();
                      //       viewTicketsButtonClicked(refresh);
                        
                      //   });
                      // }
                        
                        
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
    addButton.setVisible(true);

    currentPage = "Assets";

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

    addButtonToTable("Edit", currentPage);
    addButtonToTable("Delete", currentPage);
  }

  @FXML
  public void assetTypeButtonClicked(ActionEvent event){
    pageTitle.setText("Asset Types");
    addButton.setText("Add Type");
    addButton.setVisible(true);
    currentPage = "AssetTypes";

    AddAssetTypePopupController popUpController = new AddAssetTypePopupController();
    addButton.setOnAction(e -> popUpController.promptAddAssetTypePopup(addButton));

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

    addButtonToTable("Edit", currentPage);
    addButtonToTable("Delete", currentPage);
  }

  @FXML 
  public void usersButtonClicked(ActionEvent event) {
    pageTitle.setText("Users");
    addButton.setText("Add User");
    addButton.setVisible(true);
    currentPage = "Users";


    AddUserPopUpController popUpController = new AddUserPopUpController();
    addButton.setOnAction(e -> popUpController.promptAddUserPopUp());

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

    addButtonToTable("Edit", currentPage);
    addButtonToTable("Delete", currentPage);
  }
  
  @FXML
  public void viewTicketsButtonClicked(ActionEvent event) { 
    AddTicketPopUpController ticketPopUpController = new AddTicketPopUpController();
    addButton.setOnAction(e -> ticketPopUpController.promptAddTicketPopUp());
    
    pageTitle.setText("Maintenance Tickets");
    addButton.setText("Add Ticket");
    addButton.setVisible(true);
    currentPage = "Tickets";
 

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
    addButtonToTable("Notes", currentPage);
    addButtonToTable("Images", currentPage);
    addButtonToTable("Edit", currentPage);
    addButtonToTable("Delete", currentPage);
  }

  @FXML
  public void statusButtonClicked(ActionEvent event) { 
    pageTitle.setText("Status");
    addButton.setText("Add Ticket");
    addButton.setVisible(false);
    currentPage = "Status";
  

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

    TableColumn<TOMaintenanceTicket, String> raiserColumn = new TableColumn<>("Raiser");
    raiserColumn.setCellValueFactory(new PropertyValueFactory<>("raisedByEmail"));
    dataTable.getColumns().add(raiserColumn);

    TableColumn<TOMaintenanceTicket, String> fixerColumn = new TableColumn<>("Fixer");
    fixerColumn.setCellValueFactory(new PropertyValueFactory<>("fixedByEmail"));
    dataTable.getColumns().add(fixerColumn);

    addButtonToTable("Assign", currentPage);
    addButtonToTable("Start", currentPage);
    addButtonToTable("Complete", currentPage);
    addButtonToTable("Approve", currentPage);
    addButtonToTable("Disapprove", currentPage);

}
}
