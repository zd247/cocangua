package model;

import javafx.scene.paint.Color;

public class Player {
    private String name;
    private int nestId;
    private Color color;
    private int point = 0;
    private Dice[] dice = new Dice[2];
    private boolean rolled;         // Check for the roll action

    Player(){
    }
    public Player(String name){
        this.name = name;           // set name
        this.color = getNestById(nestId).getColor(); // set players color
    }
    private static Nest getNestById(int id) {           // get the nest by id
        return new Nest(id);
    }
    void setNestId(int id){
        nestId = id;
    }
    int getNestId(){
        return nestId;
    }

    void set_name(String name){
        this.name = name;
    }
    String getName(){
        return name;
    }


    void storePoints(int point){
        this.point = point;
    }
    int getPoint(){
        return point;
    }

    int movePosition(Piece piece){          // return the next position the piece has to travel
        int destination;
        destination = piece.getCurrentPosition() + dice[0].getFace() + dice[1].getFace();
        if (destination > 47){              // keep moving without crashing, there are only 47 steps, so the step which is next to 47 is 0
            destination = destination - 47;
        }
        return destination;
    }
    void resetPoint () {this.point = 0;}        // reset the point

    void roll(){                //roll
        dice[0].roll();
        dice[1].roll();
        rolled = true;          // this player has rolled
    }
    boolean checkRoll(){
        return rolled;
    }

    void stop(){
        ;
    }

    Piece pick(int id){                 // Pick the piece with its id in the nest
        if (rolled) {
            Nest nest = getNestById(nestId);
            rolled = false;             // whenever there is a pick, the checker will reset to continuously check the next turn
            return nest.getPieces().get(id);
        }
        return null;                    // If the player does not roll yet, there is no piece is picked
    }

    void deploy(Piece piece){           // Get out the nest
        if (color == Color.BLUE){
            piece.setCurrentPosition(1);
        }
        else if(color == Color.YELLOW) {
            piece.setCurrentPosition(13);
        }
        else if(color == Color.GREEN) {
            piece.setCurrentPosition(25);
        }
        else if(color == Color.RED) {
            piece.setCurrentPosition(37);
        }
    }
}

