package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.utils.ErrorLog;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CharactersUpdateController extends Controller implements Initializable {
    @FXML
    private TableView<Characters> TableView;
    @FXML
    private TableColumn<Characters, Integer> id_CharacterColumn;
    @FXML
    private TableColumn<Characters, String> nameColumn;
    @FXML
    private TableColumn<Characters, String> passiveColumn;
    @FXML
    private TableColumn<Characters, ImageView> visualColumn;
    @FXML
    private Button backToAdminPanel;

    private ObservableList<Characters> observableList;

    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * It populates the TableView with characters from the database if it is empty.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TableView.getItems().isEmpty()) {
            List<Characters> charactersList = CharactersDAO.build().findAll();
            this.observableList = FXCollections.observableArrayList(charactersList);
            TableView.setItems(observableList);
            id_CharacterColumn.setCellValueFactory(characters -> new SimpleIntegerProperty(characters.getValue().getId_character()).asObject());
            nameColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getName()));
            passiveColumn.setCellValueFactory(characters -> new SimpleStringProperty(characters.getValue().getPassive()));
            visualColumn.setCellValueFactory(characters -> {
                byte[] visualData = characters.getValue().getVisual();
                if (visualData != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(visualData);
                    Image image = new Image(bis);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(200);
                    imageView.setOnMouseClicked(event -> {
                        Stage stage = new Stage();
                        ImageView imageViewFull = new ImageView(image);
                        StackPane layout = new StackPane(imageViewFull);
                        Scene scene = new Scene(layout);
                        stage.setScene(scene);
                        stage.show();
                    });
                    return new SimpleObjectProperty<>(imageView);
                } else {
                    System.out.println("visualData es null");
                    return null;
                }
            });
        }
    }
    /**
     * This method is used to update a character.
     * It gets the character selected in the TableView, displays a dialog for the user to select a field and enter a new value,
     * and if the user confirms, it checks if the new value already exists, and if not, it updates the character in the database.
     */
    @FXML
    private void updateCharacter() {
        Characters selectedCharacter = TableView.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Actualizar personaje");
            ButtonType acceptButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(acceptButtonType, ButtonType.CANCEL);
            ComboBox<String> fieldComboBox = new ComboBox<>();
            fieldComboBox.getItems().addAll("Pasiva", "Nombre");
            TextField newValueTextField = new TextField();
            GridPane grid = new GridPane();
            grid.add(new Label("Campo:"), 0, 0);
            grid.add(fieldComboBox, 1, 0);
            grid.add(new Label("Nuevo valor:"), 0, 1);
            grid.add(newValueTextField, 1, 1);
            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == acceptButtonType) {
                    if (fieldComboBox.getValue() == null || newValueTextField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Advertencia");
                        alert.setHeaderText("Campo o valor no seleccionado");
                        alert.setContentText("Por favor, selecciona un campo y proporciona un nuevo valor.");
                        alert.showAndWait();
                        return null;
                    }
                    return new Pair<>(fieldComboBox.getValue(), newValueTextField.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(fieldValue -> {
                String field = fieldValue.getKey();
                String newValue = fieldValue.getValue();

                Characters existingCharacter = null;
                switch (field) {
                    case "Pasiva":
                        existingCharacter = CharactersDAO.build().findByPassive(newValue);
                        break;
                    case "Nombre":
                        existingCharacter = CharactersDAO.build().findByName(newValue);
                        break;
                }

                if (existingCharacter != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Valor ya existente");
                    alert.setContentText("El valor que intentas usar ya existe. Por favor, elige un valor diferente.");

                    alert.showAndWait();
                } else {
                    switch (field) {
                        case "Pasiva":
                            selectedCharacter.setPassive(newValue);
                            break;
                        case "Nombre":
                            selectedCharacter.setName(newValue);
                            break;
                    }

                    CharactersDAO.build().save(selectedCharacter);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("No se seleccionó ningún personaje");
            alert.setContentText("Por favor, selecciona un personaje para actualizar.");
            alert.showAndWait();
        }
/**
 * This method is called when the view is opened.
 * Currently, it does not perform any actions.
 */
    }
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
     * This method is used to navigate to the admin panel.
     * It tries to set the root of the application to the "adminPanel" screen.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    private void backToAdminPanel() {
        try {
            App.setRoot("adminPanel");
        } catch (IOException e) {
            ErrorLog.fileRead(e);
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
    }
}