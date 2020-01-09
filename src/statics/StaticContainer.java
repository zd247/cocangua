package statics;

import javafx.scene.paint.Color;
import model.*;


import java.util.HashMap;

import static javafx.scene.paint.Color.BLACK;
import static model.Map.*;

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


    //TURN LOGIC STATICS
    public static int turn = 0;
    public static int id = -1;
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
    public static void rollDice(Dice dice1, Dice dice2) {
        dice1.setOnMouseClicked(event -> {
            if (turn == 0) {
                turn = 1;
                if (id >= (players.length - 1)) {
                    id = 0;
                } else {
                    id++;
                }

                //check for default case
                if (id != -1 && players[id].getConnectionStatus() == ConnectionStatus.OFF){
                    while (!(players[id].getConnectionStatus() == ConnectionStatus.PLAYER)) {
                        id++;
                        if (id > (players.length - 1)) {
                            id = 0;
                        }
                    }
                }

                //start rolling
                diceValue1 = dice1.roll();
                diceValue2 = dice2.roll();
                System.out.println("Player" + id  + " " + diceValue1);
                System.out.println("Player" + id  + " " + diceValue2);

                //draw indicator
                for (int i = 0; i < players.length; i++) {
                    if (i != id) {
                        spaceMap.get(i).rect.setStrokeWidth(0);
                        players[i].resetCheck();
                    } else {
                        spaceMap.get(i).rect.setStroke(BLACK);
                        spaceMap.get(i).rect.setStrokeWidth(10);
                        players[i].rolled();
                    }
                }

                //check when clean board, reset turn counter
                if (allAtHome(id) && diceValue1 != 6 && diceValue2 != 6) {
                    players[id].resetCheck();
                    if (diceValue1 == diceValue2) {
                        id--;
                    }
                    turn = 0;
                }

                //brain-fuckery logic starts here

//                else if (!currentPiece.ableToMove(diceValue1)
//                        && !currentPiece.ableToMove(diceValue2)
//                        && !canDeploy(id,moveAmount1,moveAmount2)
//                        && !able_To_Kick(moveAmount1,id)
//                        &&!able_To_Kick(moveAmount2,id)){
//                    players[id].resetCheck();
//                    if (moveAmount1 == moveAmount2) {
//                        id--;
//                    }
//                    turn =0;
//                }
//                System.out.println(!able_To_Move(id, moveAmount1));
//                System.out.println(!able_To_Move(id,moveAmount2));
//                System.out.println(!canDeploy(id));
//                System.out.println(!able_To_Kick(moveAmount1,id));
//                System.out.println(!able_To_Kick(moveAmount2,id));
//                System.out.println("--------------");
            }
        });
    }

    /**
     *
     * @param nestId
     * @return
     */
    static boolean allAtHome(int nestId){
        for (int i = 0; i <=  47; i++){
            if( spaceMap.get(i).getOccupancy())
            {
                if (spaceMap.get(i).getPiece().getNestId() == nestId){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canDeploy(int nestID){
        if(diceValue1 == 6 || diceValue2 == 6) {
            for (int i = 0; i < 4; i ++){
                if (nestMap.get(nestID).getPieceList()[i].getCurrentPosition() == -1){
                    return true;
                }
            }
        }
        return false;
    }




//    public static ConnectionStatus getPlayerConnectionStatusById() {
//
//    }






}
