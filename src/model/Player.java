package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private int point;
    private int nestId;
    private Dice dice = new Dice();
    private int dice1 = 0;
    private int dice2 = 0;
    private boolean[] connect = {false,false,false,false};
    private boolean rolled;
    private ArrayList<Nest> nest;
    Player(){
    }
    Player(int id, String name, int point, int nestId){
        this.id = id;
        this.name = name;
        if (!connect[id]) {
            connect[id] = true;
        }
        this.point = point;
        this.nestId = nestId;
    }

    void setNestId(int id){
        nestId = id;
    }
    int getNestId(){
        return nestId;
    }

    void set_id(int id){
        this.id = id;
    }
    int getId(){
        return id;
    }

    void set_name(String name){
        this.name = name;
    }

    String getName(){
        return name;
    }

    boolean setConnected(){
        if (connect[id]) {
            return false;
        }
        else {
            connect[id] = true;
            return true;
        }
    }

    boolean setDisconnected(){
        connect[id] = false;
        return true;
    }

    boolean getConnection(){
        return connect[id];
    }

    void setPoint(int point){
        this.point = point;
    }

    int getPoint(){
        return point;
    }

    void createNest(Color color){
        Nest newNest = new Nest(Integer.toString(nestId), color);
        nest.add(newNest);
    }

    void makeMove(int id){
        Piece piece = pick(id);
        int destination;
        destination = piece.getCurrentPosition() + dice1 + dice2;
        if (destination > 47){
            destination = destination - 47;
        }
        piece.setCurrentPosition(destination);
    }

    void roll(){
        dice1 = dice.roll();
        dice2 = dice.roll();
        isRolled(true);
    }
    void takeTurn(){
        isRolled(false);
    }
    void isRolled(boolean roll){
        this.rolled = roll;
    }
    boolean checkRoll(){
        return rolled;
    }
    int[] getDices(){
        return new int[]{dice1,dice2};
    }

    void stop(){
        setDisconnected();
    }

    Piece pick(int id) {
        if (rolled) {
            for (int i = 0; i < 4; i++) {
                if (nest.get(i).getNestById().equals(Integer.toString(nestId))) {

                    return nest.get(nestId).getPieces().get(id);
                }
            }
        }
        return null;
    }

    void deploy(int id){
        Piece piece = pick(id);
        for (int i = 0; i < 4; i++) {
            if (nest.get(i).getNestById().equals(Integer.toString(nestId))) {
                if (nest.get(i).getColor() == Color.BLUE){
                    piece.setCurrentPosition(1);
                }
                else if(nest.get(i).getColor() == Color.YELLOW) {
                    piece.setCurrentPosition(13);
                }
                else if(nest.get(i).getColor() == Color.GREEN) {
                    piece.setCurrentPosition(25);
                }
                else if(nest.get(i).getColor() == Color.RED) {
                    piece.setCurrentPosition(37);
                }
            }
        }
    }
}
