package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle {
    private String nestId;
    private boolean isDeployed;
    private boolean isBlocked;
    private boolean isHome;
    private Color color;
    private int currentPosition;
//=================================[]=====================================

    public Piece(String nestId, int currentPosition, Color color) {
        this.nestId = nestId;
        this.color = color;
        this.currentPosition = currentPosition;
        this.isDeployed = false;
        this.isBlocked = true;
        this.isHome = true;
    }

    public String getNestId() {
        return nestId;
    }

    public void setNestId(String nestId) {
        this.nestId = nestId;
    }

    public Color getColor() { return color; }

    public boolean isDeployed() {
        return isDeployed;
    }

    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        //To set equals to the index of space that it stands now
        //current position = index
        this.currentPosition = currentPosition;
    }

    //=================================[]=====================================

    private void setColor(String chooseColor) {
        //While we set color, we are setting the start of each piece
        switch (chooseColor.toLowerCase()){
            case "red":{
                color = Color.RED;
            }
            case "blue":{
                color = Color.BLUE;
            }
            case "green":{
                color = Color.GREEN;
            }
            case "yellow":{
                color = Color.YELLOW;
            }
        }
    }

    public void kick (Piece piece) {
        if (currentPosition == piece.getCurrentPosition()){
            piece.isHome = true;
        }
    }

    public void move(int moveAmount){
        //get the current position which is index of circle
        //after that + move amount to get the index of next space
        currentPosition += moveAmount;
    }
}
