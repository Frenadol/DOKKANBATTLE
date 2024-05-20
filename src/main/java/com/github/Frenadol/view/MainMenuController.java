package com.github.Frenadol.view;

import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.utils.ErrorLog;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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

    /**
     * This method is used to play a video.
     * It loads the video from the specified URL, creates a MediaPlayer and a MediaView for it,
     * and displays it in a new Stage when the MediaPlayer is ready.
     */
    @FXML
    private void playVideo() {
        URL videoUrl = getClass().getResource("/MediaContent/BlackRoseVideo.mp4");
        Media media = new Media(videoUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            double width = mediaPlayer.getMedia().getWidth();
            double height = mediaPlayer.getMedia().getHeight();

            MediaView fullScreenMediaView = new MediaView(mediaPlayer);

            Stage fullScreenStage = new Stage();

            Scene scene = new Scene(new Group(fullScreenMediaView), width, height);

            fullScreenStage.setScene(scene);

            fullScreenStage.show();

            mediaPlayer.setAutoPlay(true);

            mediaPlayer.setOnEndOfMedia(() -> {
                fullScreenStage.close();
            });
        });
    }
    /**
     * This method is used to display an alert dialog with a specified title, content, and alert type.
     * It creates a new Alert object, sets the provided title, content, and alert type, and then displays the alert.
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * It gets the logged in user, and if the user is not null, it displays the user's dragon stones in the TableView.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Users user = Session.getInstance().getUserLogged();
        if (user != null) {
            int dragonStones = user.getDragon_stones();
            this.observableList = FXCollections.observableArrayList(user);
            tableView.setItems(observableList);
            dragonStonesColumn.setCellValueFactory(data -> new SimpleIntegerProperty(dragonStones).asObject());
        } else {
            showAlert("Error", "Ha ocurrido un error al obtener el nombre de usuario: " ,Alert.AlertType.ERROR);
        }
    }
    /**
     * This method is called when the view is closed.
     * It updates the logged in user in the database and closes the window.
     */
    @FXML
    private void onClose() {
        Users user = Session.getInstance().getUserLogged();
        UsersDAO usersDAO = new UsersDAO();
        usersDAO.updateUser(user);
        Stage stage = (Stage) mediaView.getScene().getWindow();
        stage.close();
    }
    /**
     * This method is used to update the username of the logged in user.
     * It displays a confirmation dialog, and if the user confirms, it displays an input dialog for the new username,
     * checks if the new username is valid and does not already exist, and if it is, it updates the username in the database and refreshes the TableView.
     */
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
                        showAlert("Error", "El campo de nombre de usuario está vacío.", Alert.AlertType.ERROR);
                        return;
                    }
                    UsersDAO usersDAO = new UsersDAO();
                    Users existingUser = usersDAO.findByName(newUsername);
                    if (existingUser != null && !existingUser.equals(user)) {
                        showAlert("Error", "El nombre de usuario ya existe. Por favor, elige un nombre de usuario diferente.", Alert.AlertType.ERROR);
                        return;
                    }
                    if (newUsername.equals(currentUsername)) {
                        showAlert("Error", "El nuevo nombre de usuario es igual al actual. Por favor, elige un nombre de usuario diferente.", Alert.AlertType.ERROR);
                        return;
                    }
                    user.setName_user(newUsername);
                    usersDAO.updateUser(user);
                    showAlert("Éxito", "Nombre de usuario actualizado con éxito.", Alert.AlertType.INFORMATION);
                    tableView.refresh();
                }
            } else {
                showAlert("Error", "No se ha iniciado sesión.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            ErrorLog.fileRead(e);
            showAlert("Error", "Ha ocurrido un error al actualizar el nombre de usuario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /**
     * This method is used to display an input dialog for the new username.
     * It creates a new TextInputDialog object, sets the title, header text, and content text, and then displays the dialog.
     * If the user enters a username, it returns the username, otherwise it throws an exception.
     */
    private String showUsernameInputDialog() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Cambio de nombre de usuario");
            dialog.setHeaderText("Por favor, introduce tu nuevo nombre de usuario:");
            dialog.setContentText("Nombre de usuario:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                return result.get();
            } else {
                throw new Exception("No se ha introducido ningún nombre de usuario.");
            }
        } catch (Exception e) {
            ErrorLog.fileRead(e);
            showAlert("Error", "Ha ocurrido un error al obtener el nombre de usuario: ",Alert.AlertType.ERROR);
            return null;
        }
    }
    /**
     * This method is used to switch the view to the Character List screen.
     * It tries to set the root of the application to the "CharacterList" screen.
     * If an error occurs during this process, it throws the exception.
     */
    @FXML
    private void switchToCharacterList() throws Exception {
        App.setRoot("CharacterList");
    }
    /**
     * This method is used to navigate to the Summon Menu screen.
     * It tries to set the root of the application to the "summonsMenu" screen.
     * If an error occurs during this process, it throws the exception.
     */
    @FXML
    private void goToSummonMenu() throws IOException {
        App.setRoot("summonsMenu");
    }
    /**
     * This method is used to navigate to the Animations Gallery screen.
     * It tries to set the root of the application to the "animationsGallery" screen.
     * If an error occurs during this process, it throws the exception.
     */
    @FXML
    private void goToAnimationsGallery() throws IOException {
        App.setRoot("animationsGallery");
    }
}


