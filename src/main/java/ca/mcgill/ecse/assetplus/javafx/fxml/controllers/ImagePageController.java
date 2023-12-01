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
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.controller.TOTicketImage;
import ca.mcgill.ecse.assetplus.controller.TOUser;

//Get a TOMaintenanceTicket over 

public class ImagePageController {
  @FXML private TableView imageTable;
  @FXML private Button addImage;


  @FXML
  private Button closeImagePopUpButton;


  @FXML
  private Label pageTitle;


  private TOMaintenanceTicket mTicket;

  public ImagePageController(TOMaintenanceTicket maintenanceTicket){
    this.mTicket = maintenanceTicket;
  }


  public void promptImagePopUp(Button addButton) {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/javafx/fxml/pop-ups/ImagesPopup.fxml"));
      loader.setControllerFactory(param -> new ImagePageController(mTicket));
      Parent root = loader.load();

      // Populate the choice box
      ImagePageController controller = loader.getController();
      controller.loadTable();

      // Create a new stage for the pop-up
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.initStyle(StageStyle.TRANSPARENT);
      popupStage.setTitle("Add Asset");
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

  private void addDeleteButtonToTable(){
    TableColumn<Object, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<TableColumn<Object, Void>, TableCell<Object, Void>>() {
            
          @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                final TableCell<Object, Void> cell = new TableCell<Object, Void>() {

                    private Button btn = new Button("Delete");

                    { 
                          btn.setOnAction((ActionEvent event) -> {
                            Object data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);

                            AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(((TOTicketImage) data).getImageURL(), mTicket.getId());
                            //DOES NOT RELOAD PROPERLY 
                            loadTable();
                          });

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

        imageTable.getColumns().add(colBtn);
  }

  @FXML
  public void loadTable(){
    //clear the table
    imageTable.getColumns().clear();
    imageTable.getItems().clear();

    AddImageToTicketPopUpController popUpController = new AddImageToTicketPopUpController();
    addImage.setOnAction(e -> popUpController.promptAddImagePopUp(addImage, mTicket.getId()));

    ArrayList<TOTicketImage> images = new ArrayList<TOTicketImage>();
    List<String> oldImages = mTicket.getImageURLs();

    for (int i = 0; i < mTicket.getImageURLs().size(); i++){
      images.add(new TOTicketImage(oldImages.get(i)));
    }

    imageTable.getItems().addAll(images);
  
    TableColumn<TOTicketImage, String> urlColumn = new TableColumn<>("Image URL");
    urlColumn.setCellValueFactory(new PropertyValueFactory<>("imageURL"));;
    imageTable.getColumns().add(urlColumn);

    addDeleteButtonToTable();

  }



  public void handleCloseButtonClick() {
    ViewUtils.closeWindow(addImage);
  }

}
