package helper;

import controller.GameController;
import controller.MenuController;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.core.*;


import java.util.HashMap;

import static javafx.scene.paint.Color.BLACK;


public class StaticContainer {
    public static Player[] players = new Player[4];  // player container for client

    // A map to store all circle positions
    public static HashMap<Integer, Space> spaceMap;

    // A map to store all nests with colors as their key
    public static HashMap<Integer, Nest> nestMap;

    // Maps to store all houses
    public static HashMap<Integer, House> houseMap ;

    public static Piece currentPiece = null;

    public static GameController gameController ;

    public static int POLLING_INTERVAL = 1000; //miliseconds


    public static MenuController menuController;

    public static Language language;

    public static ObservableList<String> availableChoices = FXCollections.observableArrayList( "English","Tiếng Việt");

    public static SequentialTransition seq;

    public static Dice dice1;

    public static Dice dice2;

    public static Stage alertBox;

    public static AnimationTimer timer;

    public static Stage window;

    public static int numberOfPlayer;

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
     * Dice get on mouse click handler
     */
    public static void diceWork(){
        if (turn == 0) {                // If the current turn is done
            turn = 1;
            if (globalNestId >= (players.length - 1)) {     // Move to the next player's turn
                globalNestId = 0;
            } else {
                globalNestId++;
            }

            //check for default case
            if (globalNestId != -1 && players[globalNestId].getConnectionStatus() == ConnectionStatus.OFF){         // Move turn until there is not the offline player
                while ((players[globalNestId].getConnectionStatus() == ConnectionStatus.OFF)) {
                    globalNestId++;
                    if (globalNestId > (players.length - 1)) {
                        globalNestId = 0;
                    }
                }
            }

            //start rolling
            diceValue1 = dice1.roll();                      // roll dice 1 and get its value
            diceValue2 = dice2.roll();                      // roll dice 2 and get its value
            System.out.println("Player" + globalNestId + " " + diceValue1);
            System.out.println("Player" + globalNestId + " " + diceValue2);

            //draw indicator
            for (int i = 0; i < players.length; i++) {          // Setting the nest highlight for indicating its turn
                if (i != globalNestId) {
                    players[i].resetCheck();
                    nestMap.get(i).rect.setStrokeWidth(0);
                } else {
                    players[i].rolled();
                    nestMap.get(i).rect.setStroke(BLACK);
                    nestMap.get(i).rect.setStrokeWidth(10);
                }
            }
            //check when clean board, reset turn counter
            if (allAtHome(globalNestId) && diceValue1 != 6 && diceValue2 != 6) {        // If it is impossible to move, skip this turn
                nestMap.get(globalNestId).rect.setStrokeWidth(0);
                if (diceValue1 == diceValue2) {
                    globalNestId--;
                }
                int nextTurn = globalNestId + 1;
                if (nextTurn == 4){
                    nextTurn = 0;
                }
                while (players[nextTurn].getConnectionStatus() == ConnectionStatus.OFF){
                    nextTurn++;
                    if (nextTurn == 4){
                        nextTurn = 0;
                    }
                }
                // IS TURN, WAITING FOR INPUT
                nestMap.get(nextTurn).rect.setStroke(Color.SILVER);
                nestMap.get(nextTurn).rect.setStrokeWidth(10);
                turn = 0;
            }

            else {
                if (!nestMap.get(globalNestId).getPieceList()[0].ableToMove(diceValue1,diceTurn)            // If it is impossible to move, skip this turn
                        && !nestMap.get(globalNestId).getPieceList()[0].ableToMove(diceValue2,diceTurn)
                        && !canDeploy(globalNestId)
                        && !ableToKick(diceValue1,globalNestId)
                        && !ableToKick(diceValue2,globalNestId)
                        && !pieceInHouseCanMove(globalNestId,diceValue1)
                        && !pieceInHouseCanMove(globalNestId,diceValue2) ) {
                    players[globalNestId].resetCheck();
                    nestMap.get(globalNestId).rect.setStrokeWidth(0);
                    if (diceValue1 == diceValue2) {
                        globalNestId--;
                    }
                    int nextTurn = globalNestId + 1;
                    if (nextTurn == 4){
                        nextTurn = 0;
                    }
                    while (players[nextTurn].getConnectionStatus() == ConnectionStatus.OFF){
                        nextTurn++;
                        if (nextTurn == 4){
                            nextTurn = 0;
                        }
                    }
                    nestMap.get(nextTurn).rect.setStroke(Color.SILVER);
                    nestMap.get(nextTurn).rect.setStrokeWidth(10);
                    turn = 0;
                }

                System.out.println("--------------");
            }
            if (globalNestId != -1 && players[globalNestId].getConnectionStatus() == ConnectionStatus.BOT && turn == 1){            // If there is a bot's turn
                players[globalNestId].resetCheck();
                botPlay();                              // Bot will play by itself
                turn = 0;
            }
            Timeline timeline = new Timeline();
            int nextTurn = globalNestId + 1;
            if (nextTurn == 4){
                nextTurn = 0;
            }
            while (players[nextTurn].getConnectionStatus() == ConnectionStatus.OFF){
                nextTurn++;
                if (nextTurn == 4){
                    nextTurn = 0;
                }
            }
            if (players[nextTurn].getConnectionStatus() == ConnectionStatus.BOT && turn == 0) {         //Whenever the next turn is a bot, it will auto roll.
                dice1.setDisable(true);
                dice2.setDisable(true);
                KeyFrame key = new KeyFrame(Duration.millis(200 * (diceValue1 + diceValue2) + 400), new EventHandler<ActionEvent>() {
                    //100 for translate time, 100 for pause and 400 for waiting between the dice
                    @Override
                    public void handle(ActionEvent event) {
                        diceWork();                     // roll dice automatically after a fixed duration
                    }
                });
                timeline.getKeyFrames().add(key);
                timeline.play();
            }
            else if (players[nextTurn].getConnectionStatus() == ConnectionStatus.PLAYER){
                dice1.setDisable(false);
                dice2.setDisable(false);
            }
        }
    }

