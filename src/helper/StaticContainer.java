/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/

package helper;

import controller.MenuController;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.core.*;

import java.util.HashMap;

import static helper.LayoutContainer.*;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.GREENYELLOW;

public class StaticContainer {
    public static Player[] players = new Player[4];  // player container for client

    // A map to store all circle positions
    public static HashMap<Integer, Space> spaceMap;

    // A map to store all nests with colors as their key
    public static HashMap<Integer, Nest> nestMap;

    // Color indicators for turn
    public static Color nestWaitingForTurnColor = WHITE;
    public static Color nestMustRollColor = ORANGE;
    public static Color nestMustMoveColor = GREENYELLOW;

    // Maps to store all houses
    public static HashMap<Integer, House> houseMap ;

    public static MenuController menuController;

    public static ObservableList<String> availableChoices = FXCollections.observableArrayList( "English","Tiếng Việt");

    public static SequentialTransition seq;

    public static Dice dice1;

    public static Dice dice2;

    public static Stage alertBox;

    public static AnimationTimer timer;

    public static Stage window;

    public static int numberOfPlayer;

    public static Score score;

    public static String addIn;

    //TURN LOGIC STATICS
    public static int firstTurn = 0;
    public static int turn = 0;
    public static int globalNestId = -1;
    public static int diceTurn = 0;
    public static int playerMoveAmount = 0;
    public static int diceValue1 = 0;
    public static int diceValue2 = 0;
    public static boolean pieceIsMoving;

    public enum ConnectionStatus {
        PLAYER, BOT, OFF
    }


    //======================[Static Functions]==============================

    /**
     * Draw the nest indicator
     */
    static void drawIndicator() {

        for (int i = 0; i < players.length; i++) {          // Setting the nest highlight for indicating its turn
            if (i != globalNestId) {
                players[i].resetRolled();

                // MUST ROLL INDICATOR
                //nestMap.get(i).circle.setStroke(nestMustRollColor);

            } else {
                nestMap.get(i).circle.setStrokeWidth(10);
                players[i].rolled();

                // MUST MOVE INDICATOR
                nestMap.get(i).circle.setStroke(nestMustMoveColor);
            }
        }
    }

    /**
     * Dice get on mouse click handler, For rolling and getting the value of each dice, then handle the logic from there
     * After selecting and indicating the name, the player consequently roll and then find out who will get the first roll (first turn).
     */
    public static void diceWork(){
        pieceIsMoving = false;
        if (turn == 0) {                // If the current turn is done
            turn = 1;
            if (globalNestId >= (players.length - 1)) {     // Move to the next player's turn
                globalNestId = 0;
            } else {
                globalNestId++;
            }

            //check for default case
            if (globalNestId != -1 && players[globalNestId].getConnectionStatus() == StaticContainer.ConnectionStatus.OFF){         // Move turn until there is not the offline player
                while ((players[globalNestId].getConnectionStatus() == StaticContainer.ConnectionStatus.OFF)) {
                    globalNestId++;
                    if (globalNestId > (players.length - 1)) {
                        globalNestId = 0;
                    }
                }
            }

            //start rolling, main logic starts here
            diceValue1 = dice1.roll();                      // roll dice 1 and get its value
            diceValue2 = dice2.roll();                      // roll dice 2 and get its value
            drawIndicator();

            //check when clean board, reset turn counter
            if (allAtHome(globalNestId) && diceValue1 != 6 && diceValue2 != 6) {        // If it is impossible to move, skip this turn
                gameController.activityLog.setText(language.getStatusNextTurn());
                // WAITING FOR TURN (DEFAULT) INDICATOR
                nestMap.get(globalNestId).circle.setStroke(nestWaitingForTurnColor);

                if (diceValue1 == diceValue2) {
                    globalNestId--;
                }
                int nextTurn = globalNestId + 1;
                if (nextTurn == 4){
                    nextTurn = 0;
                }
                while (players[nextTurn].getConnectionStatus() == StaticContainer.ConnectionStatus.OFF){
                    nextTurn++;
                    if (nextTurn == 4){
                        nextTurn = 0;
                    }
                }
                // IS TURN, WAITING TO ROLL
                nestMap.get(nextTurn).circle.setStroke(nestMustRollColor);
                nestMap.get(nextTurn).circle.setStrokeWidth(10);
                turn = 0;
            }
            else {
                // If it is impossible to move, kick or deploy, skip this turn
                if (!nestMap.get(globalNestId).getPieceList()[0].ableToMove(diceValue1,diceTurn)
                        && !nestMap.get(globalNestId).getPieceList()[0].ableToMove(diceValue2,diceTurn)
                        && !canDeploy(globalNestId)
                        && !ableToKick(diceValue1,globalNestId)
                        && !ableToKick(diceValue2,globalNestId)
                        && !pieceInHouseCanMove(globalNestId,diceValue1)
                        && !pieceInHouseCanMove(globalNestId,diceValue2) ) {
                    players[globalNestId].resetRolled();                                                    // reset checker which means not allow that user to use their piece anymore
                    // WAITING FOR TURN (DEFAULT) INDICATOR
                    nestMap.get(globalNestId).circle.setStroke(nestWaitingForTurnColor);
                    if (diceValue1 == diceValue2) {                                                         // got 2 dices have the same value, can roll 1 more time
                        globalNestId--;
                    }
                    int nextTurn = globalNestId + 1;                                                        // Store the player's id of next turn
                    if (nextTurn == 4){
                        nextTurn = 0;
                    }
                    while (players[nextTurn].getConnectionStatus() == StaticContainer.ConnectionStatus.OFF){        // Until there is not a offline player
                        nextTurn++;
                        if (nextTurn == 4){
                            nextTurn = 0;
                        }
                    }

                    // MUST ROLL INDICATOR
                    nestMap.get(nextTurn).circle.setStroke(nestMustRollColor);
                    nestMap.get(nextTurn).circle.setStrokeWidth(10);
                    gameController.activityLog.setText("Next Turn!");
                    turn = 0;
                }

                System.out.println("--------------");
            }

            handleBotLogic();



        }
    }

