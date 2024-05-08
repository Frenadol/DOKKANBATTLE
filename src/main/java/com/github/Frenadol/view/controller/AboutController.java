package com.github.Frenadol.view.controller;

import com.github.Frenadol.App;
import com.github.Frenadol.view.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController extends Controller implements Initializable {
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void goToMain() throws IOException {
        App.currentController.changeScene(Scenes.MAIN,null);
    }
}