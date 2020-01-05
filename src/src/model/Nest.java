package model;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javafx.scene.paint.Color.WHITE;

public class Nest extends Pane {
    private int id;
    private GridPane pieces = new GridPane();


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


        // Draw a circle
        Circle circle = new Circle(75); // magic number
        circle.setCenterX(95); // magic number
        circle.setCenterY(95);
        circle.setFill(WHITE);

        pieces.setLayoutX(75);
        pieces.setLayoutY(75);
        // add pieces (2x2)
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Piece piece = new Piece(this.id);
                pieces.add(piece, i, j);
            }
        }


        this.getChildren().addAll(rect, circle, pieces);
    }
}