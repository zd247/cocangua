package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Nest {
    private String id;
    private Color color;
    private ArrayList<Piece> pieces;
    //getter
    ArrayList<Piece> getPieces() {
        return pieces;
    }

    Color getColor() {
        return color;
    }

    String getNestById() {
        return id;
    }

    //Setter
    void setId(String id) {
        this.id = id;
    }

    void setColor(String color) {
        switch (color.toLowerCase()){
            case "red":{
                this.color = Color.RED;
            }
            case "blue":{
                this.color = Color.BLUE;
            }
            case "green":{
                this.color = Color.GREEN;
            }
            case "yellow":{
                this.color = Color.YELLOW;
            }
        }
    }

    //Method
    void addPiece(Piece piece) {
        pieces.add(piece);
    }

    void removePiece (Piece piece) {
        pieces.remove(piece);
    }

    void display(Pane pane) {
        pane.toFront();
    }
    void displayPieces() {

    }
}
