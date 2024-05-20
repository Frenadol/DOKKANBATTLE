package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.utils.ErrorLog;
import javafx.fxml.FXML;

public class InitialMenuController {

    /**
     * This method is used to switch the view to the Login screen.
     * It tries to set the root of the application to the "Login" screen.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    private void switchToLogin() {
        try {
            App.setRoot("Login");
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }

    /**
     * This method is used to switch the view to the Register User screen.
     * It tries to set the root of the application to the "registerUser" screen.
     * If an error occurs during this process, it logs the error.
     */
    @FXML
    private void switchToRegister() {
        try {
            App.setRoot("registerUser");
        } catch (Exception e) {
            ErrorLog.fileRead(e);
        }
    }
}