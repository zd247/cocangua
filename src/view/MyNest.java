package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyNest extends Rectangle {
    static int nestID;

    MyNest(Color color) {
        this.setWidth(50);
        this.setHeight(50);
        this.setFill(color);
    }

}
