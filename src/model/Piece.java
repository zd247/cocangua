package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static model.Map.*;
import static statics.StaticContainer.*;


public class Piece extends Circle {
    private static final int RADIUS = 12;

    private int nestId;
    private int pieceId;
    private int currentPosition;
    private boolean isDeployed; // check if piece is still in nest
    private boolean isBlocked;
    private boolean isHouse; // is inside one of six rectangle
    private boolean isAtArrival;
    private Color color;
    private int move;
    private boolean isClicked;


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
        this.isHouse = false;
        this.isAtArrival =false;

        /* REGISTER EVENT HANDLERS */

        // Hover effect for pieces
        this.setOnMouseEntered(hover -> this.setFill(color.darker()));
        this.setOnMouseExited(endHover -> this.setFill(color));
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setClicked(true);
                handleOnClickLogic();
            }
        });
    }

    //=================================[Move]=====================================

    /**
     * when click on a piece
     */
    void handleOnClickLogic() {
        int initialPosition = this.currentPosition;
        if (diceTurn == 0) { // turn 1
            playerMoveAmount = diceValue1;
            if (this.currentPosition != -1)
                diceTurn = 1;
        } else if (diceTurn == 1) { // turn 2
            playerMoveAmount = diceValue2;
            if (this.currentPosition != -1) {
                diceTurn = 2; //reset
            }
        }
        // case 1: piece being blocked and
        if (!ableToMove(playerMoveAmount) && !ableToKick(playerMoveAmount)
                && this.currentPosition != -1 && diceTurn == 1) {
            if (ableToMove(diceValue2) || ableToKick(diceValue2)) {
                playerMoveAmount = diceValue2;
                diceValue2 = diceValue1;
                diceValue1 = playerMoveAmount;
                System.out.println("dzo");
            }
        }
        //case 2: not blocked or able to kick
        if (!this.isBlockedPiece(playerMoveAmount) || this.ableToKick(playerMoveAmount)) {
            // case 2.1: check for when able to kick
            if (ableToKick(playerMoveAmount)){
                if (this.currentPosition != -1) {
                    int next = this.currentPosition +playerMoveAmount;
                    if (next > 47){
                        next -= 48;
                    }

                    this.kick(spaceMap.get(next).getPiece());
                    spaceMap.get(next).setOccupancy(false);

                } else {
                    int next ;
                    next = this.getStartPosition(this.nestId);   //get the piece at start position

                    this.kick(spaceMap.get(next).getPiece());
                    spaceMap.get(next).setOccupancy(false);
                }
            }
            //case 2.2: able to move
            if ((this.currentPosition != -1 || (diceValue1 == 6 || diceValue2 == 6) && diceTurn == 0 && diceTurn != 3)) {
                if (this.currentPosition == -1) {
                    diceTurn = 2;
                }

                if (this.getStep() == 48){
                    if (diceValue1 < diceValue2 && diceTurn == 1){
                        playerMoveAmount = diceValue2;
                        diceValue2 = diceValue1;
                        diceValue1 = playerMoveAmount;

                    }
                }
                System.out.println("go");
                this.move(playerMoveAmount);
                if (this.getCurrentPosition() == initialPosition){
                    diceTurn--;
                }

                if (initialPosition != -1) {
                    spaceMap.get(initialPosition).setOccupancy(false);
                    spaceMap.get(initialPosition).setPiece(null);
                }
                if (this.currentPosition <= 47) {
                    spaceMap.get(this.currentPosition).setOccupancy(true);
                    spaceMap.get(this.currentPosition).setPiece(this);
                }
            }
        }
        //case 3: piece is blocked on board
        if (this.isBlockedPiece(playerMoveAmount) && this.currentPosition != -1){
            diceTurn--; //reset turn
        }
        //case 4:
        if (this.currentPosition != -1 && !this.ableToMove(diceValue2)
                && !this.ableToKick(diceValue2) && diceTurn == 1){
            diceTurn = 3;
        }

        //reset player and dice turns
        if (diceTurn >= 2){
            if (diceValue1 == diceValue2) id--;
            turn = 0;
            players[nestId].resetCheck();
            diceTurn = 0;
        }

    }

    /**
     * Check for if current piece is able to perform the moveAmount
     * @param moveAmount
     * @return
     */
    public boolean ableToMove(int moveAmount){
        int check =0;
        int next = this.currentPosition + moveAmount;
        Space nextSpace = spaceMap.get(next);
        if(nextSpace.getOccupancy()){
            if (nextSpace.getPiece().getNestId() == nestId){
                check ++;
                if (nextSpace.getPiece().getStep() == 48)
                    return true;
                else if (!nextSpace.getPiece().isBlockedPiece(moveAmount)
                        &&  nextSpace.getPiece().getStep() + moveAmount <= 48) // extra cryptic?
                    return true;
            }

        }
        return check == 0 && (diceValue1 == 6 || diceValue2 == 6) ;
    }

    /**
     *
     * @param moveAmount dice value get for each dice when done rolling
     * @return
     */
    boolean ableToKick(int moveAmount){
        if (this.currentPosition == -1) { //case piece inside nest
            if (nestId == 0 && spaceMap.get(BLUE_START).getOccupancy()) {
                return spaceMap.get(BLUE_START).getPiece().getNestId() != nestId;
            } else if (nestId == 1 && spaceMap.get(YELLOW_START).getOccupancy()) {
                return spaceMap.get(YELLOW_START).getPiece().getNestId() != nestId;
            } else if (nestId == 2 && spaceMap.get(GREEN_START).getOccupancy()) {
                return spaceMap.get(GREEN_START).getPiece().getNestId() != nestId;
            } else if (nestId == 3 && spaceMap.get(RED_START).getOccupancy()) {
                return spaceMap.get(RED_START).getPiece().getNestId() != nestId;
            }
            else{
                return false;
            }
        }
        else { // case piece outside of board
            int next = this.currentPosition + moveAmount;
            if (next > 47){
                next = next - 48;
            }
            if (spaceMap.get(next).getOccupancy() && !isBlockedPiece(moveAmount-1)) {
                return nestId != spaceMap.get(next).getPiece().getNestId();
            }
            return false;
        }
    }

    /**
     * check for the next translated position for occupancy
     * @param amount dice value get for each dice when done rolling
     * @return is blocked by another or not
     */
    boolean isBlockedPiece(int amount) {
        if (this.currentPosition == -1) { // outside of nest
            switch (nestId){
                case 0:
                    return spaceMap.get(BLUE_START).getOccupancy();
                case 1:
                    return spaceMap.get(YELLOW_START).getOccupancy();
                case 2:
                    return spaceMap.get(GREEN_START).getOccupancy();
                case 3:
                    return spaceMap.get(RED_START).getOccupancy();
            }
        } else {
            if (!this.isAtArrival){ // need to set this since this is merged
                int next = this.currentPosition + amount;
                if (next == 47) this.currentPosition = -1; //reset ?? go back to house or what ?? the fuck is this?
                if (spaceMap.get(this.currentPosition+1).getOccupancy()) return true;
            }else { // logic here
                int start = 0;
                switch (nestId){
                    case 0:
                        start = BLUE_HOUSE_1;
                    case 1:
                        start = YELLOW_HOUSE_1;
                    case 2:
                        start = GREEN_HOUSE_1;
                    case 3:
                        start = RED_HOUSE_1;
                }
                for (int i = start; i < start + amount; i++){
                    if (spaceMap.get(i).getOccupancy()){
                        return true;
                    }
                }
                return false;
            }

        }
        return false;
    }

    /**
     * the controller move , use moveSpace and moveToHouse
     * @param moveAmount
     * @return point
     */
    public int move(int moveAmount){
        if (step + moveAmount < 49)
            //Run in the space map
            moveSpace(moveAmount);
        else {
            //reach to the arrival
            if (step == 48){ //at the arrival space
                currentPosition = step;
                moveToHouseDestination(moveAmount);
                return moveAmount;
            }
            else    //at the house destination
                if ( (step -47) == moveAmount){
                    //get the position (in display board) + 1 (before postion) + 1 (index in map less than current)
                    moveToHouseDestination(1);
                    return 1;
                }

        }
        return 0;
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
                if (currentPosition + moveAmount == 48){
                    step += moveAmount;
                    moveAmount = 0;
                }
                else {
                    step += (48 - currentPosition);
                    moveAmount = moveAmount - (48 - currentPosition);//remaining moveAmount
                }
                currentPosition = 0; //after that set the current position to 0, to start running index of spaceMap
            }

            //deploying
            if (!isDeployed && canDeploy) {
                //Move the piece from nest

                Space homeSpace = spaceMap.get(getStartPosition(nestId)); //reference starting position
                double x = homeSpace.getLayoutX();
                double y = homeSpace.getLayoutY();
                currentPosition = getStartPosition(nestId) ;  //index at the start space
                step = 1;
                //move the piece to said location
                isDeployed = true;  //Piece had already move outside the nest
                updateCurrentPosition(0,x,y);
            }else {
                //Move the piece in space
                Space sp1 = spaceMap.get(currentPosition + moveAmount); //running in the space
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
    public void moveToHouseDestination(int moveAmount){
        //start to move into the houseDestination
        if (!isBlocked){
            House hs = houseMap.get(step - 49 + getHouseArrival() + moveAmount);
//            House hs = houseMap.get(step - 49 + getHouseArrival() + moveAmount);
            double x = hs.getLayoutX() + width_house;
            double y = hs.getLayoutY() + height_house;
            //move the piece to said location
            updateCurrentPosition(moveAmount,x,y);
            this.isHouse = true;
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
     * @return spaceNumber
     */
    public int getStartPosition(int nestId){
        switch (nestId){
            case 0:
                return BLUE_START;
            case 1:
                return YELLOW_START;
            case 2:
                return GREEN_START;
            case 3:
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
        Nest nest = nestMap.get(nestId);
        setLayoutX(nest.getLayoutX() + Nest.NEST_SIZE / 2 + hor * 20);  //20 is gap piece
        setLayoutY(nest.getLayoutY() + Nest.NEST_SIZE / 2 + ver * 20);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getNestId() {
        return nestId;
    }

    public void setCurrentPosition(int position){
        currentPosition = position;
    };

    public int getMove() {
        return move;
    }

    public int getStep(){
        return step;
    }

    public int moveToHouse(Map map, int currentIndex, int idNest, int moveAmount, boolean isBlocked) {
        if (!isBlocked) {
            if (moveAmount == 0) {
                //when the piece is at space arrival
                return currentIndex;
            }
            House hs1 = houseMap.get(currentIndex + getHouseArrival() + moveAmount);
            //get house from the housemap

            setLayoutX(hs1.getLayoutX() + width_house);
            setLayoutY(hs1.getLayoutY() + height_house);

            return currentIndex + moveAmount;
            //to save for the next step
        }
        return currentIndex;
    }

    public void kick(Piece piece){
        piece.startPosition(piece.getPieceId(),piece.getNestId());
        piece.setCurrentPosition(-1);
        piece.setDeployed(false);
        piece.setStep(0);
    }

    public void startPosition(int idPiece, int nestId) {
        //Display pieces each nest
        int ver, hor;
        if (idPiece == 0) {
            hor = -1;
            ver = -1;
        } else if (idPiece == 1) {
            hor = 1;
            ver = -1;
        } else if (idPiece == 2) {
            hor = -1;
            ver = 1;
        } else {
            hor = 1;
            ver = 1;
        }

        Nest nest = nestMap.get(nestId);

        setLayoutX(nest.getLayoutX() + Nest.NEST_SIZE / 2 + hor * 20); //gap piece
        setLayoutY(nest.getLayoutY() + Nest.NEST_SIZE / 2 + ver * 20); //gap piece
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setClicked(boolean clicked) {
        this.isClicked = clicked;
    }

    public boolean getClicked() {
        return isClicked;
    }
}
