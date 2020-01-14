/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/

package model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.core.Piece;

// Home rectangle (6 for each color)
public class House extends StackPane implements Position {
    boolean isOccupied = false;
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