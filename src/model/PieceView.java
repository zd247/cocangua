package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PieceView extends Circle {
    PieceView(Color color){
        setRadius(10);
        setFill(color);
    }
    public double getX(){return this.getLayoutX();}
    public double getY(){return this.getLayoutY();}

}
