package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.*;
import javafx.util.Callback;
import java.util.ArrayList;
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
  @FXML private Button filterButton;

  @FXML private Button assetsButton;
  @FXML private Button assetsTypeButton;
  @FXML private Button usersButton;
  @FXML private Button ticketsButton;
  @FXML private Button statusButton;
  @FXML private Button settingsButton;
  @FXML private Button logoutButton;
  @FXML private TextField emailTextField;
  
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


  private void addButtonToTable(String buttonType, String currentPage, StdPageController topController) {

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
                            //ActionEvent refresh = new ActionEvent();
                            //FORCE OVERWRITE 
                            
                            switch(currentPage){
                              case "Assets":
                                UpdateAssetPopupController popupController = new UpdateAssetPopupController(topController);
                                btn.setOnAction((ActionEvent event) -> {
                                  Object data = getTableView().getItems().get(getIndex());
                                  System.out.println("selectedData: " + data);
                                  TOSpecificAsset selectedAsset = (TOSpecificAsset) data;
                                  int selectedAssetNumber = selectedAsset.getAssetNumber();

                                  String assetNumber = Integer.toString(selectedAssetNumber);
                                  popupController.promptUpdateAssetPopUp(addButton, assetNumber); 
                                });
                                
                                break;
                              case "AssetTypes":
                                UpdateAssetTypePopUpController updateAssetController = new UpdateAssetTypePopUpController(topController);
                                btn.setOnAction((ActionEvent event) -> {
                                  //send the data to the contoller method!!
                                  Object data = getTableView().getItems().get(getIndex());
                                  System.out.println("selectedData: " + data);
                                  TOAssetType selectedAssetType = (TOAssetType) data;
                                  String selectedAssetName = selectedAssetType.getName();
                                  Boolean isValidAssetTypeAndName = (selectedAssetType != null && selectedAssetName != null);


                                  if (isValidAssetTypeAndName) {
                                    updateAssetController.promptUpdateAssetTypePopUp(selectedAssetName);
                                  } else {
                                    ViewUtils.showError("Invalid asset type data.");
                                  }
                                });
                                                                
                                break;

                              case "Users":
                                UpdateUserPopUpController popUpController = new UpdateUserPopUpController(topController);
                                btn.setOnAction((ActionEvent event) -> {
                                  Object data = getTableView().getItems().get(getIndex());
                                  System.out.println("selectedData: " + data);
                                  TOUser selectedUser = (TOUser) data;
                                  String selectedUserEmail = selectedUser.getEmail();

                                  popUpController.promptUpdatePopUp(selectedUserEmail);
                                });
  
                                break;
                              
                              case "Tickets":
                                UpdateTicketPopUpController popUpUpdateTicketController = new UpdateTicketPopUpController(topController);
                                btn.setOnAction((ActionEvent event) -> {
                                  Object data = getTableView().getItems().get(getIndex());
                                  System.out.println("selectedData: " + data);
                                  //send the data to the contoller method!!
                                  TOMaintenanceTicket selectedTicket = (TOMaintenanceTicket) data;
                                  int selectedTicketID = selectedTicket.getId();
                                  Boolean isValidTicketID = (selectedTicket != null);
                                  // Boolean isValidTicketID = (selectedTicket != null && selectedTicketID != 0);


                            
                                  // Check if the data is a TOMaintenanceTicket instance and if TOMaintenanceTicket has an id
                                  // If so, pass it to the updateTicketPopUpController
                                  if (isValidTicketID) {
                                    popUpUpdateTicketController.promptUpdateTicketPopUp(selectedTicketID);
                                  } else {
                                    // Handle the case where data is not a TOUser or doesn't have an email
                                    ViewUtils.showError("Invalid user data.");
                                  }
                                });

                                break;
                            }
                          break;
                        
                        case "Assign":
                          AssignTicketPopUpController popUpAssignTicketController = new AssignTicketPopUpController(topController);
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                                //send the data to the contoller method!!
                                TOMaintenanceTicket selectedTicket = (TOMaintenanceTicket) data;
                                int selectedTicketID = selectedTicket.getId();
                                Boolean isValidTicketID = (selectedTicket != null);
                                // Boolean isValidTicketID = (selectedTicket != null && selectedTicketID != 0);


                                
                          
                                // Check if the data is a TOMaintenanceTicket instance and if TOMaintenanceTicket has an id
                                // If so, pass it to the AssignTicketPopUpController
                                if (isValidTicketID) {
                                  popUpAssignTicketController.promptAssignTicketPopUp(addButton, selectedTicketID);
                                } else {
                                  // Handle the case where data is not a TOUser or doesn't have an email
                                  ViewUtils.showError("Invalid user data.");
                                }
                                
                              
                              ActionEvent refresh = new ActionEvent();
                              statusButtonClicked(refresh);
                          
                          });
                          break;

                        case "Start":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        

                              String error = AssetPlusFeatureSet4Controller.StartWorkOnMaintenanceTicket(Integer.toString(((TOMaintenanceTicket) data).getId()));
                                      
                              if (!error.equals("")) {
                                ViewUtils.showError(error);
                              }
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
                              TOMaintenanceTicket selectedTicket = (TOMaintenanceTicket) data;
                              int selectedTicketID = selectedTicket.getId();

                              DisapproveTicketPopUpController controller = new DisapproveTicketPopUpController(topController);
                              controller.promptDisapproveTicketPopUp(selectedTicketID);

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
                              
                              ImagePageController popUpController = new ImagePageController((TOMaintenanceTicket) data, topController);
                              popUpController.promptImagePopUp(btn);
                              
                              ActionEvent refresh = new ActionEvent();
                              viewTicketsButtonClicked(refresh);
                          
                          });
                          break;

                        case "Notes":
                          btn.setOnAction((ActionEvent event) -> {
                              Object data = getTableView().getItems().get(getIndex());
                              System.out.println("selectedData: " + data);        
                              
                              //send the data to the contoller method!!

                              NewNotePageController popUpController = new NewNotePageController((TOMaintenanceTicket) data, topController);
                              popUpController.promptNotePopUp(btn);   
                              
                              //immediately occurs after call ASYNC
                              
                              // ActionEvent refresh = new ActionEvent();
                              // viewTicketsButtonClicked(refresh);
                          
                          });
                          break;
                      }
                        btn.setStyle("-fx-background-color: #212121; ");
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
    emailTextField.setVisible(false);
    filterButton.setVisible(false);

    currentPage = "Assets";

    //Clear 
    dataTable.getColumns().clear();
    dataTable.getItems().clear();

    AddAssetPopupController popUpController = new AddAssetPopupController(this);
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

    addButtonToTable("Edit", currentPage, this);
    addButtonToTable("Delete", currentPage, this);
  }

  @FXML
  public void assetTypeButtonClicked(ActionEvent event){
    pageTitle.setText("Asset Types");
    addButton.setText("Add Type");
    addButton.setVisible(true);
    emailTextField.setVisible(false);
    filterButton.setVisible(false);
    currentPage = "AssetTypes";

    AddAssetTypePopupController popUpController = new AddAssetTypePopupController(this);
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

    addButtonToTable("Edit", currentPage, this);
    addButtonToTable("Delete", currentPage, this);
  }

  @FXML 
  public void usersButtonClicked(ActionEvent event) {
    pageTitle.setText("Users");
    addButton.setText("Add User");
    addButton.setVisible(true);
    emailTextField.setVisible(false);
    filterButton.setVisible(false);
    currentPage = "Users";


    AddUserPopUpController popUpController = new AddUserPopUpController(this);
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

    addButtonToTable("Edit", currentPage, this);
    addButtonToTable("Delete", currentPage, this);
  }
  
  @FXML
  public void viewTicketsButtonClicked(ActionEvent event) { 
    AddTicketPopUpController ticketPopUpController = new AddTicketPopUpController(this);
    addButton.setOnAction(e -> ticketPopUpController.promptAddTicketPopUp());
    
    pageTitle.setText("Tickets");
    addButton.setText("Add Ticket");
    addButton.setVisible(true);
    emailTextField.setVisible(true);
    filterButton.setVisible(true);
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
    addButtonToTable("Notes", currentPage, this);
    addButtonToTable("Images", currentPage, this);
    addButtonToTable("Edit", currentPage, this);
    addButtonToTable("Delete", currentPage, this);
  }

  @FXML
  public void filterButtonClicked(ActionEvent event) { 
    AddTicketPopUpController ticketPopUpController = new AddTicketPopUpController(this);
    addButton.setOnAction(e -> ticketPopUpController.promptAddTicketPopUp());
    
    pageTitle.setText("Tickets");
    addButton.setText("Add Ticket");
    addButton.setVisible(true);
    currentPage = "Tickets";
    String emailFilter = emailTextField.getText();

    if (emailFilter.strip().isEmpty()){
      viewTicketsButtonClicked(new ActionEvent());
    }

    dataTable.getColumns().clear();
    dataTable.getItems().clear();

    List<TOMaintenanceTicket> tickets= AssetPlusFeatureSet6Controller.getTickets();
    List<TOMaintenanceTicket> filteredTickets = new ArrayList<TOMaintenanceTicket>();

    for (int i = 0; i<tickets.size(); i++){
      if ((emailFilter.equals(tickets.get(i).getRaisedByEmail())) || (emailFilter.equals(tickets.get(i).getRaisedOnDate().toString()))){
        filteredTickets.add(tickets.get(i));}
    }

    dataTable.getItems().addAll(filteredTickets);

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
    addButtonToTable("Notes", currentPage, this);
    addButtonToTable("Images", currentPage, this);
    addButtonToTable("Edit", currentPage, this);
    addButtonToTable("Delete", currentPage, this);
  }

  @FXML
  public void statusButtonClicked(ActionEvent event) { 
    pageTitle.setText("Status");
    addButton.setText("Add Ticket");
    addButton.setVisible(false);
    emailTextField.setVisible(false);
    filterButton.setVisible(false);
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

    addButtonToTable("Assign", currentPage, this);
    addButtonToTable("Start", currentPage, this);
    addButtonToTable("Complete", currentPage, this);
    addButtonToTable("Approve", currentPage, this);
    addButtonToTable("Disapprove", currentPage, this);

  }

  //TODO: To be redirected later
  @FXML
  public void settingsButtonClicked(ActionEvent event) { 
    UpdateManagerPasswordController managerPopUpController = new UpdateManagerPasswordController();

    managerPopUpController.promptUpdateManagerPasswordPopUp();
  }
}
