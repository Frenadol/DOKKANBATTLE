package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.utils.ErrorLog;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminMenuController {
    @FXML
    private TextField idField;

    @FXML
    private TextField typeField;
    @FXML
    private TextField characterClassField;
    @FXML
    private TextField nameField;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField categoriesField;
    @FXML
    private TextField superAttackField;
    @FXML
    private TextField ultraSuperAttackField;
    @FXML
    private TextField raretyField;
    @FXML
    private TextArea passiveField;
    @FXML
    private Button saveCharacterInPortalButton;

    private File imageFile;

    private Character_portalDAO characterPortalDAO = new Character_portalDAO();
    /**
     * This method is used to load an image file from the disk.
     * It opens a file chooser dialog that allows the user to select an image file.
     * The selected image file is then read into an Image object and displayed in the ImageView.
     */
    @FXML
    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imageView.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            try {
                InputStream is = new FileInputStream(imageFile);
                Image image = new Image(is);
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                ErrorLog.fileRead(e);
                showAlert("Error al cargar la imagen: ");
            }
        }
    }
    /**
     * This method is used to save a character to the database.
     * It first checks if all the required fields are filled and an image is selected.
     * If all the required information is provided, it reads the image file into a byte array.
     * Then it creates a new Characters object and sets its properties using the information provided in the fields.
     * It then checks if a character with the same ID already exists in the database.
     * If a character with the same ID does not exist, it saves the new character to the database.
     */
    @FXML
    private void saveCharacter() {
        String name = nameField.getText();
        String id = idField.getText();
        String type = typeField.getText();
        String characterClass = characterClassField.getText();
        String categories = categoriesField.getText();
        String superAttack = superAttackField.getText();
        String ultraSuperAttack = ultraSuperAttackField.getText();
        String rarety = raretyField.getText();
        String passive = passiveField.getText();

        if (name.isEmpty() || imageFile == null) {
            showAlert("Por favor, complete todos los campos y seleccione una imagen.");
            return;
        }

        try {
            byte[] imageData = new byte[(int) imageFile.length()];
            FileInputStream fis = new FileInputStream(imageFile);
            fis.read(imageData);
            fis.close();

            try {
                id = String.valueOf(Integer.parseInt(idField.getText()));
            } catch (NumberFormatException ex) {
                showAlert("El ID debe ser un número entero: " + ex.getMessage());
                return;
            }

            Characters character = new Characters();
            character.setId_character(Integer.parseInt(id));
            character.setType(type);
            character.setCharacter_class(characterClass);
            character.setName(name);
            character.setCategories(categories);
            character.setSuperAttack(superAttack);
            character.setUltraSuperAttack(ultraSuperAttack);
            character.setRarety(rarety);
            character.setPassive(passive);
            character.setVisual(imageData);
            CharactersDAO charactersDAO = new CharactersDAO();

            if (charactersDAO.findById(character) != null) {
                showAlert("El ID del personaje ya existe. Por favor, elija un ID único.");
                return;
            }
            charactersDAO.save(character);
            showAlert("Personaje guardado con éxito!");
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * This method is used to save a character to a specific portal in the database.
     * It first checks if all the required fields are filled and an image is selected.
     * If all the required information is provided, it reads the image file into a byte array.
     * Then it creates a new Characters object and sets its properties using the information provided in the fields.
     * It then checks if a character with the same ID already exists in the database.
     * If a character with the same ID does not exist, it saves the new character to the database.
     * Then it adds the new character to the specified portal and saves the portal to the database.
     */
    @FXML
    private void saveCharacterInPortal() {
        String name = nameField.getText();
        String id = idField.getText();
        String type = typeField.getText();
        String characterClass = characterClassField.getText();
        String categories = categoriesField.getText();
        String superAttack = superAttackField.getText();
        String ultraSuperAttack = ultraSuperAttackField.getText();
        String rarety = raretyField.getText();
        String passive = passiveField.getText();

        if (name.isEmpty() || imageFile == null) {
            showAlert("Por favor, complete todos los campos y seleccione una imagen.");
            return;
        }
        try {
            byte[] imageData = new byte[(int) imageFile.length()];
            FileInputStream fis = new FileInputStream(imageFile);
            fis.read(imageData);
            fis.close();
            try {
                id = String.valueOf(Integer.parseInt(idField.getText()));
            } catch (NumberFormatException ex) {
                showAlert("El ID debe ser un número entero: ");
                return;
            }

            Characters character = new Characters();
            character.setId_character(Integer.parseInt(id));
            character.setType(type);
            character.setCharacter_class(characterClass);
            character.setName(name);
            character.setCategories(categories);
            character.setSuperAttack(superAttack);
            character.setUltraSuperAttack(ultraSuperAttack);
            character.setRarety(rarety);
            character.setPassive(passive);
            character.setVisual(imageData);
            CharactersDAO charactersDAO = new CharactersDAO();
            if (charactersDAO.findById(character) == null) {
                charactersDAO.save(character);
            }
            Character_portal portalEntity = new Character_portal();
            portalEntity.setId_portal(2);
            Character_portal entity = characterPortalDAO.findById(portalEntity);
            if (entity == null) {
                showAlert("No se encontró el portal con la ID especificada.");
                return;
            }
            List<Characters> characters = entity.getFeatured_characters();
            if (characters == null) {
                characters = new ArrayList<>();
            }
            characters.add(character);
            entity.setFeatured_characters(characters);
            characterPortalDAO.insertIntoLocated(entity, Integer.parseInt(id));
            showAlert("Personaje guardado con éxito en el portal!");
        } catch (Exception e) {
            ErrorLog.fileRead(e);
            showAlert("Error al guardar el personaje en el portal: ");
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
        ErrorLog.logMessage(message);
    }
    /**
     * This method is used to navigate to the character repository screen.
     * It tries to set the root of the application to the "charactersRepo" screen.
     * If an error occurs during this process, it logs the error and displays an alert.
     */
    @FXML
    public void goToCharacterRepo() {
        try {
            App.setRoot("charactersRepo");
        } catch (IOException e) {
            ErrorLog.fileRead(e);
            showAlert("Error al ir al repositorio de personajes: ");
        }
    }
    /**
     * This method is used to navigate to the admin panel screen.
     * It tries to set the root of the application to the "adminPanel" screen.
     * If an error occurs during this process, it logs the error and displays an alert.
     */
    @FXML
    public void goToAdminPanel() {
        try {
            App.setRoot("adminPanel");
        } catch (IOException e) {
            ErrorLog.fileRead(e);
            showAlert("Error al ir al repositorio de personajes: " + e.getMessage());
        }
    }
}