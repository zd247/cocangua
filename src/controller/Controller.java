package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Input listeners
 */

public class Controller implements Initializable {
    @FXML
    private Pane container;
    @FXML
    public Button rollDiceBtn;

    Board board;

    StateManager playerStates;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new Board ();
        // draw board
        container.getChildren().addAll(board);

        //handle players logic
        playerStates = new StateManager();
    }
}
