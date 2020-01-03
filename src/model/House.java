package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Home rectangle (6 for each color)
public class House extends Rectangle implements Position {
    boolean isOccupied;
    Piece piece;

    // Construct a space bases on input
    public House(Color color, double width, double height) {
        setWidth(width);
        setHeight(height);
        setFill(color);
        piece = null;
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
