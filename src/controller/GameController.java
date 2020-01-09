package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import model.*;

import static javafx.scene.paint.Color.BLACK;
import static statics.StaticContainer.*;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Sound.THEME;


/**
 * Input listeners
 */

public class GameController implements Initializable {
    public Button button;
    @FXML
    private BorderPane container;
    @FXML
    private HBox topBar;
    @FXML
    private HBox bottomBar;
    @FXML
    private ToggleButton pauseBtn;
    @FXML
    private ToggleButton soundBtn;
    @FXML
    private ChoiceBox<String> languageBox;


    Dice dice1 = new Dice();    // add dices
    Dice dice2 = new Dice();
    int turn = 0;
    int id = -1;
    int moveAmount1 = 0;
    int moveAmount2 = 0;
    Language language = new Language("en","US");
    // Text fields that needs updating
    @FXML private Label nameLbBlue;
    @FXML private Label nameLbYellow;
    @FXML private Label nameLbGreen;
    @FXML private Label nameLbRed;
    @FXML private Label scoreLbBlue;
    @FXML private Label scoreLbYellow;
    @FXML private Label scoreLbGreen;
    @FXML private Label scoreLbRed;
    @FXML private TextField activityLog;    // Update notifications (kick, block etc.)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLanguage();
        Map map = new Map();
        // draw board
        container.setCenter(map);
        updatePoint();
        rollDice();

