package com.github.Frenadol.view;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Characters;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AdminMenuController {
    @FXML
    private Integer idField;

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
    private TextField passiveField;

    private File imageFile;

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
        // Obtener otros atributos del formulario y validarlos si es necesario

        if (name.isEmpty() || imageFile == null) {
            showAlert("Por favor, complete todos los campos y seleccione una imagen.");
            return;
        }

        try {
            byte[] imageData = new byte[(int) imageFile.length()];
            FileInputStream fis = new FileInputStream(imageFile);
            fis.read(imageData);
            fis.close();

            int id = idField != null ? idField.intValue() : 0; // Obtener el valor entero del campo idField

            Characters character = new Characters();
            character.setId_character(id);
            character.setName(name);
            character.setVisual(imageData);

            CharactersDAO charactersDAO = new CharactersDAO();

            // Verificar si el ID ya existe en la base de datos
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


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
