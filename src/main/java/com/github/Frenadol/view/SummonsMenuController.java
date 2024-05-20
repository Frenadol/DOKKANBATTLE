package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.ErrorLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static com.github.Frenadol.model.dao.Character_portalDAO.build;

public class SummonsMenuController implements Initializable {
    @FXML
    private TableView<Character_portal> character_PortalTableView;

    @FXML
    private TableColumn<Character_portal, String> nameColumn;
    private ObservableList<Character_portal> observableList;
    @FXML
    private MediaView mediaView;
    private boolean isVideoPlaying = false;
    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * It populates the TableView with character portals from the database.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Character_portal> charactersPortals = Character_portalDAO.build().findAll();
            this.observableList = FXCollections.observableArrayList(charactersPortals);
            character_PortalTableView.setItems(observableList);
            nameColumn.setCellValueFactory(Character_portal -> new SimpleStringProperty(Character_portal.getValue().getName_portal()));
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }

    /**
     * This method is called when the summon button is clicked.
     * It gets the logged in user, checks if the user has enough dragon stones for a summon,
     * and if the user does, it reduces the user's dragon stones by the summon cost, gets a random character from a portal,
     * and if the character is not already obtained by the user, it adds the character to the user's characters list and updates the user in the database.
     * If the character is already obtained by the user, it compensates the user with dragon stones.
     */
    private void summonButtonClicked() {
        try {
            Users user = Session.getInstance().getUserLogged();
            UsersDAO usersDAO = new UsersDAO();
            final int SUMMON_COST = 50;
            int stonesRemaining = user.getDragon_stones() - SUMMON_COST;
            if (stonesRemaining < 0) {
                showStonesCompensationAlert();
                return;
            }
            user.setDragon_stones(stonesRemaining);
            Character_portal characterPortal = new Character_portal();
            characterPortal.setId_portal(2);
            Character_portal portal = build().findById(characterPortal);

            if (portal != null) {
                int idUser = user.getId_user();
                int idCharacter = generateRandomIndex();
                if (Character_portalDAO.build().findCharacter(idUser, idCharacter) != 0) {
                    return;
                }
            } else {
                showStonesCompensationAlert();
            }

            summonCharacters(user, usersDAO);
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }

    /**
     * This method is used to summon characters.
     * It generates a random index, gets the character at that index, and if the character is not null, it adds the character to the summoned characters list and the user's characters list.
     * If the character is not already obtained by the user, it inserts the character into the obtained characters in the database.
     * If the character is already obtained by the user, it compensates the user with dragon stones.
     * If there are any summoned characters, it updates the user in the database and displays the summoned characters dialog.
     */
    private void summonCharacters(Users user, UsersDAO usersDAO) {
        try {
            List<Characters> summonedCharacters = new ArrayList<>();
            int randomIndex = generateRandomIndex();
            Characters character = build().findAllLocated(randomIndex);
            if (character != null) {
                summonedCharacters.add(character);
                user.getCharacters_list().add(character);
                List<Characters> obtainedCharacters;
                try {
                    obtainedCharacters = usersDAO.findAllCharacterFromObtained(user);
                } catch (SQLException e) {
                    showStonesCompensationAlert();
                    return;
                }
                boolean isCharacterObtained = false;
                for (Characters obtainedCharacter : obtainedCharacters) {
                    if (obtainedCharacter.getId_character() == character.getId_character()) {
                        isCharacterObtained = true;
                        break;
                    }
                }
                if (!isCharacterObtained) {
                    usersDAO.insertObtainedCharacters(user, character.getId_character());
                } else {
                    showStonesCompensationAlert();
                    user.setDragon_stones(user.getDragon_stones() + 25);
                }
            }

            if (!summonedCharacters.isEmpty()) {
                usersDAO.updateUser(user);
                showSummonedCharactersDialog(summonedCharacters);
            }
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * This method is used to display a compensation alert when a character is already obtained by the user.
     * It creates a new Alert object, sets the title, header text, and content text, and then displays the alert.
     */
    private void showStonesCompensationAlert() {
        try {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Compensación por personaje repetido");
            alert.setHeaderText("Personaje repetido");
            alert.setContentText("Se han añadido 25 piedras a tus piedras por invocar a unpersonaje repetido.");

            alert.showAndWait();
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * This method is used to display a dialog with the summoned characters.
     * It loads the SummonedCharacters view, gets its controller, sets the summoned characters in the controller, and then displays the view in a new Stage.
     */
    private void showSummonedCharactersDialog(List<Characters> summonedCharacters) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SummonedCharacters.fxml"));
            Parent root = loader.load();
            SummonedCharactersController controller = loader.getController();
            controller.setSummonedCharacters(summonedCharacters);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Personajes invocados");
            stage.showAndWait();
        } catch (IOException e) {
            ErrorLog.fileRead(e);
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


    private Random random = new Random();
    /**
     * This method is used to generate a random index.
     * It gets the number of characters in the database and returns a random integer between 1 and that number.
     * If an error occurs during this process, it logs the error and returns 0.
     */
    private int generateRandomIndex() {
        try {
            int characters = new CharactersDAO().countCharacters();
            return random.nextInt(characters) + 1;
        } catch (Exception e) {
            ErrorLog.fileRead(e);
            return 0;
        }
    }
    /**
     * This method is called when the view is closed.
     * Currently, it does not perform any actions.
     */
    @FXML
    private void onClose() {
    }
    /**
     * This method is used to play a random video.
     * It loads the video from the specified URL, creates a MediaPlayer for it, and when the MediaPlayer is ready, it creates a MediaView for it, displays it in a new Stage, and starts playing the video.
     * When the video ends, it closes the Stage and calls the summonButtonClicked method.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    private void playRandomVideo() {


        try {
            String videoPath = "/MediaContent/BlackRift.mp4";

            URL resource = getClass().getResource(videoPath);
            if (resource != null) {
                Media media = new Media(resource.toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setOnReady(() -> {
                    isVideoPlaying = true;
                    Stage videoStage = new Stage();
                    videoStage.setTitle("Animacion");
                    StackPane root = new StackPane(mediaView);
                    Scene scene = new Scene(root);
                    videoStage.setScene(scene);
                    videoStage.show();
                    videoStage.toFront();
                    videoStage.setFullScreen(true); // Set the stage to full screen
                    mediaView.fitWidthProperty().bind(scene.widthProperty());
                    mediaView.fitHeightProperty().bind(scene.heightProperty());
                    mediaView.setPreserveRatio(true);
                    mediaView.setMediaPlayer(mediaPlayer);
                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.setOnEndOfMedia(() -> {
                        videoStage.close();
                        isVideoPlaying = false;
                        summonButtonClicked();
                    });
                });

                mediaPlayer.setOnError(() -> {
                    isVideoPlaying = false;
                    ErrorLog.logMessage("Error al reproducir el video: " + videoPath);
                });
            } else {
                ErrorLog.logMessage("No se pudo encontrar el recurso: " + videoPath);
            }
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
}