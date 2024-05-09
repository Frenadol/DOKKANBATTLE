package com.github.Frenadol.view;

import com.github.Frenadol.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class MainMenuController {

    @FXML
    private AnchorPane anchorPane;

    private MediaPlayer mediaPlayer;

    @FXML
    private void initialize() {
        // Configurar la imagen inicial en el AnchorPane
        // Aquí puedes configurar la imagen inicial en el AnchorPane si es necesario
    }

    @FXML
    private void switchToCharacterList() throws Exception {
        // Detener la reproducción del video antes de cambiar a la lista de personajes
        stopVideo();
        App.setRoot("CharacterList");
    }

    @FXML
    private void playVideo() {
        try {

            File videoFile = new File("C:/Users/ferna/IdeaProjects/PROYECTODOKKANBATTLE/src/main/resources/MediaContent/Black Rose Video.mp4"); // Cambia esto por la ruta de tu video


            String videoUrl = videoFile.toURI().toString();


            Media media = new Media(videoUrl);


            mediaPlayer = new MediaPlayer(media);


            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(anchorPane.getWidth());
            mediaView.setFitHeight(anchorPane.getHeight());


            anchorPane.getChildren().add(mediaView);
            AnchorPane.setTopAnchor(mediaView, 0.0);
            AnchorPane.setBottomAnchor(mediaView, 0.0);
            AnchorPane.setLeftAnchor(mediaView, 0.0);
            AnchorPane.setRightAnchor(mediaView, 0.0);

            // Reproducir el video
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al reproducir el video.");
        }
    }

    private void stopVideo() {
        // Detener la reproducción del video si está en curso
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
