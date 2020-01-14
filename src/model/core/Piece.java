package model.core;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import helper.Map;
import model.Sound;

import static helper.Map.*;
import static helper.StaticContainer.*;
import static javafx.scene.paint.Color.WHITE;


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
    private boolean isClicked;
    Player player;// - get player by nestId - a static function
    private boolean canDeploy = true; // this will be reference by the player later

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
        this.setStroke(WHITE);
        this.setStrokeWidth(2);

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
                if (globalNestId == nestId) {
                    setClicked(true);
                    seq = new SequentialTransition();
                    handleOnClickLogic();
                    updatePoint(gameController);
                }
            }
        });
    }

    //=================================[Move]=====================================

    /**
     * when click on a piece
     */
    void handleOnClickLogic() {
        int initialPosition = this.currentPosition;          // Store the initial position
        if (players[nestId].isRolled()) {                   // Whenever this player is rolled
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
            if (this.getStep() <= 47) {             // For the piece outside home
                // case 1: piece being blocked and not able to kick in the first dice turn
                if (!this.ableToMove(playerMoveAmount,diceTurn) && !this.ableToKick(playerMoveAmount)
                        && this.currentPosition != -1 && diceTurn == 1) {
                    if (ableToMove(diceValue2,diceTurn) || ableToKick(diceValue2)) {        // If the second dice is available to move, swap the dices
                        playerMoveAmount = diceValue2;
                        diceValue2 = diceValue1;
                        diceValue1 = playerMoveAmount;
                    }
                }
                //case 2: not be blocked or able to kick
                if (!this.isBlockedPiece(playerMoveAmount) || this.ableToKick(playerMoveAmount)) {
                    // case 2.1: check for when able to kick
                    if (this.ableToKick(playerMoveAmount)) {            // If it is able to kick
                        int next = 0;
                        if (this.currentPosition != -1 && this.step + playerMoveAmount <= 48) {    // Not go through homepath to check
                            next = this.currentPosition + playerMoveAmount;             // Store the next position for checking
                            if (next > 47) {                                            // the next position 47 is 0
                                next -= 48;
                            }
                            Piece kickedPiece = spaceMap.get(next).getPiece();          // Get the piece in the destination
                            this.kick(kickedPiece);                                     // kick it
                            players[nestId].setPoints(players[nestId].getPoints() + 2); // set point plus
                            players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2); // set lose point
                            spaceMap.get(next).setPiece(null);                          // reset that destination to move
                            spaceMap.get(next).setOccupancy(false);
                        } else if (this.currentPosition == -1 && (diceValue1 == 6 || diceValue2 == 6)){         // Or it is able to deploy
                            next = this.getStartPosition(this.nestId);   //get the piece at start position
                            Piece kickedPiece = spaceMap.get(next).getPiece();
                            this.kick(kickedPiece);
                            players[nestId].setPoints(players[nestId].getPoints() + 2);
                            players[kickedPiece.getNestId()].setPoints(players[kickedPiece.getNestId()].getPoints() - 2);
                            spaceMap.get(next).setPiece(null);
                            spaceMap.get(next).setOccupancy(false);
                        }
                    }
                    //case 2.2: able to move or able to deploy
                    if ((this.currentPosition != -1 || ((playerMoveAmount == 6 || diceValue2 == 6) && diceTurn == 0) || (playerMoveAmount == 6 && diceTurn == 1) )) {
                        if (this.currentPosition == -1) {
                            if (playerMoveAmount != 6 && diceTurn == 0){
                                playerMoveAmount = diceValue2;
                                diceValue2 = diceValue1;
                                diceValue1 = playerMoveAmount;
                            }
                            diceTurn ++;
                        }

                        if (this.step + playerMoveAmount <= 48) {
                            players[nestId].setPoints(players[nestId].getPoints() + this.movePiece(playerMoveAmount));
                            if (initialPosition != -1) {
                                spaceMap.get(initialPosition).setOccupancy(false);
                                spaceMap.get(initialPosition).setPiece(null);
                            }
                            spaceMap.get(this.currentPosition).setOccupancy(true);
                            spaceMap.get(this.currentPosition).setPiece(this);
                        }
                        if (currentPosition == initialPosition) {
                            diceTurn--;
                        }
                    }
                }
                //case 3: piece is blocked on board
                else if ((this.isBlockedPiece(playerMoveAmount) && this.currentPosition != -1)) {
                    diceTurn--; //reset turn
                }
            }
            // If this piece is standing on homepath or inside the house (not nest)
            else if ((!this.blockHome(playerMoveAmount) || (!this.blockHome(diceValue2) && diceTurn == 1 )) && this.step >= 48 && this.step < 48 + 6) {
                if (!this.blockHome(diceValue2) && (diceValue1 < diceValue2 || this.blockHome(diceValue1)) && diceTurn == 1) {          // Take the dice has highest value to use if it is available
                    playerMoveAmount = diceValue2;
                    diceValue2 = diceValue1;
                    diceValue1 = playerMoveAmount;
                }
                players[nestId].setPoints(players[nestId].getPoints() + this.movePiece(playerMoveAmount));                      // Set move with point plus
                if (initialPosition >= 48) {                                                                                    // If this piece is inside home
                    houseMap.get(initialPosition).setOccupancy(false);
                    houseMap.get(initialPosition).setPiece(null);
                }
                else if (initialPosition == this.getStartPosition(nestId) - 1){                                                 // Or it is standing on home path
                    spaceMap.get(initialPosition).setOccupancy(false);
                    spaceMap.get(initialPosition).setPiece(null);
                }
                houseMap.get(this.currentPosition).setOccupancy(true);
                houseMap.get(this.currentPosition).setPiece(this);
            }
            if (currentPosition == initialPosition && this.step >= 48) {                                            // If it is blocked
                diceTurn--;
            }
            //case 4:
            if (this.currentPosition != -1 && !this.ableToMove(diceValue2,diceTurn)                         // If there is no way to go
                    && !this.ableToKick(diceValue2,nestId) && diceTurn == 1 && !this.ableToMoveInHome(diceValue2) && !(diceValue2 == 6 && !this.noPieceAtHome(nestId) && this.ableToDeploy())) {
                diceTurn = 3;
            }
            seq.play();
            //reset player and dice turns
            if (diceTurn >= 2) {                            // If used all dice turns
                nestMap.get(globalNestId).rect.setStrokeWidth(0);
                nestMap.get(globalNestId).circle.setStrokeWidth(0);
                if (diceValue1 == diceValue2) globalNestId--;               // Re roll if there is the same dice value
                int nextTurn = globalNestId + 1;                            // Get the next player id
                if (nextTurn == 4){
                    nextTurn = 0;
                }
                while (players[nextTurn].getConnectionStatus() == ConnectionStatus.OFF){
                    nextTurn++;
                    if (nextTurn == 4){
                        nextTurn = 0;
                    }
                }
                players[nestId].resetCheck();                               // Reset the roll
                diceTurn = 0;

                // BOT MUST ROLL
                nestMap.get(nextTurn).circle.setStroke(nestMustRollColor);

                if (players[nextTurn].getConnectionStatus() == ConnectionStatus.BOT) {              // Auto roll if the next one is a bot
                    Timeline timeline = new Timeline();
                    KeyFrame key = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            turn = 0;
                            diceWork();
                        }
                    });
                    timeline.getKeyFrames().add(key);
                    timeline.play();
                }
                else{
                    turn = 0;                                       // Else allow to roll
                    dice1.setDisable(false);
                    dice2.setDisable(false);
                }
            }
        }
    }

    /**
     * Check for if current piece is able to perform the moveAmount
     * @param moveAmount
     * @return
     */
    public boolean ableToMove(int moveAmount, int diceTurn){
        int check =0;
        for (int i = 0; i <  4; i++){
                if (getNestById(nestId).getPieceList()[i].getStep() <= 48 && getNestById(nestId).getPieceList()[i].getCurrentPosition() != -1) {
                    check ++;
                    if (getNestById(nestId).getPieceList()[i].getStep() == 48) {
                        if (!getNestById(nestId).getPieceList()[i].blockHome(moveAmount)) {
                            return true;
                        }
                    } else if (getNestById(nestId).getPieceList()[i].getStep() + moveAmount <= 48 && !getNestById(nestId).getPieceList()[i].isBlockedPiece(moveAmount)) {
                        return true;
                    }
                }
        }
        return check == 0 && (diceValue1 == 6 || diceValue2 == 6) && diceTurn == 0 && !this.noPieceAtHome(this.nestId);
    }


    /**
     * check whether there is a piece at home or not
     * @param nestId
     * @return
     */
    public boolean noPieceAtHome(int nestId){
        for (int i = 0; i < 4; i ++){
            if (getNestById(nestId).getPieceList()[i].getCurrentPosition() == - 1){
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether it is able to kick (is used for checking in advance)
     */

    public boolean ableToKick(int moveAmount, int nestId){
        Piece piece;
        for (int i =0; i< 4; i++) {
            if(getNestById(nestId).getPieceList()[i].getCurrentPosition() != -1 && getNestById(nestId).getPieceList()[i].getStep() + moveAmount <= 48){
                piece =getNestById(nestId).getPieceList()[i];
                if (piece.ableToKick(moveAmount)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Overload function (is used for checking the specific piece)
     * @param moveAmount
     * @return
     */
    public boolean ableToKick(int moveAmount){
        if (this.currentPosition == -1) { //case piece inside nest
            if (nestId == 0 && spaceMap.get(BLUE_START).getOccupancy()) {
                return spaceMap.get(BLUE_START).getPiece().getNestId() != nestId;
            } else if (nestId == 1 && spaceMap.get(YELLOW_START).getOccupancy()) {
                return spaceMap.get(YELLOW_START).getPiece().getNestId() != nestId;
            } else if (nestId == 2 && spaceMap.get(GREEN_START).getOccupancy()) {
                return spaceMap.get(GREEN_START).getPiece().getNestId() != nestId;
            } else if (nestId == 3 && spaceMap.get(RED_START).getOccupancy()) {
                return spaceMap.get(RED_START).getPiece().getNestId() != nestId;
            }else{
                return false;
            }

        }
        else { // case piece outside of board
            int next = this.currentPosition + moveAmount;
            if (next > 47){
                next = next - 48;
            }
            if (spaceMap.get(next).getOccupancy() && !this.isBlockedPiece(moveAmount-1)) {
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
    public boolean isBlockedPiece(int amount) {
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
            int initial = this.currentPosition;
            if (spaceMap.get(initial).getPiece().getStep() + amount < 49) {
                for (int i = 0; i < amount; i++, initial++) {
                    if (initial == 47) {
                        initial = -1;
                    }
                    if (spaceMap.get(initial + 1).getOccupancy()) {
                        return true;
                    }
                }
            }
        }
            return false;
    }

    /**
     * the controller move , use moveSpace and moveToHouse
     * @param moveAmount
     * @return point
     */
    public int movePiece(int moveAmount){
        if (step + moveAmount < 49)
            //Run in the space map
            movePieceToSpace(moveAmount);
        else {
            //reach to the arrival
            if (step == 48){ //at the arrival space
                movePieceToHouseDestination(moveAmount);
                return moveAmount;
            }
            else {
                //at the house destination
                    //get the position (in display board) + 1 (before postion) + 1 (index in map less than current)
                if (step == 51 || step == 52 || step == 53){    //3-4-5
                    players[nestId].decreaseGetToHouse();
                }
                movePieceToHouseDestination(1);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Check whether there is a block at home or not
     * @param dice
     * @return
     */
    public boolean blockHome(int dice){
        int start = getHouseArrival();
        if (this.step <= start + 5 && this.step > 47) {
            if (this.step == 48) {
                for (int i = start; i < start + dice; i++) {
                    if (houseMap.get(i).getOccupancy()) {
                        return true;
                    }
                }
                return false;
            } else{
                if (dice + start == this.currentPosition + 2) {
                    return houseMap.get(this.currentPosition+1).getOccupancy();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * For checking in advance, check if there is a piece is able to move at home
     * @param diceAmount
     * @return
     */
    public boolean ableToMoveInHome(int diceAmount){
        int start = getHouseArrival();
        for (int i = start; i < start + 5; i++){
            if (houseMap.get(i).getOccupancy() && (i - start) + 2 == diceAmount){
                if (!houseMap.get(i).getPiece().blockHome(diceAmount)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * For checking in advance, check if there is a piece can be deploy
     * @return
     */
    public boolean ableToDeploy(){
        for (int i =0;i <4; i++){
            if (getNestById(nestId).getPieceList()[i].getCurrentPosition() == -1){
                if (!getNestById(nestId).getPieceList()[i].isBlockedPiece(1)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * make the piece run by space and moveAmount
     * @param moveAmount
     */
    // Move a specified circle a certain amount
    private void movePieceToSpace(int moveAmount) {
        if (!isBlocked){
            //deploying
            if (!isDeployed && canDeploy) {
                //Move the piece from nest

                double x = getSpaceX(getStartPosition(nestId));
                double y = getSpaceY(getStartPosition(nestId));
                updatePieceMoving(x,y).play();    //move animation
                Sound.playSound(Sound.DEPLOY);
                currentPosition = getStartPosition(nestId) ;  //index at the start space
                isDeployed = true;  //Piece had already move outside the nest
            }else {
                //Move the piece in space
                // move the piece to said location
                for (int i = 1; i < moveAmount + 1 ; i++) {
                    if (currentPosition + 1 > 47)
                        currentPosition = -1;
                    double x = getSpaceX(currentPosition + 1);
                    double y = getSpaceY(currentPosition + 1);
                    seq.getChildren().addAll(updatePieceMoving(x,y), new PauseTransition(Duration.millis(100)));  //move animation
                    Sound.playSound(Sound.MOVE);
                }
            }
        }
    }

    /**
     * get into the house destination
     * @param moveAmount
     */
    public void movePieceToHouseDestination(int moveAmount){
        //start to move into the houseDestination
        if (!isBlocked){
            SequentialTransition seq = new SequentialTransition();
            for (int i = 0; i < moveAmount ; i++){
                double x = getHouseX(getHouseArrival() + step - 48);     //start at the first and jump to the moveamount and ghe currentt
                double y = getHouseY(getHouseArrival() + step - 48);
                //move the piece to said location
                seq.getChildren().addAll(updatePieceMoving(x,y),new PauseTransition(Duration.millis(100)));
                Sound.playSound(Sound.HOME);
            }
            this.isHouse = true;
            seq.play();
            getToTheTopHouse();
        }
    }

    private TranslateTransition updatePieceMoving(double x, double y ){
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), this);
        tt.setToX(x - getLayoutX());
        tt.setToY(y - getLayoutY());
        //set the piece null when get to house
        if (step > 48){
            setOpacity(0.5);
            setStroke(WHITE);
            setStrokeWidth(5);
        }
        if (step == 48){
            currentPosition = getHouseArrival() - 1;
        }
        pieceIsMoving = true;
        currentPosition ++;
        step ++;
        return tt;
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
    public int getHouseArrival(){
        switch (nestId){
            case 0:{
                return BLUE_HOUSE_1;
            }
            case 1:{
                return YELLOW_HOUSE_1;
            }
            case 2:{
                return GREEN_HOUSE_1;
            }
            case 3: {
                return RED_HOUSE_1;
            }
        }
        return 0;   //return dummy value
    }

    /**
     * to settle piece in the nest with its id
     *
     */
    public void pieceInTheNest(){
        //to set the piece with its id nicely
        int vPos;
        int hPos;
        if (pieceId == 0) {
            hPos = -1;
            vPos = -1;
        } else if (pieceId == 1) {
            hPos = 1;
            vPos = -1;
        } else if (pieceId == 2) {
            hPos = -1;
            vPos = 1;
        } else {
            hPos = 1;
            vPos = 1;
        }
        Nest nest = nestMap.get(nestId);
        setLayoutX(nest.getLayoutX() + Nest.NEST_SIZE / 2 + hPos * 20);  //20 is gap piece
        setLayoutY(nest.getLayoutY() + Nest.NEST_SIZE / 2 + vPos * 20);
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

    public int getStep(){
        return step;
    }

    public void kick(Piece piece){
        piece.kickTransition();
        piece.setCurrentPosition(-1);
        piece.setDeployed(false);
        piece.setStep(0);
        Sound.playSound(Sound.KICK);
    }

    private void kickTransition(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(400), this);
        tt.setToX(0);
        tt.setToY(0);
        tt.play();
        Sound.playSound(Sound.KICK);
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

    private void getToTheTopHouse(){
        if (step == 51 || step == 52 || step == 53 || step == 54){
            //3-4-5-6
            players[nestId].increaseGetToHouse();    //increase by 1
        }
    }
}
