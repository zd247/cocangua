package model;

import javafx.scene.paint.Color;

public class Player {
    // Player attributes
    private String name;
    private int nestId;
    private int point = 0;


    private boolean rolled; // Check if the player has played their turn
    private Dice[] dices = {new Dice(), new Dice()};    // Create 2 new dices for each player

    public Player(String name, int nestId) {
        this.name = name;
        this.nestId = nestId;
    }

    // Player name getter and setter
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }


}

