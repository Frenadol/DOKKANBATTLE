package com.github.Frenadol.view.controller;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField; // Asume que este es el campo de texto donde el usuario ingresa su nombre de usuario

    @FXML
    private PasswordField passwordField; // Asume que este es el campo de texto donde el usuario ingresa su contraseña

    @FXML
    private void handleLoginButtonAction() throws Exception {
        // Recupera los valores ingresados por el usuario
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Aquí es donde verificarías las credenciales del usuario con la base de datos
        UsersDAO usersDAO = new UsersDAO();
        Users user = usersDAO.findByName("Fernando");

        if (user != null) {
            // Si las credenciales son correctas, establece al usuario como el usuario actual
            SessionManager.getInstance().setCurrentUser(user);

            // Cambia a la ventana MainMenu
            App.setRoot("MainMenu");
        } else {
            // Maneja el caso en que las credenciales no son correctas
            System.out.println("Las credenciales no son correctas");
        }
    }
}
