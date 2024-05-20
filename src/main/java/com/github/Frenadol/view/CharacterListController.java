package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.utils.ErrorLog;
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
    /**
     * This method is called when the view is opened.
     * Currently, it does not perform any actions.
     */
    @Override
    public void onOpen(Object input) {

    }
    /**
     * This method is called when the view is closed.
     * Currently, it does not perform any actions.
     */
    @Override
    public void onClose(Object output) {

    }
    /**
     * This method is used to sell a character.
     * It gets the character selected in the TableView, displays a confirmation dialog, and if the user confirms,
     * it removes the character from the user's list, deletes it from the database, and removes it from the TableView.
     */
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
                    usersDAO.save(user);
                } catch (SQLException e) {
                    ErrorLog.fileRead(e);
                    showAlert("Error al vender el personaje. Inténtelo de nuevo.");
                }
                observableList.remove(selectedCharacter);
            }
        } else {
            showAlert("Por favor, selecciona un personaje para vender.");
        }
    }
    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * It populates the TableView with the characters from the logged in user's list.
     */
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
    /**
     * This method is used to navigate to the main menu.
     * It tries to set the root of the application to the "mainMenu" screen.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    public void goToMainMenu() {
        try {
            App.setRoot("mainMenu");
        } catch (IOException e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * This method is used to display an alert dialog with a specified message.
     * It creates a new Alert object, sets the provided message as its content, and then displays the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}