package statics;

import model.Player;

import java.util.HashMap;

public class StaticContainer { // singleton
    public static HashMap<Integer, Player> players; // check for null when starting the game

    private Player createPlayer() {
        Player ret = new Player();


        return ret;
    }

}
