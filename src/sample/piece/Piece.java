package sample.piece;


public class Piece {
    /**Attributes*/

    private Position positions;

    private int id ;
    //Id can be updated by the view

    private enum Color {Red, Blue, Green, Yellow};
    //Get the color chosen by the user

    private boolean moveOut;
    //Check if the piece is outside the nest or not

    private boolean selected ;
    //Check if the piece is selected by the user

    private boolean kickBack;
    //When enemy's piece kick the piece , it will then go back to its nest

    /**Method*/
    public Piece(){
        /*start in his nest with id nest relating to the color*/
        selected = false;
        kickBack = false;
        moveOut = false;
    }

    public void move(int x, int y){
        positions.setPosition(x,y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setKickBack(boolean kickBack) {
        this.kickBack = kickBack;
    }

}
