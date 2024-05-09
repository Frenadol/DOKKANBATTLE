package com.github.Frenadol.view;

import com.github.Frenadol.App;
import javafx.fxml.FXML;

public class InitialMenuController {

    @FXML
    private void switchToLogin() throws Exception {
        App.setRoot("Login");
    }

    @FXML
    private void switchToRegister() throws Exception {
        App.setRoot("registerUser");
    }
}
