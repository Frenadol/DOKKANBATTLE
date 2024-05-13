package com.github.Frenadol.view;

import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Session;
import com.github.Frenadol.model.entity.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SummonsMenuController {
    @FXML
    private TableView<Characters> tableView;

    @FXML
    private TableColumn<Characters, String> nameColumn;

    @FXML
    private void summonButtonClicked() {
        Users user = Session.getInstance().getUserLogged();
        UsersDAO usersDAO = new UsersDAO();
        int stonesRemaining = user.getDragon_stones() - 50;
        user.setDragon_stones(stonesRemaining);
        usersDAO.save(user);

        Character_portal character_portal = new Character_portal();
        character_portal.setId_portal(2);
        Character_portal portal = Character_portalDAO.build().findById(character_portal);
        System.out.println(portal);

        if (portal != null) {
            Character_portalDAO characterPortalDAO = Character_portalDAO.build();
            characterPortalDAO.findAllLocated(portal);

            List<Characters> characters = portal.getFeatured_characters();
            List<Characters> summonedCharacters = new ArrayList<>();
            if (characters != null && !characters.isEmpty()) {
                for (int i = 0; i < 10; i++) {
                    int randomIndex = generateRandomCharacterId(0, characters.size() - 1);
                    Characters character = characters.get(randomIndex);
                    if (character != null) {
                        if (!user.getCharacters_list().contains(character)) {
                            summonedCharacters.add(character);
                            user.getCharacters_list().add(character);
                        } else {
                            user.setDragon_stones(user.getDragon_stones() + 5);
                            System.out.println("¡Has obtenido un personaje repetido! Se han añadido 5 piedras a tu cuenta.");
                        }
                    }
                }
            }

            usersDAO.save(user);

            showSummonedCharactersDialog(summonedCharacters);
        } else {
            System.out.println("No se encontró el portal con id_portal igual a 2.");
        }
    }


    private void showSummonedCharactersDialog(List<Characters> summonedCharacters) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Personajes invocados");
        alert.setHeaderText("¡Has invocado nuevos personajes!");

        // Configurar la tabla en la ventana emergente
        TableView<Characters> dialogTableView = new TableView<>();
        TableColumn<Characters, String> dialogNameColumn = new TableColumn<>("Nombre");
        dialogNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // Usar "name" en lugar de "Name"
        dialogTableView.getColumns().add(dialogNameColumn);
        TableColumn<Characters, String> dialogVisualColumn = new TableColumn<>("Visual");
        dialogVisualColumn.setCellValueFactory(new PropertyValueFactory<>("visual")); // Usar "visual" en lugar de "Visual"
        dialogTableView.getColumns().add(dialogVisualColumn);

        dialogTableView.getItems().addAll(summonedCharacters);

        alert.getDialogPane().setContent(dialogTableView);

        alert.showAndWait();
    }

    public int generateRandomCharacterId(int minId, int maxId) {
        Random random = new Random();
        return random.nextInt((maxId - minId) + 1) + minId;
    }
}
