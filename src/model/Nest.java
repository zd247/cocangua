package model;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.WHITE;

public class Nest extends StackPane {
    private int id;
    private Piece[] pieceList = new Piece[4];

    final public static double NEST_SIZE = 200;

    // Constructor based on ID
    public Nest(int id) {
        this.id = id;
        initNest();

        //register event handler

    }

    // Set Nest appearance and create 4 pieces to add to Nest
    public void initNest() {
        // Draw a 200x200 colored square
        Rectangle rect = new Rectangle(NEST_SIZE, NEST_SIZE);
        // Add css file
        getStylesheets().add(getClass().getResource("/cocangua.css").toExternalForm());
        rect.setId("nestSq");

        switch (id){ //set color base on nestId.
            case 0:
                rect.setFill(Color.DODGERBLUE);
                break;
            case 1:
                rect.setFill(Color.GOLD);
                break;
            case 2:
                rect.setFill(Color.SEAGREEN);
                break;
            case 3:
                rect.setFill(Color.TOMATO);
                break;
        }

        // Draw a circle of 75 radius
        Circle circle = new Circle(75);
        circle.setFill(WHITE);

        // Add 4 pieces to pieceList
        for (int pieceID = 0; pieceID < pieceList.length; pieceID++) {
            Piece piece = new Piece(this.id, pieceID);
            pieceList[pieceID] = piece;
        }

        this.getChildren().addAll(rect, circle);
    }

    public Piece[] getPieceList() {
        return pieceList;
    }
}