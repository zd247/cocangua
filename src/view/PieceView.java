package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.House;
import model.Map;
import model.Space;

public class PieceView extends Circle {

    private final static int GAP_PIECE = 20;
    public PieceView(Color color){
        setRadius(12);
        setFill(color);
    }

    /**
     * Get the start for 4 pieces on each nest
     * @param map
     * @param idPiece : to place in order
     * @param nestId : to get the nest in hashmap
     */
    public void startPosition(Map map, int idPiece, int nestId ){
        //Display pieces each nest
        int ver, hor;
        if (idPiece==0){
            hor = -1;
            ver = -1;
        }
        else if (idPiece == 1){
            hor = 1;
            ver = -1;
        }
        else if (idPiece == 2){
            hor = -1;
            ver = 1;
        }
        else {
            hor = 1;
            ver = 1;
        }

        NestView nest = map.getNestViewMap().get(nestId);

        setLayoutX(nest.getLayoutX() + NestView.NEST_SIZE / 2 + hor * GAP_PIECE);
        setLayoutY(nest.getLayoutY() + NestView.NEST_SIZE / 2 + ver * GAP_PIECE);
    }

    /**
     * To make the piece move on space
     * @param currentIndex
     * @param moveAmount
     * @param map
     * @param regionColor : to get the start position
     * @param isHome
     * @param isBlocked
     * @return currentIndex+moveAmount || currentIndex
     */
    // Move a specified circle a certain amount
    public int movePiece(int currentIndex, int moveAmount, Map map, Color regionColor, boolean isHome, boolean isBlocked) {
        if (!isBlocked){

            if (currentIndex+moveAmount > 47){
                //If the piece goes out of index, then set the current index 0
                moveAmount = moveAmount - (48 - currentIndex);
                currentIndex = 0;
            }

            if (isHome){
                //when the piece starts to move from its nest

                Space homeSpace = map.getSpaceMap().get(getStartPosition(regionColor));
                //to get the home space

                setLayoutX(homeSpace.getLayoutX());
                setLayoutY(homeSpace.getLayoutY());

                return getStartPosition(regionColor);
            }else {
                Space sp1 = map.getSpaceMap().get(currentIndex + moveAmount);

                //get previous space
                Space prevSpace = map.getSpaceMap().get(currentIndex);

                if (!sp1.getOccupancy()) {
                    //need more update
                    setLayoutX(sp1.getLayoutX());
                    setLayoutY(sp1.getLayoutY());

                    sp1.setOccupancy(true);

                    return currentIndex+moveAmount;
                }
                else
                    System.out.println("Space is occupied");
            }
        }
        return currentIndex;
    }

    /**
     * To support the move piece on space, make the piece of its nest color to move on the right start
     * @param nestColor
     * @return
     */
    private int getStartPosition(Color nestColor){
        //To find the start space
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


    /**
     * to make the piece move on the right house
     * @param map
     * @param currentIndex
     * @param idNest
     * @param moveAmount
     * @param isBlocked
     * @return
     */
    public int moveToHouse(Map map, int currentIndex, int idNest, int moveAmount, boolean isBlocked){
        if (!isBlocked){
            if (moveAmount == 0){
                //when the piece is at space arrival
                return currentIndex;
            }
            House hs1 = map.getHouseMap().get(currentIndex + getHomeArrival(idNest) + moveAmount);
            //get house from the housemap

            setLayoutX(hs1.getLayoutX() + Map.HOUSE_LONG_SIDE/2);
            setLayoutY(hs1.getLayoutY() + Map.HOUSE_SHORT_SIDE/2);

            return currentIndex + moveAmount;
            //to save for the next step
        }
        return currentIndex;
    }

    /**
     * to support houseMap, get the right current position
     * @param idNest
     * @return
     */
    private int getHomeArrival(int idNest){
        //To get the index in the hashmap
        switch (idNest){
            case 0:
                return Map.BLUE_HOUSE_1;
            case 1:
                return Map.YELLOW_HOUSE_1;
            case 2:
                return Map.GREEN_HOUSE_1;
            case 3:
                return Map.RED_HOUSE_1;
        }
        return 0;
    }
}

