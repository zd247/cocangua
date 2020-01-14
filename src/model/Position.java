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

import model.core.Piece;

// Interface for all spaces
public interface Position {
    // Get and set occupancy state
    boolean getOccupancy();
    void setOccupancy(boolean isOccupied);

    // Get and set Piece
    Piece getPiece();
    void setPiece(Piece piece);
}
