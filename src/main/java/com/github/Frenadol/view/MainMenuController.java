package com.github.Frenadol.view;
import javafx.fxml.Initializable;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.ImageInput;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private void switchToCharacterList() throws Exception {
        App.setRoot("CharacterList");
    }
    @FXML
    public void goToSummonMenu() throws IOException {
        App.setRoot("summonsMenu");
    }

    @FXML
    private void playVideo() {
        // Obtener la ruta del video
        URL videoUrl = getClass().getResource("/MediaContent/BlackRoseVideo.mp4");
        Media media = new Media(videoUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        // Reproducir el video
        mediaPlayer.setAutoPlay(true);

        // Cerrar la ventana cuando el video termina
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
}