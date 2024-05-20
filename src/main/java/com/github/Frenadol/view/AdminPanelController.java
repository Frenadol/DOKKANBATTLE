package com.github.Frenadol.view;

import com.github.Frenadol.App;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanelController {
    /**
     * This method is used to navigate to the create characters menu.
     * It tries to set the root of the application to the "adminMenu" screen.
     * If an error occurs during this process, it throws an IOException.
     */
    @FXML
    private void goToCreateCharactersMenu() throws IOException {
        App.setRoot("adminMenu");
    }
    /**
     * This method is used to navigate to the update characters menu.
     * It tries to set the root of the application to the "CharactersUpdateMenu" screen.
     * If an error occurs during this process, it throws an IOException.
     */
    @FXML
    private void goToUpdateCharactersMenu() throws IOException {
        App.setRoot("CharactersUpdateMenu");
    }
    /**
     * This method is used to close the application.
     * It calls the exit method of the Platform class, which terminates the JavaFX application.
     */
    @FXML
    private void closeApp() throws IOException {
        Platform.exit();

    }
}
