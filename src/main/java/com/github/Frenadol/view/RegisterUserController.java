package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.utils.ErrorLog;
import com.github.Frenadol.utils.Security;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RegisterUserController {
    @FXML
    private TextField textUsername;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField password;

    private UsersDAO usersDAO = new UsersDAO();

    /**
     * This method is used to register a new user.
     * It gets the username and password from the text fields, checks if they are not empty and if the username does not already exist,
     * hashes the password, creates a new User object, sets its fields, and inserts it into the database.
     * If any of these steps fail, it displays an error message.
     */
    public void registerUser() {
        String username = textUsername.getText();
        String pass = password.getText();

        if (username.isEmpty() || pass.isEmpty()) {
            String message = "Por favor, complete todos los campos.";
            showAlert(message);
            ErrorLog.logMessage(message);
            return;
        }

        Users existingUser = usersDAO.findByName(username);
        if (existingUser != null) {
            String message = "El nombre de usuario ya está en uso. Por favor, elija otro.";
            showAlert(message);
            ErrorLog.logMessage(message);
            return;
        }


        Users newUser = new Users();
        newUser.setName_user(username);
        try {
            newUser.setPassword(Security.hashPassword(pass));
        } catch (NoSuchAlgorithmException e) {
            ErrorLog.fileRead(e);
            ErrorLog.logMessage("Error hashing password: " + e.getMessage());
            showAlert("Error al hashear la contraseña.");
            return;
        }
        newUser.setDragon_stones(750);
        newUser.setAdmin(false);
        try {
            usersDAO.insertUser(newUser);
        } catch (Exception e) {
            ErrorLog.fileRead(e);
            ErrorLog.logMessage("Error inserting user: " + e.getMessage());
            showAlert("Error al insertar el usuario.");
            return;
        }

        String message = "Usuario registrado con éxito!";
        showAlert(message);
        ErrorLog.logMessage(message);
    }
    /**
     * This method is used to navigate to the initial menu screen.
     * It tries to set the root of the application to the "initialMenu" screen.
     * If an error occurs during this process, it throws an IOException.
     */
    @FXML
    public void goToInitialMenu() throws IOException {
            App.setRoot("initialMenu");
    }
    /**
     * This method is used to display an alert dialog with a specified message.
     * It creates a new Alert object, sets the provided message as its content, and then displays the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    @FXML private void onClose(){

    }
}