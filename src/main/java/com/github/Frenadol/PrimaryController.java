package com.github.Frenadol;

import java.io.IOException;

import com.github.Frenadol.model.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