    /**
     * set dice on click for rolling
     */
    public static void setDiceOnClick() {
        dice2.setOnMouseClicked(event->{
            diceWork();
        });
        dice1.setOnMouseClicked(event -> {
            diceWork();
        });
    }

    /**
     *If all of the pieces is at home
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


    /**
     * Check whether it is possible to deploy or not
     * @param nestID
     * @return
     */
    public static boolean canDeploy(int nestID){
        if(diceValue1 == 6 || diceValue2 == 6) {
            for (int i = 0; i < 4; i ++){
                if (nestMap.get(nestID).getPieceList()[i].getCurrentPosition() == -1  && (!nestMap.get(nestID).getPieceList()[i].isBlockedPiece(1) ||nestMap.get(nestID).getPieceList()[i].ableToKick(1))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether there is a piece can use its dice for kick s.o or not
     * @param moveAmount
     * @param nestId
     * @return
     */
    static boolean ableToKick(int moveAmount, int nestId){
        Piece piece;
        for (int i =0; i< 4; i++) {
            if(getNestById(nestId).getPieceList()[i].getCurrentPosition() != -1 && getNestById(nestId).getPieceList()[i].getStep() + moveAmount <= 48){
                piece =getNestById(nestId).getPieceList()[i];
                if (piece.ableToKick(moveAmount)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the piece in house is able to move without blocking
     * @param nestId
     * @param diceValue
     * @return
     */
    static boolean pieceInHouseCanMove(int nestId, int diceValue){
        Piece piece;
        for (int i = 0; i < 4;i++){
            if (getNestById(nestId).getPieceList()[i].getStep() >= 48 && (getNestById(nestId).getPieceList()[i].getCurrentPosition()-getNestById(nestId).getPieceList()[i].getHouseArrival() + 2) == diceValue){
                piece = getNestById(nestId).getPieceList()[i];
                if (!piece.blockHome(diceValue)){
                    return true;
                }
            }
        }
        return false;
    }


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
        language = new Language("en", "US");
        //Referene from the game controller
        menuController = c;
        setChoiceBox(c.languageBox);
    }

    private static void setChoiceBox(ChoiceBox<String> choiceBox){
        //To set settings for the choice box
        choiceBox.setItems(availableChoices);
        if (language.getLocale().contains("en"))
            choiceBox.setValue("English");//Set face of the choice box
        else{
            choiceBox.setValue("Tiếng Việt");
        }

    }


    /**
     * Bot play function
     */
    private static void botPlay() {
        seq = new SequentialTransition();
        do {                                    // Play until 2 dices are used or there is no way to move
            int move = diceTurn;
            for (int i = 0; i < 4; i++) {
                if (getNestById(globalNestId).getPieceList()[i].getStep()< 54) {
                    botLogic(getNestById(globalNestId).getPieceList()[i]);
                    if (move != diceTurn) {
                        break;
                    }
                }
            }
        }while (diceTurn < 2);

        //Reset all the things
        nestMap.get(globalNestId).rect.setStrokeWidth(0);
        players[globalNestId].resetCheck();
        if (diceValue1 == diceValue2) globalNestId--;
        int nextTurn = globalNestId + 1;
        if (nextTurn == 4){
            nextTurn = 0;
        }
        while (players[nextTurn].getConnectionStatus() == ConnectionStatus.OFF){
            nextTurn++;
            if (nextTurn == 4){
                nextTurn = 0;
            }
        }
        nestMap.get(nextTurn).rect.setStroke(Color.SILVER);
        nestMap.get(nextTurn).rect.setStrokeWidth(10);
        diceTurn = 0;
        seq.play();
    }

    /**
     * Bot logic on moving, behaviour, it is applied almost the condition with the "player" when clicked on piece, modify a bit to make it smarter
     * @param piece
     */
    public static void botLogic(Piece piece) {
        int initialPosition = piece.getCurrentPosition();
            if (diceTurn == 0) { // turn 1
                playerMoveAmount = diceValue1;
                if (piece.getCurrentPosition() != -1)
                    diceTurn = 1;
            } else if (diceTurn == 1) { // turn 2
                playerMoveAmount = diceValue2;
                if (piece.getCurrentPosition() != -1) {
                    diceTurn = 2; //reset
                }
            }
            if (piece.getStep() <= 47) {
                // case 1: piece being blocked and
                if (!piece.ableToMove(playerMoveAmount,diceTurn) && !piece.ableToKick(playerMoveAmount)
                        && piece.getCurrentPosition() != -1 && diceTurn == 1) {
                    if (piece.ableToMove(diceValue2,diceTurn) || piece.ableToKick(diceValue2)) {
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
                            Piece kickedPiece = spaceMap.get(next).getPiece();
                            piece.kick(kickedPiece);
                            players[globalNestId].setPoints(players[globalNestId].getPoints() + 2);
                            players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);
                            spaceMap.get(next).setPiece(null);
                            spaceMap.get(next).setOccupancy(false);
                        } else if (piece.getCurrentPosition() == -1 && (diceValue1 == 6 || diceValue2 == 6)){
                            next = piece.getStartPosition(globalNestId);   //get the piece at start position
                            Piece kickedPiece = spaceMap.get(next).getPiece();
                            piece.kick(kickedPiece);
                            players[globalNestId].setPoints(players[globalNestId].getPoints() + 2);
                            players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);
                            spaceMap.get(next).setPiece(null);
                            spaceMap.get(next).setOccupancy(false);
                        }
                    }
                    //case 2.2: able to move
                    if ((piece.getCurrentPosition() != -1 || ((playerMoveAmount == 6 || diceValue2 == 6) && diceTurn == 0) || (playerMoveAmount == 6 && diceTurn == 1) )) {
                        if (piece.getCurrentPosition() == -1) {
                            if (playerMoveAmount != 6 && diceTurn == 0){
                                playerMoveAmount = diceValue2;
                                diceValue2 = diceValue1;
                                diceValue1 = playerMoveAmount;
                            }
                            diceTurn ++;
                        }

                        if (piece.getStep() + playerMoveAmount <= 48) {
                            players[globalNestId].setPoints(players[globalNestId].getPoints() + piece.movePiece(playerMoveAmount));
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
            }
            else if ((!piece.blockHome(playerMoveAmount) || (!piece.blockHome(diceValue2) && diceTurn == 1 )) && piece.getStep() >= 48 && piece.getStep() < 48 + 6) {
                if (!piece.blockHome(diceValue2) && (diceValue1 < diceValue2 || piece.blockHome(diceValue1)) && diceTurn == 1) {
                    playerMoveAmount = diceValue2;
                    diceValue2 = diceValue1;
                    diceValue1 = playerMoveAmount;
                }
                players[globalNestId].setPoints(players[globalNestId].getPoints() + piece.movePiece(playerMoveAmount));
                if (initialPosition >= 48) {
                    houseMap.get(initialPosition).setOccupancy(false);
                    houseMap.get(initialPosition).setPiece(null);
                }
                else if (initialPosition == piece.getStartPosition(globalNestId) - 1){
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
            if (piece.getCurrentPosition() != -1 && !piece.ableToMove(diceValue2,diceTurn)
                    && !piece.ableToKick(diceValue2,globalNestId) && diceTurn == 1 && !piece.ableToMoveInHome(diceValue2) && !(diceValue2 == 6 && !piece.noPieceAtHome(globalNestId) && piece.ableToDeploy())) {
                diceTurn = 3;
            }
        }

}
