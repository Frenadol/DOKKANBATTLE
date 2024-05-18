package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.model.entity.Characters;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private int soldCharacterId;

    private ObservableList<Characters> observableList;

    @Override
    public void onOpen(Object input) {

    }

    @Override
    public void onClose(Object output) {

    }
    @FXML
    public void sellCharacter() {
        Characters selectedCharacter = TableView.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de venta");
            alert.setHeaderText("Estás a punto de vender el personaje " + selectedCharacter.getName());
            alert.setContentText("¿Estás seguro de que quieres vender este personaje?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Users user = Session.getInstance().getUserLogged();
                user.getCharacters_list().remove(selectedCharacter);
                soldCharacterId = selectedCharacter.getId_character(); // Almacenar la ID del personaje vendido
                try {
                    UsersDAO usersDAO = UsersDAO.build();
                    usersDAO.deleteObtainedCharacters(Session.getInstance().getUserLogged(), selectedCharacter.getId_character());
                    usersDAO.save(user); // Guardar los cambios en el usuario
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                observableList.remove(selectedCharacter);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("No se seleccionó ningún personaje");
            alert.setContentText("Por favor, selecciona un personaje para vender.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TableView.getItems().isEmpty()) {
            Users user = Session.getInstance().getUserLogged();

            List<Characters> charactersList = UsersDAO.build().new UsersLazyAll(user.getId_user(), user.getName_user(), user.getPassword(), user.getDragon_stones(), user.getCharacters_list(), user.isAdmin()).UsersLazyAll();

            this.observableList = FXCollections.observableArrayList(charactersList);
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
                    System.out.println("visualData es null");
                    return null;
                }
            });
        }

    }
    @FXML
    public void goToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

}

