package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.model.entity.Characters;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterUserController {
    @FXML
    private TextField textUsername;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField password;

    private UsersDAO usersDAO = new UsersDAO();
    private CharactersDAO charactersDAO = new CharactersDAO();

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
        newUser.setDragon_stones(9999);
        newUser.setAdmin(true);


        usersDAO.insertUser(newUser);



        showAlert("Usuario registrado con éxito!");
    }

    public void goToInitialMenu() throws IOException {
        App.setRoot("initialMenu");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
    @FXML private void onClose(){
        Users user = Session.getInstance().getUserLogged();

    }
}
