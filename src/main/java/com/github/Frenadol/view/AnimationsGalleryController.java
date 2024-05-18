package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Characters;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AnimationsGalleryController extends Controller implements Initializable {
    @FXML
    private MediaView mediaView;
    @FXML
    private TableView<Characters> TableView;
    @FXML
    private TableColumn<Characters, Integer> id_CharacterColumn;
    @FXML
    private TableColumn<Characters, String> nameColumn;
    @FXML
    private TableColumn<Characters, ImageView> visualColumn;

    private ObservableList<Characters> observableList;
    private MediaPlayer mediaPlayer;

    @Override
    public void onOpen(Object input) {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TableView.getItems().isEmpty()) {
            List<Characters> charactersList = CharactersDAO.build().findAll();
            this.observableList = FXCollections.observableArrayList(charactersList);
            TableView.setItems(observableList);
            id_CharacterColumn.setCellValueFactory(characters -> new SimpleIntegerProperty(characters.getValue().getId_character()).asObject());
            nameColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getName()));
            visualColumn.setCellValueFactory(characters -> {
                byte[] visualData = characters.getValue().getVisual();
                if (visualData != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(visualData);
                    Image image = new Image(bis);

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(200);

                    return new SimpleObjectProperty<>(imageView);
                } else {
                    System.out.println("visualData es null");
                    return null;
                }
            });
        }
    }

    @FXML
    private void playVideo(String videoPath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        URL videoUrl = getClass().getResource(videoPath);
        Media media = new Media(videoUrl.toString());
        mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaView.setMediaPlayer(null);
        });

    }
    @FXML
    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaView.setMediaPlayer(null);
        }
    }

    @FXML
    private void pauseVideo() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    @FXML
    public void goToMainMenu() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        App.setRoot("mainMenu");
    }

    @FXML
    private void CoolerAnimations() {
        playVideo("/MediaContent/Cooler.mp4");
    }
    @FXML
    private void RoseAnimations() {
        playVideo("/MediaContent/RoseAnimations.mp4");
    }
    @FXML
    private void GohanAnimations() {
        playVideo("/MediaContent/GohanAnimations.mp4");
    }
    @FXML
    private void GokuAndVeguetaAnimations() {
        playVideo("/MediaContent/VeguitoBlue.mp4");
    }
    @FXML
    private void ZamasuInt() {
        playVideo("/MediaContent/ZamasuINT.mp4");
    }
    @FXML
    private void ZamasuTEQ() {
        playVideo("/MediaContent/ZamasuTEQLR.mp4");
    }
    @FXML
    private void TrunksLR() {
        playVideo("/MediaContent/TrunksFISLR.mp4");
    }
    @FXML
    private void VeguetaAndTrunksLR() {
        playVideo("/MediaContent/VeguetaAndTrunks.mp4");
    }

}
