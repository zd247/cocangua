package sample.model;

import sample.model.piece.Piece;

/**
 *
 */
public class Player {
    private int id;
    private String name;
    private boolean connected;
    private int score;
    private int nestId;


    //=================================[]=====================================

    public Player(int id, String name, boolean connected, int score, int nestId) {
        this.id = id;
        this.name = name;
        this.connected = connected;
        this.score = score;
        this.nestId = nestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNestId() {
        return nestId;
    }

    public void setNestId(int nestId) {
        this.nestId = nestId;
    }


    //=================================[]=====================================

    /**
     * Register player
     * @return connectivity status
     */
    public boolean connect() {
        return true;
    }

    /**
     * @return
     */
    public boolean disconnect() {return true;}

    public void deploy(Piece piece) {

    }

    public void rollDice () {

    }



}
