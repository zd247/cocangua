package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Piece extends Circle {
    private static final int RADIUS = 12;

    private int nestId;
    private int currentPosition;
    private boolean isDeployed;
    private boolean isBlocked;
    private boolean isHome;


    //=================================[Init]=====================================


    public Piece(int nestId) {
        this.nestId = nestId;

        // Display
        switch (nestId){ //set color base on nestId.
            case 0:
                this.setFill(Color.DODGERBLUE);
                break;
            case 1:
                this.setFill(Color.GOLD);
                break;
            case 2:
                this.setFill(Color.SEAGREEN);
                break;
            case 3:
                this.setFill(Color.TOMATO);
                break;

        }
        this.setRadius(RADIUS);

        this.currentPosition = -1; //inside nest status
        this.isDeployed = false;
        this.isBlocked = false;
        this.isHome = false;


        //Register event handler
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                /*
                if(players[finalI].isRolled()) {
                    if (id == finalI) {
                        if (piece.getCurrentPosition() != -1 || players[finalI].getDices()[0].getFace() == 6 || players[finalI].getDices()[1].getFace() == 6) {
                            if (piece.getMove() + moveAmount < 48){
                                //when the piece runs 1 round of map

                                players[finalI].resetCheck();

                                piece.setBlocked(false);
                                //First set the piece block is false, should be deleted

                                int nextPosition = p.movePiece(piece.getCurrentPosition(), moveAmount, map, Map.REGION_COLOR[finalI], piece.isHome(), piece.isBlocked());
                                //To make the piece move with MoveAmount step

                                piece.setCurrentPosition(nextPosition);
                                //and get the next position for another turn

                                if (piece.isHome()) {
                                    //once the piece get out of the nest

                                    piece.setHome(false);
                                    piece.setMove(1);
                                    //start move at 1
                                }
                                else
                                    piece.setMove(piece.getMove()+moveAmount);
                            }
                        }
                    }
                }
                map.getChildren().add(p);
                 */
            }
        });

    }


    //=================================[Move]=====================================

    public double getX(){return this.getLayoutX();}

    public double getY(){return this.getLayoutY();}

    // Move a specified circle a certain amount
    public int movePiece(int currentIndex, int moveAmount, Map map, Color regionColor, boolean isHome, boolean isBlocked) {
        if (!isBlocked){

            //If the piece goes out of index, then set the current index 0
            if (currentIndex + moveAmount > 47){
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
        Nest nest = map.getNestMap().get(nestId);
        double x = nest.getLayoutX() + Nest.NEST_SIZE / 2;
        double y = nest.getLayoutY() + Nest.NEST_SIZE / 2;
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

    /**
     * Handle onClick event
     */
    class OnClickPiece implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {

        }
    }


}
