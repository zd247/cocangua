package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

    public Label playerName;

    @FXML
    private BorderPane container;

    @FXML
    private Button rollDiceBtn;

    Player[] players = {new Player("Player 1"),new Player("Player 2"),new Player("Player 3"),new Player("Player 4"),};

    private int moveAmount;

    Map map;
    private int id = -1;
    int NUM_OF_PLAYER = 4;

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
        for (int nestId = 0; nestId < Map.REGION_COLOR.length; nestId++){
            //nest id

            for (int pieceId = 0; pieceId < 4; pieceId++){
                //piece id

                int finalI = nestId;
                Piece piece = new Piece(nestId,-1);
                //current postion is home

                PieceView p = new PieceView(PIECE_COLOR[nestId]);
                p.startPosition(map, pieceId, nestId );
                p.setOnMouseClicked(event -> {
                    if(players[finalI].isRolled()) {
                        if (id == finalI) {
                            if (piece.getCurrentPosition() != -1 || players[finalI].getDices()[0].getFace() == 6 || players[finalI].getDices()[1].getFace() == 6) {
                                if (piece.getMove() + moveAmount < 48){
                                    //when the piece runs 1 round of map

                                    players[finalI].resetCheck();

                                    piece.setBlocked(false);
                                    //First set the piece block is false, should be deleted

                                    int nextPosition = p.movePiece(piece.getCurrentPosition(), moveAmount, map, Map.REGION_COLOR[finalI], piece.isHome(), piece.isBlocked());
                                    //To make the piece move with MoveAmount step

                                    piece.setCurrentPosition(nextPosition);
                                    //and get the next position for another turn

                                    if (piece.isHome()) {
                                        //once the piece get out of the nest

                                        piece.setHome(false);
                                        piece.setMove(1);
                                        //start move at 1
                                    }
                                    else
                                        piece.setMove(piece.getMove()+moveAmount);
                                }
                            }
                        }
                    }
                });
                map.getChildren().add(p);
            }
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
        if (id == 3){
            id = 0;
        }
        else {
            id++;
        }
        //Roll dice here, wilasdasd
        players[id].roll();
        moveAmount = players[id].getDices()[0].getFace() + players[id].getDices()[1].getFace();
        faceDice1.setText("" + players[id].getDices()[0].getFace());
        faceDice2.setText("" + players[id].getDices()[1].getFace());
        playerName.setText("" + players[id].getName());
    }
}
