package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Input listeners
 */

public class Controller implements Initializable {
    @FXML
    public Label faceDice1; // Dice 1's rolled number

    @FXML
    public Label faceDice2; // Dice 2's rolled number

    @FXML
    private BorderPane container;

    @FXML
    private Button rollDiceBtn;


    int NUM_OF_PLAYER = 4;

    final private static Color[] PIECE_COLOR = {Color.DODGERBLUE, Color.GOLD, Color.SEAGREEN, Color.TOMATO};
    //Piece color

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map map = new Map();
        // draw board
        container.setCenter(map);

        Player[] players = generatePlayers(NUM_OF_PLAYER);


        /**Test code goes here*/

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName());
        }


        /***/
    }

    private Player[] generatePlayers(int numOfPlayer) {
        Player[] rets = new Player[numOfPlayer];
        for (int i = 0; i < rets.length; i++) {
            //player
            String name = "Player" + i; // TO-DO (load from input file FileStream when user finishes the start menu)
            Player player = new Player(name,i);
            //nest





            rets[i] = player; // assign

        }
        return rets;
    }




    @FXML
    private void rollDice(MouseEvent mouseEvent) {
//        if (id == 3){
//            id = 0;
//        }
//        else {
//            id++;
//        }
//
//        players[id].roll();
//        moveAmount = players[id].getDices()[0].getFace() + players[id].getDices()[1].getFace();
//        faceDice1.setText("" + players[id].getDices()[0].getFace());
//        faceDice2.setText("" + players[id].getDices()[1].getFace());
//        playerName.setText("" + players[id].getName());
    }
}

