package com.github.Frenadol.view.controller;

import com.github.Frenadol.App;
import javafx.fxml.FXML;

public class InitialMenuController {

    @FXML
    private void switchToLogin() throws Exception {
        // Cambia a la ventana de inicio de sesión
        App.setRoot("login");
    }

    @FXML
    private void switchToRegister() throws Exception {
        // Cambia a la ventana de registro
        App.setRoot("registerUser");
    }
}
