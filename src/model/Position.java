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
