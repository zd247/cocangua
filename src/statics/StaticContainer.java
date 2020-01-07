package statics;

import javafx.scene.paint.Color;
import model.Nest;
import model.Player;
import model.PlayerField;


import static model.Map.*;

public class StaticContainer { // can be made singleton but not necessary
    // containers
    public static Player[] players = new Player[4];
    public static PlayerField[] playerFields = new PlayerField[4];

    public enum ConnectionStatus {
        PLAYER, BOT, OFF
    }

    /**
     *
     * @param nestId
     * @return
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
        return getNestMap().get(nestId);
    }

//    public static ConnectionStatus getPlayerConnectionStatusById() {
//
//    }






}
