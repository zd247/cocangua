package model;

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
    private int move;


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
        this.isHome = true;

        /* REGISTER EVENT HANDLERS */

        // Hover effect for pieces
        this.setOnMouseEntered(hover -> this.setFill(color.darker()));
        this.setOnMouseExited(endHover -> this.setFill(color));
    }

    //=================================[Move]=====================================
    void handleThisNewLogic() {
        if (nestCounter == 0) {
            curPlayerMoveAmount = diceValue1;
            if (this.currentPosition != -1)
                nestCounter = 1;
        } else if (nestCounter == 1) {
            curPlayerMoveAmount = diceValue2;
            if (this.currentPosition != -1) {
                nestCounter = 2;
            }
        }
        if (!ableToMove(nestId, curPlayerMoveAmount) && this.currentPosition != -1 && nestCounter == 1) {
            if (ableToMove(nestId, diceValue2) || ableToKick(this.currentPosition, diceValue2, nestId)) {
                curPlayerMoveAmount = diceValue2;
                diceValue2 = diceValue1;
                diceValue1 = curPlayerMoveAmount;
                System.out.println("dzo");
            }
        }
        if (!isBlockedPiece(this.currentPosition, curPlayerMoveAmount, nestId) || able_To_Kick(piece.getCurrentPosition(), p_move.moveAmount, finalI)) {
            if (able_To_Kick(piece.getCurrentPosition(), p_move.moveAmount, finalI)){
                if (piece.getCurrentPosition() != -1) {
                    int next = piece.getCurrentPosition() + p_move.moveAmount;
                    if (next > 47){
                        next -= 48;
                    }

                    piece.kick(Map.getSpaceMap().get(next).getPiece());
                    Map.getSpaceMap().get(next).setOccupancy(false);

                } else {
                    int next ;
                    next = piece.getStartPosition(piece.getNestId());   //get the piece at start position

                    piece.kick(Map.getSpaceMap().get(next).getPiece());
                    Map.getSpaceMap().get(next).setOccupancy(false);
                }
            }
            if ((piece.getCurrentPosition() != -1 || ((moveAmount1 == 6 || moveAmount2 == 6) && nest_counter.count == 0)) && nest_counter.count!= 3) {
                System.out.println(finalPieceId+ " if statement " + piece.getMove() + p_move.moveAmount);
                if (piece.getCurrentPosition() == -1) {
                    nest_counter.count = 2;
                }

                if (piece.getStep() == 48){
                    if (moveAmount1 < moveAmount2 && nest_counter.count == 1){
                        p_move.moveAmount = moveAmount2;
                        moveAmount2 = moveAmount1;
                        moveAmount1 = p_move.moveAmount;

                    }
                }
                System.out.println("go");
                piece.move(p_move.moveAmount);
                if (piece.getCurrentPosition() == initial){
                    nest_counter.count--;
                }

                if (initial != -1) {
                    Map.getSpaceMap().get(initial).setOccupancy(false);
                    Map.getSpaceMap().get(initial).setPiece(null);
                }
                if (piece.getCurrentPosition() <= 47) {
                    Map.getSpaceMap().get(piece.getCurrentPosition()).setOccupancy(true);
                    Map.getSpaceMap().get(piece.getCurrentPosition()).setPiece(piece);
                }
            }
        }

    }


    boolean ableToMove(int nestId, int dices){
        int check =0;
        for (int i = 0; i <=  47; i++){
            if( Map.getSpaceMap().get(i).getOccupancy())
            {
                if (Map.getSpaceMap().get(i).getPiece().getNestId() == nestId){
                    check ++;
                    if (Map.getSpaceMap().get(i).getPiece().getStep() == 48){
                        return true;
                    }
                    else if (!isBlockedPiece(Map.getSpaceMap().get(i).getPiece().getCurrentPosition(), dices, nestId) && Map.getSpaceMap().get(i).getPiece().getStep() + dices <= 48){
                        return true;
                    }
                }
            }
        }
        return check == 0 && (moveAmount1 == 6 || moveAmount2 == 6) ;
    }

    boolean ableToKick(int position, int diceAmount, int nestId){
        if (position == -1) {
            if (nestId == 0 && Map.getSpaceMap().get(Map.BLUE_START).getOccupancy()) {
                return Map.getSpaceMap().get(Map.BLUE_START).getPiece().getNestId() != nestId;
            } else if (nestId == 1 && Map.getSpaceMap().get(Map.YELLOW_START).getOccupancy()) {
                return Map.getSpaceMap().get(Map.YELLOW_START).getPiece().getNestId() != nestId;
            } else if (nestId == 2 && Map.getSpaceMap().get(Map.GREEN_START).getOccupancy()) {
                return Map.getSpaceMap().get(Map.GREEN_START).getPiece().getNestId() != nestId;
            } else if (nestId == 3 && Map.getSpaceMap().get(Map.RED_START).getOccupancy()) {
                return Map.getSpaceMap().get(Map.RED_START).getPiece().getNestId() != nestId;
            }
            else{
                return false;
            }
        }
        else {
            int next = position + diceAmount;
            if (next > 47){
                next = next - 48;
            }
            if (Map.getSpaceMap().get(next).getOccupancy() && !isBlockedPiece(position,diceAmount-1,nestId)) {
                return nestId != Map.getSpaceMap().get(next).getPiece().getNestId();
            }
            return false;
        }
    }

    boolean isBlockedPiece(int position, int diceAmount, int nestID) {
        if (position == -1) {
            if (nestID == 0) {
                return Map.getSpaceMap().get(Map.BLUE_START).getOccupancy();
            } else if (nestID == 1) {
                return Map.getSpaceMap().get(Map.YELLOW_START).getOccupancy();
            } else if (nestID == 2) {
                return Map.getSpaceMap().get(Map.GREEN_START).getOccupancy();
            } else if (nestID == 3) {
                return Map.getSpaceMap().get(Map.RED_START).getOccupancy();
            }
        } else {
            for (int i = 0; i < diceAmount; i++, position++) {
                if (position == 47){
                    position = -1;
                }
                if (Map.getSpaceMap().get(position+1).getOccupancy()) {
                    return true;
                }
            }
        }
        return false;
    }







    /**
     * the controller move , use moveSpace and moveToHouse
     * @param moveAmount
     */
    public void move(int moveAmount){
        if (step + moveAmount < 49)
            //Run in the space map
            moveSpace(moveAmount);
        else {
            //reach to the arrival
            if (step == 48){ //at the arrival space
                currentPosition = step;
                moveToHouseDestination(moveAmount);
            }
            else    //at the house destination
                if ( (step -48) == moveAmount)
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

                Space homeSpace = getSpaceMap().get(getStartPosition(nestId)); //reference starting position
                double x = homeSpace.getLayoutX();
                double y = homeSpace.getLayoutY();
                currentPosition = getStartPosition(nestId) ;  //index at the start space
                step = 1;
                //move the piece to said location
                isDeployed = true;  //Piece had already move outside the nest
                updateCurrentPosition(0,x,y);
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
    public void moveToHouseDestination(int moveAmount){
        //start to move into the houseDestination
        if (!isBlocked){
            House hs = getHouseMap().get(step - 49 + getHouseArrival() + moveAmount);
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
        Nest nest = Map.getNestMap().get(nestId);
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

    public void setMove(int move) {
        this.move = move;
    }

    public int getStep(){
        return step;
    }
    public void setHome(boolean home) {
        isHome = home;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public int moveToHouse(Map map, int currentIndex, int idNest, int moveAmount, boolean isBlocked) {
        if (!isBlocked) {
            if (moveAmount == 0) {
                //when the piece is at space arrival
                return currentIndex;
            }
            House hs1 = map.getHouseMap().get(currentIndex + getHouseArrival() + moveAmount);
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

        Nest nest = getNestMap().get(nestId);

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
}
