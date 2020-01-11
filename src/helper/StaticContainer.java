package helper;

import controller.GameController;
import controller.MenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public static int firstTurn = 0;
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
     * @param dice
     * - After selecting and indicating the name, the player consequently roll and then find out who will get the first roll (first turn).
     */



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
                    while ((players[globalNestId].getConnectionStatus() == ConnectionStatus.OFF)) {
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
                if (globalNestId != -1 && players[globalNestId].getConnectionStatus() == ConnectionStatus.BOT){
                    players[globalNestId].resetCheck();
                    bot_play();
                    turn = 0;
                    if (diceValue1 == diceValue2) globalNestId--;
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
        c.loadLangue(); //load language in menu
    }

    public static void changeChoiceBoxInMenu(MenuController c){
        //Referene from the game controller
        menuController = c;
        setChoiceBox(c.languageBox);
        c.loadLangue();//load language in game
    }

    private static void setChoiceBox(ChoiceBox<String> choiceBox){
        //To set settings for the choice box
        choiceBox.setItems(availableChoices);
        if (language.getLocale().contains("en"))
            choiceBox.setValue("English");//Set face of the choice box
        else
            choiceBox.setValue("Tiếng Việt");
    }



    private static void bot_play() {
        int check = 0;
        if ((diceValue1 == 6 || diceValue2 == 6)) {
            for (int ii = 0; ii < 4; ii++) {
                if (getNestById(globalNestId).getPieceList()[ii].getCurrentPosition() == -1) {
                    if (getNestById(globalNestId).getPieceList()[ii].isBlockedPiece(playerMoveAmount)) {
                        if (getNestById(globalNestId).getPieceList()[ii].ableToKick(playerMoveAmount)) {
                            int next = getNestById(globalNestId).getPieceList()[ii].getStartPosition(getNestById(globalNestId).getPieceList()[ii].getNestId());   //get the piece at start position
                            Piece kickedPiece = spaceMap.get(next).getPiece();
                            getNestById(globalNestId).getPieceList()[ii].kick(kickedPiece);
                            players[globalNestId].setPoints(players[globalNestId].getPoints() + 2);
                            players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);
                            spaceMap.get(next).setPiece(null);
                            spaceMap.get(next).setOccupancy(false);
                            getNestById(globalNestId).getPieceList()[ii].movePiece(diceValue1);
                            spaceMap.get(getNestById(globalNestId).getPieceList()[ii].getCurrentPosition()).setOccupancy(true);
                            spaceMap.get(getNestById(globalNestId).getPieceList()[ii].getCurrentPosition()).setPiece(getNestById(globalNestId).getPieceList()[ii]);
                            diceTurn = 0;
                            check++;
                            break;
                        }
                    }
                    else {
                        getNestById(globalNestId).getPieceList()[ii].movePiece(diceValue1);
                        spaceMap.get(getNestById(globalNestId).getPieceList()[ii].getCurrentPosition()).setOccupancy(true);
                        spaceMap.get(getNestById(globalNestId).getPieceList()[ii].getCurrentPosition()).setPiece(getNestById(globalNestId).getPieceList()[ii]);
                        diceTurn = 0;
                        check++;
                        break;
                    }
                }
            }
        }
        if (check == 0) {
            while (diceTurn < 2) {
                if (diceTurn == 0) { // turn 1
                    playerMoveAmount = diceValue1;
                    diceTurn = 1;
                } else if (diceTurn == 1) { // turn 2
                    playerMoveAmount = diceValue2;
                    diceTurn = 2; //reset
                }
                for (int i = 47; i >= 0; i--) {
                    if (spaceMap.get(i).getOccupancy()) {
                        if (spaceMap.get(i).getPiece().getNestId() == globalNestId) {
                            Piece piece = spaceMap.get(i).getPiece();
                            int initialPosition = piece.getCurrentPosition();
                            if (piece.getStep() <= 47) {
                                // case 1: piece being blocked and
                                if (!piece.ableToMove(playerMoveAmount, diceTurn) && !piece.ableToKick(playerMoveAmount)
                                        && piece.getCurrentPosition() != -1 && diceTurn == 1) {
                                    if (piece.ableToMove(diceValue2, diceTurn) || piece.ableToKick(diceValue2)) {
                                        playerMoveAmount = diceValue2;
                                        diceValue2 = diceValue1;
                                        diceValue1 = playerMoveAmount;
                                    }
                                }
                                //case 2: not blocked or able to kick
                                if (!piece.isBlockedPiece(playerMoveAmount) || piece.ableToKick(playerMoveAmount)) {
                                    // case 2.1: check for when able to kick
                                    if (piece.ableToKick(playerMoveAmount)) {
                                        int next = 0;
                                        if (piece.getCurrentPosition() != -1 && piece.getStep() + playerMoveAmount <= 48) {
                                            next = piece.getCurrentPosition() + playerMoveAmount;
                                            if (next > 47) {
                                                next -= 48;
                                            }
                                        }
                                        Piece kickedPiece = spaceMap.get(next).getPiece();
                                        piece.kick(kickedPiece);
                                        players[globalNestId].setPoints(players[globalNestId].getPoints() + 2);
                                        players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);
                                        spaceMap.get(next).setPiece(null);
                                        spaceMap.get(next).setOccupancy(false);
                                    }
                                    //case 2.2: able to move
                                    if ((piece.getCurrentPosition() != -1 || ((diceValue1 == 6 || diceValue2 == 6) && diceTurn == 0))) {
                                        if (piece.getCurrentPosition() == -1) {
                                            diceTurn = 2;
                                        }

                                        if (piece.getStep() + playerMoveAmount <= 48) {
                                            players[globalNestId].setPoints(players[globalNestId].getPoints() + piece.movePiece(playerMoveAmount));
                                            i = -1;
                                            if (initialPosition != -1) {
                                                spaceMap.get(initialPosition).setOccupancy(false);
                                                spaceMap.get(initialPosition).setPiece(null);
                                            }
                                            spaceMap.get(piece.getCurrentPosition()).setOccupancy(true);
                                            spaceMap.get(piece.getCurrentPosition()).setPiece(piece);
                                        }
                                        if (piece.getCurrentPosition() == initialPosition) {
                                            diceTurn--;
                                        }
                                    }
                                }
                                //case 3: piece is blocked on board
                                else if ((piece.isBlockedPiece(playerMoveAmount) && piece.getCurrentPosition() != -1)) {
                                    diceTurn--; //reset turn
                                }
                            } else if ((!piece.blockHome(playerMoveAmount) || (!piece.blockHome(diceValue2) && diceTurn == 1 && diceValue1 < diceValue2)) && piece.getStep() >= 48 && piece.getStep() < 48 + 6) {
                                if (!piece.blockHome(diceValue2) && diceValue1 < diceValue2 && diceTurn == 1) {
                                    playerMoveAmount = diceValue2;
                                    diceValue2 = diceValue1;
                                    diceValue1 = playerMoveAmount;
                                }
                                players[globalNestId].setPoints(players[globalNestId].getPoints() + piece.movePiece(playerMoveAmount));
                                i = -1;
                                if (initialPosition >= 48) {
                                    houseMap.get(initialPosition).setOccupancy(false);
                                    houseMap.get(initialPosition).setPiece(null);
                                } else if (initialPosition == piece.getStartPosition(globalNestId) - 1) {
                                    spaceMap.get(initialPosition).setOccupancy(false);
                                    spaceMap.get(initialPosition).setPiece(null);
                                }
                                houseMap.get(piece.getCurrentPosition()).setOccupancy(true);
                                houseMap.get(piece.getCurrentPosition()).setPiece(piece);
                            }
                            if (piece.getCurrentPosition() == initialPosition && piece.getStep() >= 48) {
                                diceTurn--;
                            }
                            //case 4:
                            if (piece.getCurrentPosition() != -1 && !piece.ableToMove(diceValue2, diceTurn)
                                    && !piece.ableToKick(diceValue2, globalNestId) && diceTurn == 1 && !piece.ableToMoveInHome(diceValue2)) {
                                diceTurn = 3;
                            }
                            //reset player and dice turns
                        }
                    }
                }
            }
            turn = 0;
            diceTurn = 0;
        }
    }


}
