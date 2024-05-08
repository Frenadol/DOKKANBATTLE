package com.github.Frenadol.view.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Characters;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CharacterListController extends Controller implements Initializable {
    @FXML
    private TableView<Characters> TableView;
    @FXML
    private TableColumn<Characters, Integer> id_CharacterColumn;
    @FXML
    private TableColumn<Characters, String> typeColumn;
    @FXML
    private TableColumn<Characters, String> classColumn;
    @FXML
    private TableColumn<Characters, String> nameColumn;

    @FXML
    private TableColumn<Characters, String> categoriesColumn;
    @FXML
    private TableColumn<Characters, String> superAttackColumn;
    @FXML
    private TableColumn<Characters, String> ultraSuperAttackColumn;
    @FXML
    private TableColumn<Characters, String> raretyColumn;
    @FXML
    private TableColumn<Characters, String> passiveColumn;
    @FXML
    private TableColumn<Characters, ImageView> visualColumn;
    private ObservableList<Characters> observableList;

    @Override
    public void onOpen(Object input) {

    }

    @Override
    public void onClose(Object output) {

    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        List<Characters> characters2 = CharactersDAO.build().findAll();
        this.observableList = FXCollections.observableArrayList(characters2);
        TableView.setItems(observableList);

        id_CharacterColumn.setCellValueFactory(characters -> new SimpleIntegerProperty(characters.getValue().getId_character()).asObject());
        typeColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getType()));
        classColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getCharacter_class()));
        nameColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getName()));
        categoriesColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getCategories()));
        superAttackColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getSuperAttack()));
        ultraSuperAttackColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getUltraSuperAttack()));
        raretyColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getRarety()));
        passiveColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getPassive()));
        visualColumn.setCellValueFactory(characters -> {
            byte[] visualData = characters.getValue().getVisual();
            if (visualData != null) {
                // Convertir los bytes a una imagen de JavaFX
                ByteArrayInputStream bis = new ByteArrayInputStream(visualData);
                Image image = new Image(bis);

                // Crear un ImageView para mostrar la imagen
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);  // Ajustar el ancho
                imageView.setFitHeight(100); // Ajustar la altura

                // Agregar un evento de clic al ImageView
                imageView.setOnMouseClicked(event -> {
                    // Crear una nueva ventana para mostrar la imagen
                    Stage stage = new Stage();
                    ImageView imageViewFull = new ImageView(image);
                    StackPane layout = new StackPane(imageViewFull);
                    Scene scene = new Scene(layout);
                    stage.setScene(scene);
                    stage.show();
                });

                // Devolver el ImageView como el valor de la celda
                return new SimpleObjectProperty<>(imageView);
            } else {
                System.out.println("visualData es null");
                // Código para manejar cuando visualData es null...
                return null;
            }
        });
    }
}