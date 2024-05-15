package com.github.Frenadol.view;

import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SummonedCharactersController implements Initializable {

    @FXML
    private TableView<Characters> summonedCharactersTableView;
    @FXML
    private TableColumn<Characters, String> nameColumn;
    @FXML
    private TableColumn<Characters, ImageView> visualColumn;

    private ObservableList<Characters> observableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList = FXCollections.observableArrayList();
        summonedCharactersTableView.setItems(observableList);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        visualColumn.setCellValueFactory(characters -> {
            byte[] visualData = characters.getValue().getVisual();
            if (visualData != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(visualData);
                Image image = new Image(bis);

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setOnMouseClicked(event -> {
                    Stage stage = new Stage();
                    ImageView imageViewFull = new ImageView(image);
                    StackPane layout = new StackPane(imageViewFull);
                    Scene scene = new Scene(layout);
                    stage.setScene(scene);
                    stage.show();
                });

                return new SimpleObjectProperty<>(imageView);
            } else {
                System.out.println("visualData es null");
                return null;
            }
        });
    }

    public void setSummonedCharacters(List<Characters> summonedCharacters) {
        observableList.addAll(summonedCharacters);
    }
    @FXML private void onClose(){

    }
}
