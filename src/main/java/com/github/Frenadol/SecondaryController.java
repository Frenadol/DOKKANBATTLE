package com.github.Frenadol;

import java.io.IOException;

import com.github.Frenadol.model.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}