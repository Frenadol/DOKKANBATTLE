package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.Security;
import com.github.Frenadol.utils.ErrorLog;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField password;

    private UsersDAO usersDAO = new UsersDAO();

    /**
     * This method is used to log in a user.
     * It gets the username and password from the text fields, finds the user in the database,
     * checks if the entered password matches the stored password, and if it does, it logs in the user and switches to the main menu.
     * If the password does not match or the user does not exist, it displays an error message.
     */

    @FXML
    public void login() {
        try {
            String username = textUsername.getText();
            String pass = password.getText();

            Users userLogin = usersDAO.build().findByName(username);

            if (userLogin != null) {
                if (checkPassword(pass, userLogin.getPassword()) && username.equals(userLogin.getName_user())) {
                    Session.getInstance().logIn(userLogin);
                    App.setRoot("mainMenu");
                } else {
                    String message = "Contraseña invalida, introduzca una contraseña valida";
                    showAlert(message);
                    ErrorLog.logMessage(message);
                }
            } else {
                String message = "Usuario incorrecto, introduzca bien el nombre de usuario";
                ErrorLog.fileRead(new IOException());
                showAlert(message);
                ErrorLog.logMessage(message);
            }
        } catch (IOException e) {
            ErrorLog.fileRead(e);
            ErrorLog.logMessage("Error in login: " + e.getMessage());
        }
    }
    /**
     * This method is used to check if the entered password matches the stored password.
     * It hashes the entered password and compares it with the stored password.
     * If the hashing process fails, it displays an error message and returns false.
     * @param enteredPassword The password entered by the user.
     * @param storedPassword The password stored in the database.
     * @return true if the entered password matches the stored password, false otherwise.
     */
    public boolean checkPassword(String enteredPassword, String storedPassword) {
        try {
            String hashedEnteredPassword = Security.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPassword);
        } catch (NoSuchAlgorithmException e) {
            String message = "Error al hashear la contraseña.";
            showAlert(message);
            ErrorLog.logMessage(message);
            return false;
        }
    }
    /**
     * This method is used to display an alert dialog with a specified message.
     * It creates a new Alert object, sets the provided message as its content, and then displays the alert.
     * @param message The message to be displayed in the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
    /**
     * This method is called when the view is closed.
     * Currently, it does not perform any actions.
     */
    @FXML
    private void onClose(){
    }
    /**
     * This method is used to navigate to the admin login screen.
     * It tries to set the root of the application to the "adminLogin" screen.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    private void goToAdminMenu() {
        try {
            App.setRoot("adminLogin");
        } catch (IOException e) {
            ErrorLog.fileRead(e);
            ErrorLog.logMessage("Error going to admin menu: " + e.getMessage());
        }
    }
    /**
     * This method is used to navigate to the initial menu screen.
     * It tries to set the root of the application to the "initialMenu" screen.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    public void goToInitialMenu() throws IOException {
        App.setRoot("initialMenu");
    }
}