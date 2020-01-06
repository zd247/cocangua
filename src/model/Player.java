package model;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Player extends Pane implements Initializable {
    // Player attributes
    private String name;
    private int nestId;
    private int point;
    private boolean isConnected;
    private Button createButton;


    private boolean rolled; // Check if the player has played their turn
    private Dice[] dices = {new Dice(), new Dice()};    // Create 2 new dices for each player


    // Player name getter and setter
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

