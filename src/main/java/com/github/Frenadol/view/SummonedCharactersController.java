package com.github.Frenadol.view;

import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.ErrorLog;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SummonedCharactersController implements Initializable {

    @FXML
    private TableView<Characters> summonedCharactersTableView;
    @FXML
    private TableColumn<Characters, String> nameColumn;
    @FXML
    private TableColumn<Characters, ImageView> visualColumn;

    private ObservableList<Characters> observableList;
    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * It initializes the observable list, sets it as the items of the TableView, and sets the cell value factories for the name and visual columns.
     * The visual column's cell value factory creates an ImageView for the character's visual data and sets it to open in a new Stage when clicked.
     * If the visual data is null, it displays an error alert.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            observableList = FXCollections.observableArrayList();
            summonedCharactersTableView.setItems(observableList);

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

            visualColumn.setCellValueFactory(characters -> {
                byte[] visualData = characters.getValue().getVisual();
                if (visualData != null) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(visualData);
                        Image image = new Image(bis);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(200);
                        imageView.setFitHeight(200);
                        imageView.setOnMouseClicked(event -> {
                            Stage stage = new Stage();
                            ImageView imageViewFull = new ImageView(image);
                            StackPane layout = new StackPane(imageViewFull);
                            Scene scene = new Scene(layout);
                            stage.setScene(scene);
                            stage.show();
                        });
                        return new SimpleObjectProperty<>(imageView);
                    } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No se pudo cargar la imagen del personaje.");
                    alert.show();
                        return null;
                    }
                });
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * This method is used to set the summoned characters to be displayed in the TableView.
     * It adds all the provided characters to the observable list.
     * If an error occurs during this process, it logs the error.
     */
    public void setSummonedCharacters(List<Characters> summonedCharacters) {
        try {
            observableList.addAll(summonedCharacters);
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * This method is called when the view is closed.
     * Currently, it does not perform any actions.
     */
    @FXML private void onClose(){
    }
}