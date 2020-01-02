package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyNest extends Rectangle {
    // Default size for Nest square
    final public static double NEST_SIZE = 200;

    // Construct a Nest of specified color
    public MyNest(Color color) {
        this.setWidth(NEST_SIZE);
        this.setHeight(NEST_SIZE);
        this.setFill(color);
    }
}
