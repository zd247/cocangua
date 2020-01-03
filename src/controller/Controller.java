package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private BorderPane container;
    @FXML
    private Button rollDiceBtn;

    Map map;

    int NUM_OF_PLAYER = 4;
    Player[] players = new Player[NUM_OF_PLAYER];

    /**
     * Set color
     */
    final private static Color[] PIECE_COLOR = {Color.BEIGE, Color.BLUEVIOLET, Color.RED, Color.KHAKI};
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
        int moveAmount = 3;
        //Run through nest color and create piece
        for (int i = 0; i < Map.REGION_COLOR.length; i++){
            int finalI = i;
            Piece piece = new Piece(i,-1);
            PieceView p = new PieceView(PIECE_COLOR[i]);
            p.startPosition(map,Map.REGION_COLOR[i]);
            p.setOnMouseClicked(event -> {
                //First set the piece block is false, should be deleted
                piece.setBlocked(false);
                int nextPosition = p.movePiece(piece.getCurrentPosition(),moveAmount,map,Map.REGION_COLOR[finalI],piece.isHome(),piece.isBlocked());
                piece.setCurrentPosition(nextPosition);
                if (piece.isHome()){
                    piece.setHome(false);
                }
            });
            map.getChildren().add(p);
        }

        /***/


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
