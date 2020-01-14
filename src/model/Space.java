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

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.core.Piece;

import static javafx.scene.paint.Color.WHITE;

// The circle that a Piece will travel through
// Can be accessed via index at map.spaceMap
// Get coordinates by space.getLayoutX() and space.getLayoutY()
public class Space extends Circle implements Position {
    public Shape rect;
    boolean isOccupied;
    Piece piece;

    public Space(Color color) {
        setRadius(20);
        setStroke(color);
        setStrokeWidth(2);
        setFill(WHITE);
        this.piece = null;
        rect = new Rectangle();
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
