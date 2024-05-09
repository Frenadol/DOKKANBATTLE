package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField password;

    private UsersDAO usersDAO = new UsersDAO();

    @FXML
    public void login() throws IOException {
        String username = textUsername.getText();
        String pass = password.getText();

        Users userLogin = usersDAO.build().findByName(username);

        if (userLogin != null) {
            if (pass.equals(userLogin.getPassword()) && username.equals(userLogin.getName_user()) ) {
                App.setRoot("mainMenu");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Contraseña invalida, introduzca una contraseña valida");
                alert.show();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Usuario incorrecto, introduzca bien el nombre de usuario");
            alert.show();
        }
    }
}

