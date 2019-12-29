package sample.model.piece;

import javafx.scene.paint.Color;

public class Piece {
    private String nestId;
    private boolean isDeployed;
    private boolean isBlocked;
    private boolean isHome;
    private Color color;
    private Position currentPosition;
    private static final Position[] special ={new Position(6,0),new Position(6,6),new Position(0,6),
    new Position(0,9), new Position(6,9) , new Position(6,15),new Position(9,15), new Position(9, 9),
    new Position(15,9)};
//=================================[]=====================================


    public Piece(String nestId, Color color) {
        this.nestId = nestId;
        this.color = color;
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

    //=================================[]=====================================

    private void setColor(String chooseColor) {
        //While we set color, we are setting the start of each piece
        switch (chooseColor.toLowerCase()){
            case "red":{
                color = Color.RED;
                currentPosition.setPosition(0,0);
            }
            case "blue":{
                color = Color.BLUE;
                currentPosition.setPosition(0,0);
            }
            case "green":{
                color = Color.GREEN;
                currentPosition.setPosition(0,0);
            }
            case "yellow":{
                color = Color.YELLOW;
                currentPosition.setPosition(0,0);
            }
        }
    }

    public  boolean checkForBlock() {return false;}
    //get status for blocking

    public void move(int moveAmount) {
        //For the yellow first
        //Set the translate X and translate Y in the controller
        boolean switchX = true;
        boolean switchY = false;
        int temp;
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        if (switchX && !switchY){
            //X moves, Y stays still
            if (currentPosition.ToSwitch(moveAmount,x,0)){
                //if it does not exceed boundary
                switchX = false;
                switchY = true;
            }
        }
        else{
            if (currentPosition.ToSwitch(moveAmount,0,y)){
                switchX = true;
                switchY = false;
            }
        }
    }

    public void kick (Piece piece) {

    }







}
