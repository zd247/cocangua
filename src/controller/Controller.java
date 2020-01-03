package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

import static helper.Helper.*;

/**
 * Input listeners
 */

public class Controller implements Initializable {
    @FXML
    private BorderPane container;
    @FXML
    private Button rollDiceBtn;

    Map map;

    int NUM_OF_PLAYER = 4;
    Player[] players = new Player[NUM_OF_PLAYER];


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // draw board
        map = new Map ();
        container.setCenter(map);

        createPlayers(NUM_OF_PLAYER);

//        /**Test code goes here*/
//
//        for (int i = 0; i < players.length; i++) {
//            System.out.println(players[i].getName());
//        }
//
//        /***/


    }



    /**
     * Input listener for dice rolling button
     * @param mouseEvent
     */
    @FXML
    private int rollDice(MouseEvent mouseEvent) {
        //Roll dice here, wilasdasd
        int ret = 0;

        return ret;
    }
}
