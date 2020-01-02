package model;

import javafx.scene.paint.Color;

import static helper.Helper.*;

public class Player {

    private String name;
    private int nestId;
    private Color color;

    private int point = 0;

    public Dice[] dices = new Dice[2];
    private boolean rolled;


    public Player(String name){
        this.name = name;
        this.color = getNestById(nestId).getColor(); // set players color
    }

    public void setNestId(int id){
        nestId = id;
    }

    public int getNestId(){
        return nestId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }




    void setPoint(int point){
        this.point = point;
    }

    int getPoint(){
        return point;
    }

    void resetPoint () {this.point = 0;}

    /** REDO THIS
     *
     *
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

     */


}
