package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
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

    private List<String> videoPaths = Arrays.asList(
            "/MediaContent/BlackRift.mp4",
            "/MediaContent/GodSummonsAnimations.mp4",
            "/MediaContent/GohanSummonsAnimations.mp4"
    );

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        List<Character_portal> charactersPortals = Character_portalDAO.build().findAll();
        this.observableList = FXCollections.observableArrayList(charactersPortals);
        character_PortalTableView.setItems(observableList);
        nameColumn.setCellValueFactory(Character_portal -> new SimpleStringProperty(Character_portal.getValue().getName_portal()));

    }


    private void summonButtonClicked() {
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
    }


    private void summonCharacters(Users user, UsersDAO usersDAO) {
        List<Characters> summonedCharacters = new ArrayList<>();
        int randomIndex = generateRandomIndex();
        Characters character = build().findAllLocated(randomIndex);
        if (character != null) {
            summonedCharacters.add(character);
            user.getCharacters_list().add(character);
            List<Characters> obtainedCharacters = new ArrayList<>();
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
                user.setDragon_stones(user.getDragon_stones() + 25); // Devuelve 25 piedras al usuario
            }
        }

        if (!summonedCharacters.isEmpty()) {
            usersDAO.updateUser(user);
            showSummonedCharactersDialog(summonedCharacters);
        }
    }

    private void showStonesCompensationAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Compensación por personaje repetido");
        alert.setHeaderText(null);
        alert.setContentText("Se han añadido 25 piedras a tu cuenta como compensación por personaje repetido.");

        alert.showAndWait();
    }

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
            e.printStackTrace();
        }
    }


    @FXML
    public void goToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }


    private Random random = new Random();

    private int generateRandomIndex() {
        int characters = new CharactersDAO().countCharacters();
        return random.nextInt(characters) + 1;
    }

    @FXML
    private void onClose() {
    }

    @FXML
    private void playRandomVideo() {
        int randomIndex = random.nextInt(videoPaths.size());
        String videoPath = videoPaths.get(randomIndex);

        URL resource = getClass().getResource(videoPath);
        if (resource != null) {
            Media media = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            // Crea una nueva ventana para mostrar el video
            Stage videoStage = new Stage();
            videoStage.setTitle("Video");

            // Utiliza un StackPane para centrar el MediaView
            StackPane root = new StackPane(mediaView);
            Scene scene = new Scene(root);

            videoStage.setScene(scene);
            videoStage.show();

            // Asegura que la ventana del video esté al frente
            videoStage.toFront();

            // Ajusta el tamaño de la ventana al tamaño del video
            videoStage.setWidth(media.getWidth());
            videoStage.setHeight(media.getHeight());

            // Ajusta el tamaño del video al tamaño de la ventana
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());
            mediaView.setPreserveRatio(true);

            // Establece el MediaPlayer para el MediaView y comienza a reproducir el video
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);

            // Establece un Runnable para ejecutarse cuando el video haya terminado
            mediaPlayer.setOnEndOfMedia(() -> {
                videoStage.close(); // Cierra la ventana del video
                summonButtonClicked(); // Llama a la secuencia de conseguir el personaje o las piedras
            });
        } else {
            System.out.println("No se pudo encontrar el recurso: " + videoPath);
        }
    }
}