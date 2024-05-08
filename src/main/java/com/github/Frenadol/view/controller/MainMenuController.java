package com.github.Frenadol.view.controller;

import com.github.Frenadol.App;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void switchToCharacterList() throws Exception {
        App.setRoot("CharacterList");
    }

}
