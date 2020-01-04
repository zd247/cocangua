package controller;

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
    public Label faceDice1;
    @FXML
    private BorderPane container;
    @FXML
    private Button rollDiceBtn;

    private int moveAmount;
    Map map;

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


//        House hs1 = map.getHouseMap().get(1);
//
//        double x = hs1.getLayoutX() + Map.HOUSE_LONG_SIDE/2;
//        double y = hs1.getLayoutY() + Map.HOUSE_SHORT_SIDE/2;
//
//        PieceView test = new PieceView(PIECE_COLOR[0]);
//        test.setLayoutX(x);
//        test.setLayoutY(y);
////        test.moveToHouse(map,2,3);
//
//        map.getChildren().add(test);


//        Run through nest color and create piece
        for (int i = 0; i < 4; i++){
            //nest id

            for (int j = 0; j < 4 ; j++ ){
                //piece id

                int finalI = i;
                Piece piece = new Piece(i,-1);
                //current position is in the nest

                PieceView p = new PieceView(PIECE_COLOR[i]);
                p.startPosition(map, j, i );
                p.setOnMouseClicked(event -> {
                    //First set the piece block is false, should be deleted
                    piece.setBlocked(false);
                    if (piece.getMove() + moveAmount < 48){
                        //when the piece does not go enough 1 round (48 spaces)

                        int nextPosition = p.movePiece(piece.getCurrentPosition(),moveAmount,map,Map.REGION_COLOR[finalI],piece.isHome(),piece.isBlocked());
                        //To make the piece move according to the moveAmount and set current position for the piece

                        piece.setCurrentPosition(nextPosition);
                        if (piece.isHome()){
                            piece.setHome(false);
                            piece.setMove(1);
                        }
                        else {
                            piece.setMove(piece.getMove()+moveAmount);
                        }
                    }
                    else{
                        int leftAmount = 0;
                        if (!piece.isFinished()){
                            leftAmount = 48 - piece.getMove() ;
                            p.movePiece(piece.getCurrentPosition(),leftAmount,map,Map.REGION_COLOR[finalI],piece.isHome(),piece.isBlocked());
                            piece.setFinished(true);
                            piece.setCurrentPosition(-1);
                            piece.setMove(48);
                        }
                        if (piece.getCurrentPosition() + moveAmount > 5){
                            piece.setBlocked(true);
                        }
                        else{
                            piece.setCurrentPosition(p.moveToHouse(map,piece.getCurrentPosition(),finalI,moveAmount - leftAmount ,piece.isBlocked()));
                            System.out.println(piece.getCurrentPosition());
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
     * @param mouseEvent
     * @return void;
     */
    @FXML
    private void rollDice(MouseEvent mouseEvent) {
        //Roll dice here, wilasdasd

        Dice dice = new Dice();
        rollDiceBtn.setOnMouseClicked(event -> {
            dice.roll();
            moveAmount = dice.getFace();
            faceDice1.setText(""+moveAmount);
        });
    }
}
