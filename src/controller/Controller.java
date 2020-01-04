package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.*;
import view.PieceView;

import java.net.URL;
import java.util.ResourceBundle;

import static helper.Helper.*;

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

    Player player = new Player("Hello");

    private int moveAmount;

    Map map;
    private int check = 0;
    int NUM_OF_PLAYER = 4;
    Player[] players = new Player[NUM_OF_PLAYER];


    /**
     * Set color
     */
    final private static Color[] PIECE_COLOR = {Color.DODGERBLUE, Color.GOLD, Color.SEAGREEN, Color.TOMATO};
    //Piece color

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map = new Map();
        // draw board
        container.setCenter(map);

        createPlayers(NUM_OF_PLAYER);

        /**Test code goes here*/

//        for (int i = 0; i < players.length; i++) {
//            System.out.println(players[i].getName());
//        }

        //Move the piece with move amount 3



        //Run through nest color and create piece
        for (int i = 0; i < Map.REGION_COLOR.length; i++){
            int finalI = i;
            Piece piece = new Piece(i,-1);
            PieceView p = new PieceView(PIECE_COLOR[i]);
            p.startPosition(map, i);
            p.setOnMouseClicked(event -> {
                if(player.isRolled()) {
                    if (check == finalI) {
                        if (piece.getCurrentPosition() != -1 || player.getDices()[0].getFace() == 6 || player.getDices()[1].getFace() == 6) {
                            player.resetCheck();
                            //First set the piece block is false, should be deleted
                            piece.setBlocked(false);
                            int nextPosition = p.movePiece(piece.getCurrentPosition(), moveAmount, map, Map.REGION_COLOR[finalI], piece.isHome(), piece.isBlocked());
                            piece.setCurrentPosition(nextPosition);
                            if (piece.isHome()) {
                                piece.setHome(false);
                            }
                        }
                    }
                }
            });
            map.getChildren().add(p);
        }

        /***/


    }



    /**
     * Input listener for dice rolling button
     * @param
     * @return void;
     */
    @FXML
    private void rollDice(ActionEvent event) {
        //Roll dice here, wilasdasd
        if (check == 4){
            check = 0;
        }
        player.roll();
        moveAmount = player.getDices()[0].getFace() + player.getDices()[1].getFace();
        faceDice1.setText("" + player.getDices()[0].getFace());
        faceDice2.setText("" + player.getDices()[1].getFace());
        check++;
    }
}
