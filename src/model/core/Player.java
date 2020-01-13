package model.core;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import helper.StaticContainer.*;


public class Player extends Node {
    // Player attributes
    private String name;
    private int nestId;
    private ConnectionStatus connectionStatus;
    private int points;
    private boolean rolled;
    private Label scoreLabel; //
    private int pointForTurn = 0;
    private int getToHouse ;

    public Player (int nestId, String name) {
        this.nestId = nestId;
        this.name = name;
        points = 0;
    }

    public void setPointForTurn(int point){ pointForTurn = point;}
    public int getPointForTurn(){return pointForTurn;}
    // Player name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNestId() {
        return nestId;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public ConnectionStatus getConnectionStatus() {
        return this.connectionStatus;
    }

    public void rolled(){rolled = true;}

    public boolean isRolled(){ return rolled;}

    public void resetCheck(){rolled = false;}


    public void setPoints (int points) {
        this.points = points;
    }


    public int getPoints() {
        return this.points;
    }

    public Boolean isGetToHouse() {
        return getToHouse == 4;
    }

    public void increaseGetToHouse() {
        this.getToHouse++;
    }

    public void decreaseGetToHouse(){
        this.getToHouse--;
    }
}

