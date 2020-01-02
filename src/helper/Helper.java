package helper;

import model.*;

public class Helper {
    public static final int NUM_OF_DICES = 2;
    public static final int NUM_OF_PIECES = 4;


    public static Player[] createPlayers(int numOfPlayer) {
        Player[] rets = new Player[numOfPlayer];
        for (int i = 0; i < rets.length; i++) {
            //nest
            Nest nest = new Nest (i); // change to enum cycle of 4
            //pieces
            for (int j = 0; j < NUM_OF_PIECES;j++) {
                Piece piece = new Piece(i, -1, false, false, false);
                nest.addPiece(piece);
            }

            //player
            String name = "Player" + i;
            Player player = new Player(name);
            player.setNestId(nest.getNestId());
            //create 2 dices
            for (int k = 0; k < NUM_OF_DICES; k++) {
                Dice dice = new Dice();
                player.dices[k] = dice;
            }
        }
        return rets;
    }

    /**
     *
     * @param player
     */
    public static void updateDisplay(Player player) {
        //update nest

        //update piece positions on screen: curPos, status, updateMap() ??

        //update players score

        //end cycle
    }



    public static Nest getNestById(int id) {
        return new Nest(id);
    }

}
