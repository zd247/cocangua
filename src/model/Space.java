package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// The circle that a Piece will travel through
// Can be accessed via index at map.spaceMap
// Get coordinates by space.getLayoutX() and space.getLayoutY()
public class Space extends Circle {
    boolean isOccupied;
    private Piece piece;    // Piece currently on space

    // Construct a space bases on input
    public Space(Color color) {
        setRadius(20);
        setFill(color);
        this.piece = null;
    }

    // Get and set occupancy state
    public boolean getOccupancy() { return this.isOccupied; }
    public void setOccupancy(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    // Get and set Piece
    public Piece getPiece() { return this.piece; }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
