package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static model.Map.*;


public class Piece extends Circle {
    private static final int RADIUS = 12;

    private int nestId;
    private int currentPosition;
    private boolean isDeployed; // check if piece is still in nest
    private boolean isBlocked;
    private boolean isHome; // is inside one of six rectangle
    private Color color;





    Player player;// - get player by nestId - a static function
    boolean canDeploy = true; // this will be reference by the player later


    //=================================[Init]=====================================


    public Piece(int nestId) {
        this.nestId = nestId;


        // Display
        switch (nestId){ //set color base on nestId.
            case 0:
                this.color = Color.DODGERBLUE;
                break;
            case 1:
                this.color = Color.GOLD;
                break;
            case 2:
                this.color = Color.SEAGREEN;
                break;
            case 3:
                this.color = Color.TOMATO;
                break;

        }
        this.setFill(color);
        this.setRadius(RADIUS);

        this.currentPosition = -1; //inside nest status
        this.isDeployed = false;
        this.isBlocked = false;
        this.isHome = false;


        //Register event handler
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("something");
//                move(3); //check all cases (move from nest, move when is deployed...)

            }
        });

    }


    //=================================[Move]=====================================

    public double getX(){return this.getLayoutX();}

    public double getY(){return this.getLayoutY();}

    // Move a specified circle a certain amount
    private int move(int moveAmount) {
        if (!isBlocked){
            //MoveAmount out of range, go to prepareHome position (could be a status here if needed)
//            if (currentPosition + moveAmount > 47){
//                moveAmount = moveAmount - (48 - currentPosition);
//                currentPosition = 0; // not yet done, get homeGate position (0 for blue, 12 for yellow etc)
//                updateCurrentPosition(moveAmount, map.spaceMap.get(BLUE_ARRIVAL));
//                return 1;
//            }
            //deploying
            if (!isDeployed && canDeploy){
                Space homeSpace = getSpaceMap().get(getStartPosition(color)); //reference starting position
                double x = homeSpace.getLayoutX();
                double y = homeSpace.getLayoutY();
                //move the piece to said location
                updateCurrentPosition(currentPosition + moveAmount, x,y);

                return 2;
            }else {
                Space sp1 = getSpaceMap().get(currentPosition + moveAmount);

                //get previous space
                Space prevSpace = getSpaceMap().get(currentPosition);

                double x = sp1.getLayoutX();
                double y = sp1.getLayoutY();

                // call kick function ()

                if (!sp1.getOccupancy()) {
                    setLayoutX(x);
                    setLayoutY(y);
                    sp1.setOccupancy(true);
                    prevSpace.setOccupancy(false);
                    return currentPosition+moveAmount;
                }
                else
                    System.out.println("Space is occupied");
            }
        }
        return -1; // for move cant be performed signal
    }

    private void updateCurrentPosition(int moveAmount,double x, double y) {
        currentPosition += moveAmount;
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
