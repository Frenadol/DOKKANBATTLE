package com.github.Frenadol.view;

import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class RegisterUserController {
    @FXML
    private TextField textUsername;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField password;

    private UsersDAO usersDAO = new UsersDAO();

    @FXML
    public void registerUser() {
        String username = textUsername.getText();
        String pass = password.getText();

        if (username.isEmpty() || pass.isEmpty()) {
            showAlert("Por favor, complete todos los campos.");
            return;
        }

        Users existingUser = usersDAO.findByName(username);
        if (existingUser != null) {
            showAlert("El nombre de usuario ya está en uso. Por favor, elija otro.");
            return;
        }

        Users newUser = new Users();
        newUser.setName_user(username);
        newUser.setPassword(pass);
        newUser.setDragon_stones(500);
        newUser.setAdmin(false);

        usersDAO.updateUser(newUser);

        showAlert("Usuario registrado con éxito!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
