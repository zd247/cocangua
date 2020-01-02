package view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.*;

public class MyNest extends StackPane {
    // Default size for Nest square
    final public static double NEST_SIZE = 200;

    // Construct a Nest of specified color
    public MyNest(Color color) {
        Rectangle rec = new Rectangle(NEST_SIZE, NEST_SIZE);
        rec.setFill(color);

        Circle circle = new Circle(75);
        circle.setStroke(WHITE);
        circle.setStrokeWidth(1);
        circle.setFill(color);

        this.getChildren().addAll(rec, circle);
    }
}
