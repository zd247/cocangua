package view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Nest;

import static javafx.scene.paint.Color.*;

// Just a colored square with an associated Nest
public class NestView extends StackPane {
    final public static double NEST_SIZE = 200;

    private Nest nest;  // The nest associated with this view

    // Construct a Nest of specified color
    public NestView(Color color) {
        // Draw a 200x200 colored square
        Rectangle rec = new Rectangle(NEST_SIZE, NEST_SIZE);
        rec.setFill(color);

        // Draw a circle
        Circle circle = new Circle(75);
        circle.setStroke(WHITE);
        circle.setStrokeWidth(1);
        circle.setFill(color);

        // Place circle on the square
        this.getChildren().addAll(rec, circle);

        // At start, no Nest associated with this
        this.nest = null;
    }

    // Nest getter and setter
    public Nest getNest() { return this.nest; }
    public void setNest(Nest nest) {
        this.nest = nest;
    }
}
