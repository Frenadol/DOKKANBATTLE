package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
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
    private Button loadImageButton;
    @FXML
    private Button saveCharacterButton;
    @FXML
    private Button saveCharacterInPortalButton;

    private File imageFile;

    private Character_portalDAO characterPortalDAO = new Character_portalDAO();

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
                e.printStackTrace();
            }
        }
    }

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
                showAlert("El ID debe ser un número entero.");
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
            e.printStackTrace();
            showAlert("Error al guardar el personaje. Inténtelo de nuevo.");
        }
    }

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
                showAlert("El ID debe ser un número entero.");
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
            e.printStackTrace();
            showAlert("Error al guardar el personaje en el portal. Inténtelo de nuevo.");
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    public void goToCharacterRepo() throws IOException {
        App.setRoot("charactersRepo");
    }
    @FXML
    public void goToAdminPanel() throws IOException {
        App.setRoot("adminPanel");
    }
}