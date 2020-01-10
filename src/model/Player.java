package model;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import statics.StaticContainer.*;


public class Player extends Node {
    // Player attributes
    private String name;
    private int nestId;
    private ConnectionStatus connectionStatus;
    private int points;
    private boolean rolled;
    @FXML private Label pointLabel;
    @FXML private Label nameLabel;

    public Player (int nestId, String name) {
        this.nestId = nestId;
        this.name = name;
        this.nameLabel.setText(this.name);
        points = 0;
        this.pointLabel.setText(String.valueOf(this.points));
    }


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
        this.points += points;
        this.pointLabel.setText(String.valueOf(this.points));
    }


    public int getPoints() {
        return this.points;
    }
}

