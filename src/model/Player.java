package model;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;


public class Player extends Node {
    // Player attributes
    private String name;
    private int nestId;
    private int point;
    private boolean connectionStatus; // online/bot/offline


    public Player () { // can be used for interactive UI (needs getters and setters) : future implementation
        this.nestId = -1;

    }

    public Player (int nestId, String name, boolean connectionStatus) {

        this.nestId = nestId;
        this.connectionStatus = connectionStatus;

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

    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}

