package statics;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Player;

import java.util.HashMap;

public class StaticContainer { // can be made singleton but not necessary
    public static HashMap<Integer, Player> players = new HashMap<>(); // check for null when starting the game

    /**
     *
     * @param id
     * @return
     */
    public static Player createPlayer(int id) {
       Player player = new Player(id, "Player" + id, true);


       return player;
    }

    /**
     *
     * @param id
     * @return
     */
    public static Player createBot(int id) {
        Player player = new Player(id, "Player" + id, false);


        return player;
    }

//    /**
//     *
//     */
//    public static Color getColorByNestId(int nestId) {
//        Color color = n
//
//        return color;
//    }






}
