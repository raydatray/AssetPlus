package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.checkerframework.checker.units.qual.m;
import com.google.common.collect.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableRowSkin;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.controller.TOUser;

//Get a TOMaintenanceTicket over 

public class NewNotePageController {
  @FXML private TableView noteTable;

  @FXML private Button addNote;

  @FXML private Button closeNotePopUpButton;

  private TOMaintenanceTicket mTicket;

  public NewNotePageController(TOMaintenanceTicket maintenanceTicket){
    this.mTicket = maintenanceTicket;
  }

  public class IndexedNote{
    private int index;
    private Date date;
    private String description;
    private String noteTakerEmail;

    public IndexedNote(int index, TOMaintenanceNote note){
      this.index = index;
      this.date = note.getDate();
      this.description = note.getDescription();
      this.noteTakerEmail = note.getNoteTakerEmail();
    }

    public int getIndex(){
      return index;
    }

    public Date getDate(){
      return date;
    }

    public String getDescription(){
      return description;
    }

    public String getNoteTakerEmail(){
      return noteTakerEmail;
    }
}

  public void promptNotePopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/NotesPopup.fxml"));
      loader.setControllerFactory(param -> new NewNotePageController(mTicket));
      Parent root = loader.load();

      // Populate the choice box
      NewNotePageController controller = loader.getController();
      controller.loadTable();

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Note");
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

  public void refreshTable(){
    loadTable();
  }

  private void addButtonToTable(String buttonType){
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

                            AssetPlusFeatureSet7Controller.deleteMaintenanceNote(mTicket.getId(), ((IndexedNote) data).getIndex());
                            //DOES NOT RELOAD PROPERLY 
                            loadTable();
                          });
                          break;

                        case "Edit":
                          btn.setOnAction((ActionEvent event) -> {
                            Object data = getTableView().getItems().get(getIndex());

                            System.out.println("selectedData: " + data);

                            loadTable();
                          });    
                          break;
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

        noteTable.getColumns().add(colBtn);
  }

  @FXML
  public void loadTable(){
    //clear the table
    noteTable.getColumns().clear();
    noteTable.getItems().clear();


    AddNotePopupController popUpController = new AddNotePopupController();
    addNote.setOnAction(e -> popUpController.promptAddNotePopUp(addNote, mTicket.getId()));
    // AddNotePopUpController popupController = new AddNotePopupController(this);
    // addButton.setOnAction(e -> popupController.promptAddNotePopUp(addButton));


    ArrayList<IndexedNote> notes = new ArrayList<IndexedNote>();
    List<TOMaintenanceNote> oldNotes = mTicket.getNotes();

    for(int i = 0; i < mTicket.getNotes().size(); i++){
      notes.add(new IndexedNote(i, oldNotes.get(i)));
    }


    List<IndexedNote> obsNotes = notes;

    noteTable.getItems().addAll(obsNotes);
  
    TableColumn<IndexedNote, Integer> indexColumn = new TableColumn<>("Note Index");
    indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));;
    noteTable.getColumns().add(indexColumn);

    TableColumn<IndexedNote, String> dateColumn = new TableColumn<>("Date Added");
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));;
    noteTable.getColumns().add(dateColumn);

    TableColumn<IndexedNote, String> descriptionColumn = new TableColumn<>("Description");
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));;
    noteTable.getColumns().add(descriptionColumn);

    TableColumn<IndexedNote, String> noteTakerColumn = new TableColumn<>("Note Taker");
    noteTakerColumn.setCellValueFactory(new PropertyValueFactory<>("noteTakerEmail"));;
    noteTable.getColumns().add(noteTakerColumn);

    addButtonToTable("Edit");
    addButtonToTable("Delete");

  }




  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(addNote);
  }

  
}
