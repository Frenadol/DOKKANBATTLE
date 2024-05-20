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
    /**
     * This method is called when the view is opened.
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
     * This method is called to initialize the controller after its root element has been completely processed.
     * It populates the TableView with characters from the database.
     */
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
    /**
     * This method is used to play a video.
     * It stops any currently playing video, creates a new MediaPlayer for the specified video, and starts playing it.
     */
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
    /**
     * This method is used to stop the currently playing video.
     */
    @FXML
    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaView.setMediaPlayer(null);
        }
    }
    /**
     * This method is used to pause the currently playing video.
     */
    @FXML
    private void pauseVideo() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }
    /**
     * This method is used to navigate to the main menu.
     * It stops any currently playing video and sets the root of the application to the "mainMenu" screen.
     */
    @FXML
    public void goToMainMenu() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        App.setRoot("mainMenu");
    }
    /**
     * These methods are used to play specific animations.
     * Each method calls the playVideo method with the path to the corresponding animation video.
     */
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
