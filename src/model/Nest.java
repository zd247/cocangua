package model;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.WHITE;

public class Nest extends Pane {
    private int id;
    private Piece[] pieceList = new Piece[4];

    final public static double NEST_SIZE = 200;

    public Nest(int id) {
        this.id = id;
        initNest();

        //register event handler

    }

    /**
     * Populate the container with 4 Pieces and have them displayed on a 4x4 gridPane
     */
    public void initNest() {
        StackPane sp = new StackPane();

        // Draw a 200x200 colored square
        Rectangle rect = new Rectangle(NEST_SIZE, NEST_SIZE);
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

        // Add rectangle and circle to StackPane
        sp.getChildren().addAll(rect, circle);

        //add piece in the nest list
        for (int idPiece = 0; idPiece < pieceList.length; idPiece++) {
            Piece piece = new Piece(this.id, idPiece);
            pieceList[idPiece] = piece;
        }

        this.getChildren().addAll(sp);
    }

    public Piece[] getPieceList() {
        return pieceList;
    }
}