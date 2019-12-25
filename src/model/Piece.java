package model;

public class Piece {
    private String nestId;
    private int currentPosition;
    private boolean isDeployed;
    private boolean isBlocked;
    private boolean isHome;

    //=================================[]=====================================


    public Piece(String nestId, int currentPosition, boolean isDeployed, boolean isBlocked, boolean isHome) {
        this.nestId = nestId;
        this.currentPosition = currentPosition;
        this.isDeployed = isDeployed;
        this.isBlocked = isBlocked;
        this.isHome = isHome;
    }

    public String getNestId() {
        return nestId;
    }

    public void setNestId(String nestId) {
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

    //=================================[]=====================================

    public void setColor () {

    }

    public  boolean checkForBlock() {
        return false;
    }

    public void move(int moveAmount) {

    }

    public void kick (Piece piece) {

    }







}
