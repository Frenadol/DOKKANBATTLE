package com.github.Frenadol.view;

import com.github.Frenadol.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainMenuController {

    @FXML
    private MediaView mediaView;

    @FXML
    private void switchToCharacterList() throws Exception {
        App.setRoot("CharacterList");
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
}