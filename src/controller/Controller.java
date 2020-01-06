package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Sound.THEME;


/**
 * Input listeners
 */

public class Controller implements Initializable {
    @FXML
    private BorderPane container;
    @FXML
    private HBox topBar;
    @FXML
    private HBox bottomBar;
    @FXML
    private Button pauseBtn;
    @FXML
    private ToggleButton soundBtn;
    @FXML
    private ChoiceBox<String> languageBox;


    int NUM_OF_PLAYER = 4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map map = new Map();
        // draw board
        container.setCenter(map);

        Player[] players = initPlayers(NUM_OF_PLAYER);

        /**===========================[Test code goes here]===========================*/

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName());
            //TODO:
            //get piece using player

            //get piece using nest

            //get piece
        }

        Sound.playSound(THEME); // play sound
        Dice dice1 = new Dice();    // add dices
        Dice dice2 = new Dice();
        topBar.getChildren().addAll(dice1, dice2);

        /**===========================[End of view.test code]===========================*/
    }

    private Player[] initPlayers(int numOfPlayer) {
        Player[] rets = new Player[numOfPlayer];
        for (int i = 0; i < rets.length; i++) {
            //player
            String name = "Player" + i; // TO-DO (load from input file FileStream when user finishes the start menu)
            Player player = new Player(name,i);

            rets[i] = player; // assign

        }
        return rets;
    }

}

