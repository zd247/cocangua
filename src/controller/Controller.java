package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.*;
import view.Map;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Input listeners
 */

public class Controller implements Initializable {
    @FXML
    public Label playerNameLb;
    @FXML
    public Button rollDiceBtn;
    @FXML
    private BorderPane container;

    Map map;
    StateManager playerStates;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map = new Map();  // Draw map
        container.setCenter(map);

        // Handle players logic
        playerStates = new StateManager();
    }
}
