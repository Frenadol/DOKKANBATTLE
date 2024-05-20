package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.Security;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class AdminLoginController {
    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField password;

    private UsersDAO usersDAO = new UsersDAO();
    /**
     * Handles the login action.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void login() throws IOException {
        String username = textUsername.getText();
        String pass = password.getText();

        Users userLogin = usersDAO.build().findByName(username);
        if (userLogin != null) {
            try {
                if (Security.hashPassword(pass).equals(userLogin.getPassword()) && username.equals(userLogin.getName_user()) && userLogin.isAdmin()) {
                    Session.getInstance().logIn(userLogin);
                    App.setRoot("adminPanel");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Credenciales incorrectas para el acceso de administrador. Introduzca las credenciales correctas.");
                    alert.show();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Usuario incorrecto, introduzca correctamente el nombre de usuario.");
            alert.show();
        }
    }
    /**
     * Closes the login view.
     */
    @FXML
    private void onClose() {

    }
}