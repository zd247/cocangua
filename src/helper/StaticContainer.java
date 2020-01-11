package helper;

import controller.GameController;
import controller.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import model.*;
import model.core.*;


import java.util.HashMap;

import static javafx.scene.paint.Color.BLACK;

public class StaticContainer { // can be made singleton but not necessary
    // containers
    public static Player[] players = new Player[4];

    public static PlayerField[] playerFields = new PlayerField[4];

    // A map to store all circle positions
    public static HashMap<Integer, Space> spaceMap = new HashMap<>();

    // A map to store all nests with colors as their key
    public static HashMap<Integer, Nest> nestMap = new HashMap<>();

    // Maps to store all houses
    public static HashMap<Integer, House> houseMap = new HashMap<>();

    public static Piece currentPiece = null;

    public static GameController gameController ;

    public static int POLLING_INTERVAL = 1000; //miliseconds


    public static MenuController menuController;

    public static Language language = new Language("en", "US");

    public static ObservableList<String> availableChoices = FXCollections.observableArrayList( "English","Tiếng Việt");

    //TURN LOGIC STATICS
    public static int turn = 0;
    public static int globalNestId = -1;
    public static int diceTurn = 0;
    public static int playerMoveAmount = 0;
    public static int diceValue1 = 0;
    public static int diceValue2 = 0;

    public enum ConnectionStatus {
        PLAYER, BOT, OFF
    }


    //=========================================================================================


    /**
     * create a player on the fly globally
     * @param nestId assigned id to the player (please use nestId)
     * @return the player with the nestId param (since this player will be added to the global player list.)
     */
    public static Player createPlayer(int nestId) {
       Player player = new Player(nestId, "Player" + nestId);
       player.setConnectionStatus(ConnectionStatus.OFF);


       return player;
    }


    /**
     * return the color of the player reference by nestId (limit calling this function, bad logic)
     * @param nestId reference id
     */

    public static Color getColorByNestId(int nestId) {
        Nest ret = new Nest(nestId);
        return ret.getColor();
    }

    /**
     * return nest by id, only call this function after Map is initialized
     * @param nestId
     * @return
     */
    public static Nest getNestById(int nestId) {
        return nestMap.get(nestId);
    }

    /**
     *
     * @param dice1
     * @param dice2
     */
    public static void setDiceOnClick(Dice dice1, Dice dice2) {
        dice1.setOnMouseClicked(event -> {
            if (turn == 0) {
                turn = 1;
                if (globalNestId >= (players.length - 1)) {
                    globalNestId = 0;
                } else {
                    globalNestId++;
                }

                //check for default case
                if (globalNestId != -1 && players[globalNestId].getConnectionStatus() == ConnectionStatus.OFF){
                    while (!(players[globalNestId].getConnectionStatus() == ConnectionStatus.PLAYER)) {
                        globalNestId++;
                        if (globalNestId > (players.length - 1)) {
                            globalNestId = 0;
                        }
                    }
                }

                //start rolling
                diceValue1 = dice1.roll();
                diceValue2 = dice2.roll();
                System.out.println("Player" + globalNestId + " " + diceValue1);
                System.out.println("Player" + globalNestId + " " + diceValue2);

                //draw indicator
                for (int i = 0; i < players.length; i++) {
                    if (i != globalNestId) {
                        players[i].resetCheck();
                    } else {
                        spaceMap.get(i).rect.setStroke(BLACK);
                        spaceMap.get(i).rect.setStrokeWidth(10);
                        players[i].rolled();
                    }
                    spaceMap.get(i).rect.setStrokeWidth(0);

                }

                //check when clean board, reset turn counter
                if (allAtHome(globalNestId) && diceValue1 != 6 && diceValue2 != 6) {
                    players[globalNestId].resetCheck();
                    if (diceValue1 == diceValue2) {
                        globalNestId--;
                    }
                    turn = 0;
                }

                else {
                    if (!nestMap.get(globalNestId).getPieceList()[0].ableToMove(diceValue1,diceTurn)
                            && !nestMap.get(globalNestId).getPieceList()[0].ableToMove(diceValue2,diceTurn)
                            && !canDeploy(globalNestId)
                            && !ableToKick(diceValue1,globalNestId)
                            && !ableToKick(diceValue2,globalNestId)
                            && !pieceInHouseCanMove(globalNestId,diceValue1)
                            && !pieceInHouseCanMove(globalNestId,diceValue2) ) {
                        players[globalNestId].resetCheck();
                        if (diceValue1 == diceValue2) {
                            globalNestId--;
                        }
                        turn = 0;
                    }

                    System.out.println("--------------");
                }

            }
        });
    }

    /**
     *
     * @param nestId
     * @return
     */
    static boolean allAtHome(int nestId){
        for (int i = 0; i < 4; i ++){
            if (getNestById(nestId).getPieceList()[i].getCurrentPosition() != - 1){
                return false;
            }
        }
        return true;
    }


    public static boolean canDeploy(int nestID){
        if(diceValue1 == 6 || diceValue2 == 6) {
            for (int i = 0; i < 4; i ++){
                if (nestMap.get(nestID).getPieceList()[i].getCurrentPosition() == -1){
                    return true;
                }
            }
        }
        return false;
    }


    static boolean ableToKick(int moveAmount, int nestId){
        Piece piece;
        for (int i =0; i< 4; i++) {
            if(getNestById(nestId).getPieceList()[i].getCurrentPosition() != -1 && getNestById(nestId).getPieceList()[i].getStep() < 48){
                piece =getNestById(nestId).getPieceList()[i];
                if (piece.ableToKick(moveAmount)){
                    return true;
                }
            }
        }
        return false;
    }

    static boolean pieceInHouseCanMove(int nestId, int diceValue){
        Piece piece;
        for (int i = 0; i < 4;i++){
            if (getNestById(nestId).getPieceList()[i].getStep() >= 48 && getNestById(nestId).getPieceList()[i].getStep() < getNestById(nestId).getPieceList()[i].getHouseArrival() + 5 && (getNestById(nestId).getPieceList()[i].getCurrentPosition()-getNestById(nestId).getPieceList()[i].getHouseArrival() + 2) == diceValue){
                piece = getNestById(nestId).getPieceList()[i];
                if (!piece.blockHome(diceValue)){
                    return true;
                }
            }
        }
        return false;
    }

//    public static ConnectionStatus getPlayerConnectionStatusById() {
//
//    }

    public static void updatePoint(GameController c){
        gameController = c;
        c.scoreLbBlue.setText(players[0].getPoints()+"");
        c.scoreLbYellow.setText(players[1].getPoints()+"");
        c.scoreLbGreen.setText(players[2].getPoints()+"");
        c.scoreLbRed.setText(players[3].getPoints()+"");
    }


    public static void changeChoiceBoxInGame(GameController c){
        gameController = c;
        setChoiceBox(c.languageBox);
    }

    public static void changeChoiceBoxInMenu(MenuController c){
        menuController = c;
        setChoiceBox(c.languageBox);
    }

    private static void setChoiceBox(ChoiceBox<String> choiceBox){
        choiceBox.setItems(availableChoices);
        if (language.getLocale().contains("en"))
            choiceBox.setValue("English");
        else
            choiceBox.setValue("Tiếng Việt");
    }
}
