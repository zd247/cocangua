package model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

// Home rectangle (6 for each color)
public class House extends StackPane implements Position {
    boolean isOccupied;
    Piece piece;

    // Construct a space bases on input
    public House(Color color, double width, double height, int position) {
        // Set House appearance
        Rectangle rec = new Rectangle(width, height);
        rec.setFill(color);
        rec.setId("houseRec");  // For css file to refer to

        // Stack number label on top
        Text text = new Text(String.valueOf(position));
        text.setId("houseText");
        this.getChildren().addAll(rec, text);

        piece = null;   // Initially, no piece on the House

        // Add css file
        getStylesheets().add(getClass().getResource("/cocangua.css").toExternalForm());
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