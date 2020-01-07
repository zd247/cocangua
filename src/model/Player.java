package model;


import javafx.scene.Node;
import statics.StaticContainer.*;


public class Player extends Node {
    // Player attributes
    private String name;
    private int nestId;
    private ConnectionStatus connectionStatus;
    private int point;


    public Player () { // can be used for interactive UI (needs getters and setters) : future implementation
        this.nestId = -1;

    }

    public Player (int nestId, String name) {

        this.nestId = nestId;
        this.name = name;


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

}

