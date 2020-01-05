package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.*;
import static model.Map.*;


public class Piece extends Circle {
    private static final int RADIUS = 12;

    private int nestId;
    private int pieceId;
    private int currentPosition;
    private boolean isDeployed; // check if piece is still in nest
    private boolean isBlocked;
    private boolean isHome; // is inside one of six rectangle
    private Color color;

    Player player;// - get player by nestId - a static function
    boolean canDeploy = true; // this will be reference by the player later

    //Helper
    private double width_house;
    private double height_house;
    private int step = 0;   //how many steps that the piece goes
    //=================================[Init]=====================================

    // Construct piece based on nestId
    public Piece(int nestId, int pieceId) {
        this.nestId = nestId;
        this.pieceId = pieceId;
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

        // Set piece appearance
        this.setFill(color);
        this.setRadius(RADIUS);

        // Set move-related attributes
        this.currentPosition = -1; //inside nest status
        this.isDeployed = false;
        this.isBlocked = false;
        this.isHome = false;

        /* REGISTER EVENT HANDLERS */
        // Click event
        this.setOnMouseClicked(click -> {
            move(1);    //Assum move amount is 1
//                move(3); //check all cases (move from nest, move when is deployed...)
        });

        // Hover effect for pieces
        this.setOnMouseEntered(hover -> this.setFill(color.darker()));
        this.setOnMouseExited(endHover -> this.setFill(color));
    }

    //=================================[Move]=====================================

    /**
     * the controller move , use moveSpace and moveToHouse
     * @param moveAmount
     */
    public void move(int moveAmount){
        System.out.println(step);
        if (step + moveAmount < 49)
            //Run in the space map
            moveSpace(moveAmount);
        else {
            //reach to the arrival
            if (step == 48){ //at the arrival space
                if (moveAmount < 7) {//moveAmount should be 1-6
                    currentPosition = -1;   //Start index for house
                    moveToHouseDestination(moveAmount);
                }
            }
            else    //at the house destination
                if (currentPosition + 2 == moveAmount)
                    //get the position (in display board) + 1 (before postion) + 1 (index in map less than current)
                    moveToHouseDestination(1);
        }
    }

    /**
     * make the piece run by space and moveAmount
     * @param moveAmount
     */
    // Move a specified circle a certain amount
    private void moveSpace(int moveAmount) {
        if (!isBlocked){

            //checking out of boundary, not a case
            if (currentPosition + moveAmount > 47){
                moveAmount = moveAmount - (48 - currentPosition);//remaining moveAmount
                currentPosition = 0; //after that set the current position to 0, to start running index of spaceMap
                step++;
            }

            //deploying
            if (!isDeployed && canDeploy) {
                //Move the piece from nest
                Space homeSpace = getSpaceMap().get(getStartPosition(color)); //reference starting position
                double x = homeSpace.getLayoutX();
                double y = homeSpace.getLayoutY();
                currentPosition = getStartPosition(color) - 1 ;  //index at the start space
                moveAmount = 1; //no moving
                //move the piece to said location
                updateCurrentPosition(moveAmount, x,y);
                isDeployed = true;  //Piece had already move outside the nest
            }else {
                //Move the piece in space
                Space sp1 = getSpaceMap().get(currentPosition + moveAmount); //running in the space
                double x = sp1.getLayoutX();
                double y = sp1.getLayoutY();
                // move the piece to said location
                updateCurrentPosition(moveAmount,x,y);
            }
        }
    }

    /**
     * get into the house destination
     * @param moveAmount
     */
    private void moveToHouseDestination(int moveAmount){
        //start to move into the houseDestination
        if (!isBlocked){
            House hs = getHouseMap().get(currentPosition + getHouseArrival() + moveAmount);
            double x = hs.getLayoutX() + width_house;
            double y = hs.getLayoutY() + height_house;
            //move the piece to said location
            updateCurrentPosition(moveAmount,x,y);
        }
    }

    /**
     * update to its next position, and make the piece move
     * @param moveAmount
     * @param x
     * @param y
     */
    private void updateCurrentPosition(int moveAmount,double x, double y) {
        currentPosition += moveAmount;
        step += moveAmount;
        setLayoutX(x);
        setLayoutY(y);
    }

    /**
     * get StartSpace when the piece is deployed
     * @param nestColor
     * @return spaceNumber
     */
    private int getStartPosition(Color nestColor){
        if (DODGERBLUE.equals(color)) {
            return BLUE_START;
        } else if (GOLD.equals(color)) {
            return YELLOW_START;
        } else if (SEAGREEN.equals(color)) {
            return GREEN_START;
        } else if (TOMATO.equals(color)) {
            return RED_START;
        }
        return 0;
    }

    /**
     * get the index of house in housemap , also get the height and width of each house to set piece at center
     * @return
     */
    private int getHouseArrival(){
        switch (nestId){
            case 0:{
                width_house = Map.HOUSE_LONG_SIDE/2;
                height_house = Map.HOUSE_SHORT_SIDE/2;
                return Map.BLUE_HOUSE_1;
            }
            case 1:{
                width_house = Map.HOUSE_SHORT_SIDE/2;
                height_house = Map.HOUSE_LONG_SIDE/2;
                return Map.YELLOW_HOUSE_1;
            }
            case 2:{
                width_house = Map.HOUSE_LONG_SIDE/2;
                height_house = Map.HOUSE_SHORT_SIDE/2;
                return Map.GREEN_HOUSE_1;
            }
            case 3: {
                width_house = Map.HOUSE_SHORT_SIDE / 2;
                height_house = Map.HOUSE_LONG_SIDE / 2;
                return Map.RED_HOUSE_1;
            }
        }
        return 0;   //return dummy value
    }

    /**
     * to settle piece in the nest with its id
     * @param nestId
     */
    public void pieceNest(int nestId){
        //to set the piece with its id nicely
        int ver,hor;
        if (pieceId == 0) {
            hor = -1;
            ver = -1;
        } else if (pieceId == 1) {
            hor = 1;
            ver = -1;
        } else if (pieceId == 2) {
            hor = -1;
            ver = 1;
        } else {
            hor = 1;
            ver = 1;
        }
        Nest nest = Map.getNestMap().get(nestId);
        setLayoutX(nest.getLayoutX() + Nest.NEST_SIZE / 2 + hor * 20);  //20 is gap piece
        setLayoutY(nest.getLayoutY() + Nest.NEST_SIZE / 2 + ver * 20);
    }


}