    /**
     * For checking whether there is a bot turn or not, and set the auto rolling for it
     */
    static void handleBotLogic(){
        if (globalNestId != -1 && players[globalNestId].getConnectionStatus() == StaticContainer.ConnectionStatus.BOT && turn == 1){            // If there is a bot's turn
            players[globalNestId].resetRolled();
            botPlay();                              // Bot will play by itself
            turn = 0;
        }

        Timeline timeline = new Timeline();
        int nextTurn = globalNestId + 1;
        if (nextTurn == 4){
            nextTurn = 0;
        }

        while (players[nextTurn].getConnectionStatus() == StaticContainer.ConnectionStatus.OFF){
            nextTurn++;
            if (nextTurn == 4){
                nextTurn = 0;
            }
        }

        if (players[nextTurn].getConnectionStatus() == StaticContainer.ConnectionStatus.BOT && turn == 0) {         //Whenever the next turn is a bot, it will auto roll, and disable the dices handler
            dice1.setDisable(true);
            dice2.setDisable(true);
            if (pieceIsMoving){
                KeyFrame key = new KeyFrame(Duration.millis(200 * (diceValue1 + diceValue2) + 400), new EventHandler<ActionEvent>() {
                    //100 for translate time, 100 for pause and 400 for waiting between the dice
                    @Override
                    public void handle(ActionEvent event) {
                        diceWork();                     // roll dice automatically after a fixed duration
                    }
                });
                timeline.getKeyFrames().add(key);
            }
            else
            {
                KeyFrame key = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                    //100 for translate time, 100 for pause and 400 for waiting between the dice
                    @Override
                    public void handle(ActionEvent event) {
                        diceWork();                     // roll dice automatically after a fixed duration
                    }
                });
                timeline.getKeyFrames().add(key);
            }
            timeline.play();
        }
        else if (players[nextTurn].getConnectionStatus() == StaticContainer.ConnectionStatus.PLAYER){           // Otherwise, dices are allowed to click for rolling
            dice1.setDisable(false);
            dice2.setDisable(false);
        }
    }

    /**
     * set dice on click for rolling
     */
    public static void setDiceOnClick() {
        // only this to click on 1 dice, it will roll 2 dices. Then, the dices will be disabled until there is a new turn
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
     * Check whether it is possible to deploy or not by looking at 2 dices value
     * @param nestID
     * @return
     */
    private static boolean canDeploy(int nestID){
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
            if(getNestById(nestId).getPieceList()[i].getCurrentPosition() != -1 && getNestById(nestId).getPieceList()[i].getStep() + moveAmount <= 48){ // Cannot check through the house arrival
                piece = getNestById(nestId).getPieceList()[i];
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
            // Check for the dice value first, then check whether there is a piece in its destination or not
            if (getNestById(nestId).getPieceList()[i].getStep() >= 48 && (getNestById(nestId).getPieceList()[i].getCurrentPosition()-getNestById(nestId).getPieceList()[i].getHouseArrival() + 2) == diceValue){
                piece = getNestById(nestId).getPieceList()[i];
                if (!piece.blockHome(diceValue)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Bot play function, will be called whenever there is bot's turn
     */
    private static void botPlay() {
        seq = new SequentialTransition();
        do {                                    // Play until 2 dices are used or there is no way to move
            int move = diceTurn;
            for (int i = 0; i < 4; i++) {
                if (getNestById(globalNestId).getPieceList()[i].getStep()< 54) {
                    handleGameLogic(getNestById(globalNestId).getPieceList()[i]); // cycle through turns (globalNestId)
                    if (move != diceTurn) {         // If there is a piece which is move successfully
                        updatePoint(gameController);
                        break;
                    }
                }
            }
        } while (diceTurn < 2);

        //Reset all the things
        nestMap.get(globalNestId).circle.setStroke(nestWaitingForTurnColor);
        players[globalNestId].resetRolled();

        if (diceValue1 == diceValue2) globalNestId--;       // bonus 1 more turn if there is the same in dice's value
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
        // Must roll
        nestMap.get(nextTurn).circle.setStroke(nestMustRollColor);          // Set the warning color
        nestMap.get(nextTurn).circle.setStrokeWidth(10);
        diceTurn = 0;
        seq.play();
    }

    /**
     * Game behaviour, it is applied almost the condition with the "player" when clicked on piece, modify a bit to make it smarter
     * @param piece
     */
    public static void handleGameLogic(Piece piece) {
        int enemyId = 0;                                // Get enemyId, in case the piece is able to kick
        int kicked = 0;                                 // If it is able to kick
        int initialPosition = piece.getCurrentPosition();   // Store the initial position for reset the occupancy of that space
        if (diceTurn == 0) { // turn 1
            playerMoveAmount = diceValue1;              // Store the first dice value
            if (piece.getCurrentPosition() != -1)
                diceTurn = 1;
        } else if (diceTurn == 1) { // turn 2
            playerMoveAmount = diceValue2;              // Store the second dice value
            if (piece.getCurrentPosition() != -1) {
                diceTurn = 2;
            }
        }
        if (piece.getStep() <= 47) {                    // If the step is lower than 48 which means that the piece only able to move on space (not to house)
            // case 1: piece being blocked and not able to move with the first dice with first turn
            if (!piece.ableToMove(playerMoveAmount,diceTurn) && !piece.ableToKick(playerMoveAmount)
                    && piece.getCurrentPosition() != -1 && diceTurn == 1) {
                if (piece.ableToMove(diceValue2,diceTurn) || piece.ableToKick(diceValue2)) {        // If a piece can do one of above actions with dice 2, use dice 2 for moving
                    playerMoveAmount = diceValue2;                      // Store the value of dice 2
                    diceValue2 = diceValue1;                            // Swap value of dice 2 and dice 1, dice 1 will be used for next dice turn
                    diceValue1 = playerMoveAmount;
                }
            }
            //case 2: not blocked or able to kick
            if (!piece.isBlockedPiece(playerMoveAmount) || piece.ableToKick(playerMoveAmount)) {
                int next = 0;
                // case 2.1: check for when able to kick
                if (piece.ableToKick(playerMoveAmount)) {           // If there is able to kick case
                    if (piece.getCurrentPosition() != -1 && piece.getStep() + playerMoveAmount <= 48) { // If the piece is outsider (not in nest)
                        next = piece.getCurrentPosition() + playerMoveAmount;                       // store the destination
                        if (next > 47) {
                            next -= 48;
                        }
                        Piece kickedPiece = spaceMap.get(next).getPiece();                          // Get the enemy's piece
                        enemyId = spaceMap.get(next).getPiece().getNestId();                        // Store enemy's id for displaying status
                        piece.kick(kickedPiece);                                                    // Kick
                        players[globalNestId].setPoints(players[globalNestId].getPoints() + 2);     // Add point
                        players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);   // Lose point
                        spaceMap.get(next).setPiece(null);
                        spaceMap.get(next).setOccupancy(false);
                        kicked= 1;
                    } else if (piece.getCurrentPosition() == -1 && (diceValue1 == 6 || diceValue2 == 6)){       // If there is the piece in nest with the dice value
                        next = piece.getStartPosition(globalNestId);   //get the piece at start position
                        enemyId = spaceMap.get(next).getPiece().getNestId();
                        Piece kickedPiece = spaceMap.get(next).getPiece();
                        piece.kick(kickedPiece);
                        players[globalNestId].setPoints(players[globalNestId].getPoints() + 2);
                        players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);
                        spaceMap.get(next).setPiece(null);
                        spaceMap.get(next).setOccupancy(false);
                        kicked = 1;
                    }
                }
                //case 2.2: able to move or able to deploy
                if ((piece.getCurrentPosition() != -1 || ((playerMoveAmount == 6 || diceValue2 == 6) && diceTurn == 0) || (playerMoveAmount == 6 && diceTurn == 1) )) {
                    if (piece.getCurrentPosition() == -1) {     // For piece in nest
                        if (playerMoveAmount != 6 && diceTurn == 0){        // Use the dice has value of 6 ( no mater dice 1 or 2 )
                            playerMoveAmount = diceValue2;                  // If use dice 2, store the dice 2 value
                            diceValue2 = diceValue1;                        // Swap its value, dice 1 will be used for the next dice turn
                            diceValue1 = playerMoveAmount;
                        }
                        diceTurn ++;
                    }

                    if (piece.getStep() + playerMoveAmount <= 48) {         // If the piece move without passing its home arrival
                        players[globalNestId].setPoints(players[globalNestId].getPoints() + piece.movePiece(playerMoveAmount)); // It is allowed to move
                        // Update status for the user
                        if (kicked == 1){
                            updateStatus(players[globalNestId].getName() ,players[enemyId].getName(),0,1);
                        }
                        else{
                            if (initialPosition == -1){
                                updateStatus(players[globalNestId].getName() ,"",0,2);
                            }
                            else {
                                updateStatus(players[globalNestId].getName() ,"",playerMoveAmount,3);
                            }
                        }
                        // Set occupancy for both old and new position
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
            else if ((piece.isBlockedPiece(playerMoveAmount) && piece.getCurrentPosition() != -1)) {        // If this piece is not able to move, keep that dice turn and its value for another piece which is able to move
                diceTurn--; //reset turn
            }
        }
        else if ((!piece.blockHome(playerMoveAmount) ||     // If this piece is already at home arrival or inside its home, and there is no block
                (!piece.blockHome(diceValue2) && diceTurn == 1 )) &&
                piece.getStep() >= 48 && piece.getStep() < 48 + 6) {
            if (!piece.blockHome(diceValue2) && (diceValue1 < diceValue2 || piece.blockHome(diceValue1)) && diceTurn == 1) {        // Use the higher dice's value to move if it is available
                playerMoveAmount = diceValue2;                      // Store the dice 2's value
                diceValue2 = diceValue1;                            // swap dice 1 and dice 2
                diceValue1 = playerMoveAmount;
            }
            players[globalNestId].setPoints(players[globalNestId].getPoints() + piece.movePiece(playerMoveAmount));             // Move the piece
            // Set the occupancy
            if (initialPosition >= 48) {
                houseMap.get(initialPosition).setOccupancy(false);
                houseMap.get(initialPosition).setPiece(null);
                updateStatus(players[globalNestId].getName(),"",0,4);
            }
            else if (initialPosition == piece.getStartPosition(globalNestId) - 1){
                spaceMap.get(initialPosition).setOccupancy(false);
                spaceMap.get(initialPosition).setPiece(null);
                updateStatus(players[globalNestId].getName(),"",playerMoveAmount,5);
            }
            houseMap.get(piece.getCurrentPosition()).setOccupancy(true);
            houseMap.get(piece.getCurrentPosition()).setPiece(piece);
        }
        if (piece.getCurrentPosition() == initialPosition && piece.getStep() >= 48) {           // If it cannot move
            diceTurn--;
        }
        //case 4: after successfully using the first dice turn, auto check whether it is possible to use the next dice's value for moving or not
        if (piece.getCurrentPosition() != -1
                && !piece.ableToMove(diceValue2,diceTurn)
                && !piece.ableToKick(diceValue2,globalNestId) && diceTurn == 1
                && !piece.ableToMoveInHome(diceValue2)
                && !(diceValue2 == 6
                && !piece.noPieceAtHome(globalNestId)
                && piece.ableToDeploy())) {
            diceTurn = 3;
        }
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


}
