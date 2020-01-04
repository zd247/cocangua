package model;

import java.awt.*;

public class Piece {
    private int nestId;
    private int currentPosition;
    private boolean isDeployed;
    private boolean isBlocked;
    private boolean isHome;
    private boolean isFinished;
    private Color color;
    private int move;
//=================================[]=====================================


    public Piece(int nestId, int currentPosition) {
        this.nestId = nestId;
        this.currentPosition = currentPosition;
        this.isDeployed = false;
        this.isBlocked = true;
        this.isHome = true;
        this.isFinished = false;
    }

    public int getNestId() {
        return nestId;
    }

    public void setNestId(int nestId) {
        this.nestId = nestId;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

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

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    //=================================[]=====================================

    public void kick (Piece piece) {
        if (piece.getCurrentPosition() == this.currentPosition){
            piece.setHome(true);
        }
    }

}
