package com.github.Frenadol.view;

import com.github.Frenadol.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminPanelController {

    @FXML
    private void goToCreateCharactersMenu() throws IOException {
        App.setRoot("adminMenu");
    }

    @FXML
    private void goToUpdateCharactersMenu() throws IOException {
        App.setRoot("CharactersUpdateMenu");
    }
}
