package model;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class Nest {
    private int id;
    private Color color;
    private ArrayList<Piece> pieces;

    public Nest(int id) {
        this.id = id;
        this.color = Color.BLUE;

        // CHANGED DUE TO CHANGES IN PIECE - HAN
        pieces = new ArrayList<>();
    }

    //getter
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public Color getColor() {
        return color;
    }

    public int getNestId() {
        return this.id;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //Method
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece (Piece piece) {
        pieces.remove(piece);
    }

    /** piece needs id to be displayed correctly to its number and grid position*/
    public void displayPieces(GridPane nest) {
        nest.getChildren().retainAll(nest.getChildren().get(0));
        switch (pieces.size()) {
            case 1: {
                nest.add(generateNewPiece(1), 0, 0);
                break;
            }
            case 2: {
                nest.add(generateNewPiece(1), 0, 0);
                nest.add(generateNewPiece(2), 1, 0);
                break;
            }
            case 3: {
                nest.add(generateNewPiece(1), 0, 0);
                nest.add(generateNewPiece(2), 1, 0);
                nest.add(generateNewPiece(3), 0, 1);
                break;
            }
            case 4: {
                nest.add(generateNewPiece(1), 0, 0);
                nest.add(generateNewPiece(2), 1, 0);
                nest.add(generateNewPiece(3), 0, 1);
                nest.add(generateNewPiece(4), 1, 1);
                break;
            }
        }
    }

    private Label generateNewPiece(int number) {
        Label newLabel = new Label();
        newLabel.alignmentProperty().set(Pos.CENTER);
        newLabel.contentDisplayProperty().set(ContentDisplay.CENTER);
        newLabel.textProperty().setValue( Integer.toString(number));
        newLabel.textAlignmentProperty().set(TextAlignment.CENTER);
        newLabel.textFillProperty().set(Color.WHITE);
        newLabel.fontProperty().set(Font.font("Arial",25.0));
        newLabel.setStyle("-fx-font-weight: bold");
        Circle tmp = new Circle(15.0, color);
        tmp.strokeProperty().set(Color.BLACK);
        tmp.strokeTypeProperty().set(StrokeType.INSIDE);
        newLabel.graphicProperty().set(tmp);
        return newLabel;
    }


}