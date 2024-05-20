package com.github.Frenadol.view;

import com.github.Frenadol.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;
    /**
     * This method is called when the view is opened.
     * It changes the scene to the initial menu.
     */
    @Override
    public void onOpen(Object input) throws IOException {
            changeScene(Scenes.INITIALMENU,null);
    }
    /**
     * This method is used to change the scene.
     * It loads the specified scene, sets it as the center of the border pane, and opens it with the provided data.
     */
    public void changeScene(Scenes scene,Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }



    /**
     * This method is called when the view is closed.
     * Currently, it does not perform any actions.
     */
    @Override
    public void onClose(Object output) {
    }
    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * Currently, it does not perform any actions.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    /**
     * This method is used to load a scene from an FXML file.
     * It creates a new FXMLLoader, loads the FXML file, and returns a new View object containing the loaded scene and its controller.
     */
    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }
    /**
     * This method is used to close the application.
     * It calls the exit method of the System class, which terminates the Java Virtual Machine.
     */
    @FXML
    private void closeApp(){
        System.exit(0);
    }

}
