package controller;

import javafx.scene.paint.Color;
import model.Map;
import model.Piece;
import view.PieceView;

/**
 * Testing , may be applied
 */

public class PieceController {
    private Piece piece;
    private PieceView view;

    public PieceController(int nestId, int currentPosition, Color color){
        piece = new Piece(nestId, currentPosition);
        view = new PieceView(color);
    }

    public int getNestId() {
        return piece.getNestId();
    }

    public void setNestId(int nestId) {
        piece.setNestId(nestId);
    }

    public int getCurrentPosition() {
        return piece.getCurrentPosition();
    }

    public void setCurrentPosition(int currentPosition) {
        piece.setCurrentPosition(currentPosition);
    }

    public boolean isDeployed() {
        return piece.isDeployed();
    }

    public void setDeployed(boolean deployed) {
        piece.setDeployed(deployed);
    }

    public boolean isBlocked() {
        return piece.isBlocked();
    }

    public void setBlocked(boolean blocked) {
        piece.setBlocked(blocked);
    }

    public boolean isHome() {
        return piece.isHome();
    }

    public void setHome(boolean home) {
        piece.setHome(home);
    }

    public void move(int currentIndex, int moveAmount, Map map, Color nestColor){
        view.setOnMouseClicked(event -> {
            view.movePiece(currentIndex,moveAmount,map, nestColor,isHome(),isBlocked());
            if (isHome()){
                setHome(false);
            }
        });
    }

    public void startPosition(Map map,Color nestColor){
        view.startPosition(map,nestColor);
        piece.setBlocked(true);
        piece.setDeployed(false);
    }
}
