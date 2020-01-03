package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Map;
import model.Space;

public class PieceView extends Circle {

    public PieceView(Color color){
        setRadius(10);
        setFill(color);
    }

    public double getX(){return this.getLayoutX();}

    public double getY(){return this.getLayoutY();}

    // Move a specified circle a certain amount
    public int movePiece(int currentIndex, int moveAmount, Map map, Color regionColor, boolean isHome, boolean isBlocked) {
        if (!isBlocked){

            //If the piece goes out of index, then set the current index 0
            if (currentIndex+moveAmount > 47){
                moveAmount = moveAmount - (48 - currentIndex);
                currentIndex = 0;
            }
            if (isHome){
                Space homeSpace = map.getSpaceMap().get(getStartPosition(regionColor));
                double x = homeSpace.getLayoutX();
                double y = homeSpace.getLayoutY();
                setLayoutX(x);
                setLayoutY(y);
                return getStartPosition(regionColor);
            }else {
                Space sp1 = map.getSpaceMap().get(currentIndex + moveAmount);

                //get previous space
                Space prevSpace = map.getSpaceMap().get(currentIndex);

                double x = sp1.getLayoutX();
                double y = sp1.getLayoutY();

                if (!sp1.getOccupancy()) {
                    setLayoutX(x);
                    setLayoutY(y);
                    sp1.setOccupancy(true);
                    prevSpace.setOccupancy(false);
                    return currentIndex+moveAmount;
                }
                else
                    System.out.println("Space is occupied");
            }
        }
        return currentIndex;
    }

    public void startPosition(Map map, int nestId){
        NestView nest = map.getNestViewMap().get(nestId);
        double x = nest.getLayoutX() + NestView.NEST_SIZE / 2;
        double y = nest.getLayoutY() + NestView.NEST_SIZE / 2;
        setLayoutX(x);
        setLayoutY(y);
    }

    public int getStartPosition(Color nestColor){
        if (Map.REGION_COLOR[0].equals(nestColor)) {
            return Map.BLUE_START;
        } else if (Map.REGION_COLOR[1].equals(nestColor)) {
            return Map.YELLOW_START;
        } else if (Map.REGION_COLOR[2].equals(nestColor)) {
            return Map.GREEN_START;
        } else if (Map.REGION_COLOR[3].equals(nestColor)) {
            return Map.RED_START;
        }
        return 0;
    }

    public int checkOneRound(int currentIndex, int moveAmout){
        if (currentIndex+moveAmout > Map.RED_ARRIVAL + 10){
            return 0;
        }
        return 0;
    }
}

