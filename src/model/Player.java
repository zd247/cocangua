package model;

import javafx.scene.paint.Color;

// Player class
public class Player {
    // Player attributes
    private String name;
    private int nestId;
    private Color color;
    private int point = 0;
    private boolean rolled; // Check if the player has played their turn
    private Dice[] dices = {new Dice(), new Dice()};    // Create 2 new dices for each player

    // Constructor based on given name
    public Player(String name) {
        this.name = name;
        this.color = getNestById(nestId).getColor(); // Set player's color
    }

    // Player name getter and setter
    public String getName(){
        return name;
    }
    void setName(String name){
        this.name = name;
    }

    // Getter for array of Dice
    public Dice[] getDices() {
        return dices;
    }

    // Nest getter and setter
    private static Nest getNestById(int id) {           // get the nest by id
        return new Nest(id);
    }   // Create new nest by id
    public void setNestId(int id){
        nestId = id;
    }
    int getNestId(){
        return nestId;
    }

    // Points getter and setter
    int getPoints(){
        return point;
    }
    void storePoints(int point){
        this.point = point;
    }

    // Reset player's point
    void resetPoints() { this.point = 0; }

    // Return the next position the piece has to travel to
    public int getDestination(Piece piece){
        int destination;
        destination = piece.getCurrentPosition() + dices[0].getFace() + dices[1].getFace();

        // There are 48 only spaces (0 - 47) to travel to
        if (destination > 47)
            destination = destination - 47; // In case destination index goes overboard, reset it (pass Blue arrival)
        return destination;
    }

    // Roll both dices
    public void roll() {
        dices[0].roll();
        dices[1].roll();
        rolled = true;          // This player has rolled
    }

    // Check if player has rolled
    public boolean isRolled() { return rolled; }

    void stop() {
    }

    // Whenever there is a pick, the checker will reset to continuously check the next turn
    public void resetCheck() {
        rolled = false;
    }

    // Pick a Piece based on its id int Nest
    Piece pick(int id){
        if (rolled) {
            Nest nest = getNestById(nestId);
            // Whenever there is a pick, the checker will reset to continuously check the next turn
            rolled = false;
            return nest.getPieces().get(id);
        }
        // If the player hasn't rolled yet, no piece is picked
        return null;
    }

    // Get pieces out out the Nest
    void deploy(Piece piece){
        if (color == Color.BLUE){
            piece.setCurrentPosition(Map.BLUE_START);
        }
        else if(color == Color.YELLOW) {
            piece.setCurrentPosition(Map.YELLOW_START);
        }
        else if(color == Color.GREEN) {
            piece.setCurrentPosition(Map.GREEN_START);
        }
        else if(color == Color.RED) {
            piece.setCurrentPosition(Map.RED_START);
        }
    }
}

