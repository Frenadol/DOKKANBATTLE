package com.github.Frenadol.view;

import com.github.Frenadol.model.dao.UsersDAO;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ImageInput;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private MediaView mediaView;
    @FXML
    private TableView<Users> tableView;
    @FXML
    private TableColumn<Users, Integer> dragonStonesColumn;
    @FXML
    private ObservableList<Users> observableList;


    @FXML
    private void playVideo() {
        URL videoUrl = getClass().getResource("/MediaContent/BlackRoseVideo.mp4");
        Media media = new Media(videoUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaView.setMediaPlayer(null);
        });

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Users user = Session.getInstance().getUserLogged();
        if (user != null) {
            int dragonStones = user.getDragon_stones();
            this.observableList = FXCollections.observableArrayList(user);
            tableView.setItems(observableList);
            dragonStonesColumn.setCellValueFactory(data -> new SimpleIntegerProperty(dragonStones).asObject());
        } else {
            showAlert("Error", "No se ha iniciado sesión.");
        }
    }
    @FXML
    private void onClose() {
        Users user = Session.getInstance().getUserLogged();

        UsersDAO usersDAO = new UsersDAO();
        usersDAO.updateUser(user);
        Stage stage = (Stage) mediaView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void updateUsername() {
        try {
            Users user = Session.getInstance().getUserLogged();
            if (user != null) {
                String currentUsername = user.getName_user();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación de cambio de nombre");
                alert.setHeaderText("Tu nombre actual es " + currentUsername);
                alert.setContentText("¿Estás seguro de que quieres cambiar tu nombre de usuario?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    String newUsername = showUsernameInputDialog();
                    if (newUsername == null || newUsername.isEmpty()) {
                        showAlert("Error", "El campo de nombre de usuario está vacío.");
                        return;
                    }
                    UsersDAO usersDAO = new UsersDAO();
                    Users existingUser = usersDAO.findByName(newUsername);
                    if (existingUser != null && !existingUser.equals(user)) {
                        showAlert("Error", "El nombre de usuario ya existe. Por favor, elige un nombre de usuario diferente.");
                        return;
                    }
                    if (newUsername.equals(currentUsername)) {
                        showAlert("Error", "El nuevo nombre de usuario es igual al actual. Por favor, elige un nombre de usuario diferente.");
                        return;
                    }
                    user.setName_user(newUsername);
                    usersDAO.updateUser(user);
                    showAlert("Éxito", "Nombre de usuario actualizado con éxito.");
                    tableView.refresh(); // Actualiza la TableView para mostrar el nuevo nombre de usuario
                }
            } else {
                showAlert("Error", "No se ha iniciado sesión.");
            }
        } catch (Exception e) {
            showAlert("Error", "Ha ocurrido un error al actualizar el nombre de usuario: " + e.getMessage());
        }
    }
    private String showUsernameInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambio de nombre de usuario");
        dialog.setHeaderText("Por favor, introduce tu nuevo nombre de usuario:");
        dialog.setContentText("Nombre de usuario:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }
    @FXML
    private void switchToCharacterList() throws Exception {
        App.setRoot("CharacterList");
    }

    @FXML
    public void goToSummonMenu() throws IOException {
        App.setRoot("summonsMenu");
    }

    @FXML
    private void goToAnimationsGallery() throws IOException {
        App.setRoot("animationsGallery");
    }
}


