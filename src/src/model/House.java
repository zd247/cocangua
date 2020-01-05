package model;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.WHITE;

// Home rectangle (6 for each color)
public class House extends StackPane implements Position {
    boolean isOccupied;
    Piece piece;

    // Construct a space bases on input
    public House(Color color, double width, double height, int position) {
        // Set House appearance
        Rectangle rec = new Rectangle(width, height);
        rec.setStroke(color);
        rec.setStrokeWidth(2);
        rec.setFill(WHITE);

        // Stack number label on top
        Label houseLb = new Label("" + position);
        houseLb.setStyle("-fx-font-size: 20");
        houseLb.setTextFill(color);

        this.getChildren().addAll(rec, houseLb);

        piece = null;   // Initially, no piece on the House
    }

    @Override
    public boolean getOccupancy() {
        return isOccupied;
    }

    @Override
    public void setOccupancy(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