        /**===========================[Test code goes here]===========================*/

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName());

            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                Map.getNestMap().get(i).setDisplayDisconnected();
            }
        }
        for (int i = 0; i < players.length; i++) {
            Nest nest = Map.getNestMap().get(i);
            var p_move = new Object() {
                int moveAmount = 0;
            };
            var nest_counter = new Object() {
                int count = 0;
            };
            for(int pieceID = 0; pieceID <4; pieceID++) {
                int id_Nest = i; //nestID
                int finalPieceId = pieceID;
                Piece piece = nest.getPieceList()[pieceID];
                piece.setOnMouseClicked(event -> {
                    int initial = piece.getCurrentPosition();
                    if (piece.getCurrentPosition() != -1) {
                        initial = piece.getCurrentPosition();
                    }

                    if(players[id_Nest].isRolled()) {
                        if (id == id_Nest) {
                            if (nest_counter.count == 0) {
                                p_move.moveAmount = moveAmount1;
                                if (piece.getCurrentPosition() != -1) {
                                    nest_counter.count = 1;
                                }
                            } else if (nest_counter.count == 1) {
                                p_move.moveAmount = moveAmount2;
                                if (piece.getCurrentPosition() != -1) {
                                    nest_counter.count = 2;
                                }
                            }
                            if (!able_To_Move(id_Nest,p_move.moveAmount) && !able_To_Kick(piece.getCurrentPosition(),p_move.moveAmount,id_Nest)  && piece.getCurrentPosition()!= -1 && nest_counter.count == 1){
                                if (able_To_Move(id_Nest,moveAmount2) || able_To_Kick(piece.getCurrentPosition(),moveAmount2,id_Nest)){
                                    p_move.moveAmount = moveAmount2;
                                    moveAmount2 = moveAmount1;
                                    moveAmount1 = p_move.moveAmount;
                                    System.out.println("dzo");
                                }
                            }
                            if (!isBlockedPiece(piece.getCurrentPosition(), p_move.moveAmount, piece.getNestId()) || able_To_Kick(piece.getCurrentPosition(), p_move.moveAmount, id_Nest)) {
                                if (able_To_Kick(piece.getCurrentPosition(), p_move.moveAmount, id_Nest)){
                                    int next = 0;
                                    if (piece.getCurrentPosition() != -1 && piece.getStep() + p_move.moveAmount <= 48) {
                                        next = piece.getCurrentPosition() + p_move.moveAmount;
                                        if (next > 47){
                                            next -= 48;
                                        }
                                    } else {
                                        if (canDeploy(id_Nest,moveAmount1,moveAmount2)) {
                                            next = piece.getStartPosition(piece.getNestId());   //get the piece at start position
                                        }
                                    }
                                    Piece pieceKicked = Map.getSpaceMap().get(next).getPiece();
                                    piece.kick(pieceKicked);
                                    players[id_Nest].setPoints(players[id_Nest].getPoints() + 2);   //increase points by 2
                                    players[pieceKicked.getNestId()].setPoints(players[pieceKicked.getNestId()].getPoints() -2);
                                    //decrease point by 2
                                    Map.getSpaceMap().get(next).setOccupancy(false);
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
                                    players[id_Nest].setPoints(players[id_Nest].getPoints() + piece.move(p_move.moveAmount));
                                    //store points
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
                            else if (isBlockedPiece(piece.getCurrentPosition(), p_move.moveAmount, piece.getNestId()) && piece.getCurrentPosition()!= -1){
                                nest_counter.count--;
                            }
                            if (piece.getCurrentPosition()!= -1 && !able_To_Move(id_Nest,moveAmount2)&& !able_To_Kick(piece.getCurrentPosition(),moveAmount2,id_Nest) && nest_counter.count == 1){
                                nest_counter.count = 3;
                            }
                            System.out.println(id_Nest + " " + nest_counter.count);
                            if (nest_counter.count >= 2) {
                                if (moveAmount1 == moveAmount2) {
                                    id--;
                                }
                                turn = 0;
                                players[id_Nest].resetCheck();
                                nest_counter.count = 0;
                            }
                        }
                    }
                    updatePoint();
                });
            }
        }



        Sound.playSound(THEME); // play sound
        // this logic can be moved to static class.
        topBar.getChildren().addAll(dice1, dice2);

        // test index
        System.out.println(Map.getSpaceMap().size() + " " + Map.getHouseMap().size());

        Circle c = new Circle(12);
        c.setFill(BLACK);

        // test move
        map.getChildren().add(c);
        double x = map.getHouseX(Map.RED_HOUSE_1 + 3);
        double y = map.getHouseY(Map.RED_HOUSE_1 + 3);
        //double x = map.getSpaceX(Map.BLUE_ARRIVAL);
        //double y = map.getSpaceY(Map.BLUE_ARRIVAL);
        c.setLayoutX(x);
        c.setLayoutY(y);


        /**===========================[End of view.test code]===========================*/
    }


    @FXML
    private void setSound(ActionEvent event) {
        if (!Sound.isMute) {
            Sound.isMute = true;
            Sound.playSound(THEME);
        } else {
            Sound.isMute = false;
            Sound.playSound(THEME);
        }
    }

    public void rollDice() {
        dice1.setOnMouseClicked(event -> {
            System.out.println(players.length);
            if (turn == 0) {
                turn = 1;
                if (id >= (players.length - 1)) {
                    id = 0;
                } else {
                    id++;
                }
                if (id != -1 && players[id].getConnectionStatus() == ConnectionStatus.OFF){
                    while (!(players[id].getConnectionStatus() == ConnectionStatus.PLAYER)) {
                        id++;
                        if (id > (players.length - 1)) {
                            id = 0;
                        }
                    }
                }
                moveAmount1 = dice1.roll();
                moveAmount2 = dice2.roll();
                //Roll dice here, wilasdasd
                for (int i = 0; i < players.length; i++) {
                    if (i != id) {
                        Map.getNestMap().get(i).rect.setStrokeWidth(0);
                        players[i].resetCheck();;
                    }
                    else{
                        Map.getNestMap().get(i).rect.setStroke(BLACK);
                        Map.getNestMap().get(i).rect.setStrokeWidth(10);
                        players[i].rolled();
                    }
                }
                if (all_At_Home(id) && moveAmount1 != 6 && moveAmount2 != 6) {
                    players[id].resetCheck();
                    if (moveAmount1 == moveAmount2) {
                        id--;
                    }
                    turn = 0;
                }
                else if (!able_To_Move(id, moveAmount1) && !able_To_Move(id,moveAmount2) && !canDeploy(id,moveAmount1,moveAmount2) && !able_To_Kick(moveAmount1,id) &&!able_To_Kick(moveAmount2,id)){
                    players[id].resetCheck();
                    if (moveAmount1 == moveAmount2) {
                        id--;
                    }
                    turn =0;
                }
                System.out.println(!able_To_Move(id, moveAmount1));
                System.out.println(!able_To_Move(id,moveAmount2));
                System.out.println(!canDeploy(id,moveAmount1,moveAmount2));
                System.out.println(!able_To_Kick(moveAmount1,id));
                System.out.println(!able_To_Kick(moveAmount2,id));
                System.out.println("--------------");
            }
        });
    }

    public boolean canDeploy(int nestID, int dice1, int dice2){
        if(dice1 == 6 || dice2 == 6) {
            for (int i = 0; i < 4; i ++){
                if (Map.getNestMap().get(nestID).getPieceList()[i].getCurrentPosition() == -1){
                    return true;
                }
            }
        }
        return false;
    }

    boolean able_To_Move(int nestId, int dices){
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

    boolean able_To_Kick(int position, int diceAmount, int nestId){
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

    boolean able_To_Kick(int diceAmount, int nestId){
        for (int i = 0; i< 47; i++){
            if (Map.getSpaceMap().get(i).getOccupancy()){
                if(Map.getSpaceMap().get(i).getPiece().getNestId() == nestId){
                    if (able_To_Kick(i,diceAmount,nestId)){
                        return true;
                    }
                }
            }
        }
        return false;
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
                /*
                if(position > 47){
                    return false;
                }
                 */
                if (Map.getSpaceMap().get(position+1).getOccupancy()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isBlockedPiece(int dice, int nestID){
        int start = 0;
        if (nestID == 0) {
            start = Map.BLUE_HOUSE_1;
        } else if (nestID == 1) {
            start = Map.YELLOW_HOUSE_1;
        } else if (nestID == 2) {
            start = Map.GREEN_HOUSE_1;
        } else if (nestID == 3) {
            start = Map.RED_HOUSE_1;
        }
        for (int i = start; i < start + dice; i++){
            if (Map.getSpaceMap().get(i).getOccupancy()){
                return true;
            }
        }
        return false;
    }


    boolean all_At_Home(int nestId){
        for (int i = 0; i <=  47; i++){
            if( Map.getSpaceMap().get(i).getOccupancy())
            {
                if (Map.getSpaceMap().get(i).getPiece().getNestId() == nestId){
                    return false;
                }
            }
        }
        return true;
    }

    void updatePoint(){
        scoreLbBlue.setText(players[0].getPoints()+"");
        scoreLbYellow.setText(players[1].getPoints()+"");
        scoreLbGreen.setText(players[2].getPoints()+"");
        scoreLbRed.setText(players[3].getPoints()+"");
    }

    private void chooseLanguage(){
        ObservableList<String> availableChoices = FXCollections.observableArrayList( "English","Tiếng Việt");
        languageBox.setItems(availableChoices);
        languageBox.setValue("English");
        languageBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Tiếng Việt")){
                    language.setLanguage("vi","VN");
                    loadLangue();
                }
                else{
                    language.setLanguage("en","US");
                    loadLangue();
                }
            }
        });
    }

    private void loadLangue(){

    }
}

