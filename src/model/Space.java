package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// The circle that a Piece will travel through
public class Space extends Circle {
    boolean isOccupied;
    private Piece piece;    // Piece currently on space

    // Construct a space bases on input
    public Space(Color color) {
        setRadius(20);
        setFill(color);
        this.piece = null;
    }

    // Get coordinates of this space
    public double getX() {
        return this.getLayoutX();
    }
    public double getY() {
        return this.getLayoutY();
    }
}
