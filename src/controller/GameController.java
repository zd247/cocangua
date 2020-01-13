package controller;

import helper.InputHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.*;
import helper.Map;
import model.core.Dice;


import static helper.StaticContainer.*;


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
    public ChoiceBox<String> languageBox;

    // Text fields that needs updating
    @FXML private Label nameLbBlue;
    @FXML private Label nameLbYellow;
    @FXML private Label nameLbGreen;
    @FXML private Label nameLbRed;
    @FXML public Label scoreLbBlue;
    @FXML public Label scoreLbYellow;
    @FXML public Label scoreLbGreen;
    @FXML public Label scoreLbRed;
    @FXML private TextField activityLog;    // Update notifications (kick, block etc.)



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLanguage();
        updateName();

        // draw board
        Map map = new Map();
        container.setCenter(map);

        dice1 = new Dice();
        dice2 = new Dice();

        setDiceOnClick();

        topBar.getChildren().addAll(dice1, dice2);

        for (int i = 0; i < players.length;i++) {
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                nestMap.get(i).setDisplayDisconnected();
            }
        }
        int checker =0;
        for (int i =3; i >=0; i--){
            if (players[i].getPointForTurn() >= firstTurn){
                firstTurn = players[i].getPointForTurn();
                checker = i;
            }
        }
        globalNestId = checker - 1;
        nestMap.get(globalNestId +1).rect.setStroke(Color.SILVER);
        nestMap.get(globalNestId + 1).rect.setStrokeWidth(10);
        //set sound
        Sound.playSound(THEME);



        //============================[test]============================
        InputHandler inputHandler = new InputHandler();
        new Thread(inputHandler).start();

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



    private void chooseLanguage(){
        changeChoiceBoxInGame(this);
        languageBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Tiếng Việt")){
                    language.setLanguage("vi","VN");
                    loadLanguage();
                }
                else{
                    language.setLanguage("en","US");
                    System.out.println(language.getLocale());
                    loadLanguage();
                }
            }
        });
    }

    public void loadLanguage(){

    }

    /**===========================[Test code goes here]===========================*/

    private void updateName(){
        nameLbBlue.setText(players[0].getName());
        nameLbYellow.setText(players[1].getName());
        nameLbGreen.setText(players[2].getName());
        nameLbRed.setText(players[3].getName());
    }
    /**===========================[End of view.test code]===========================*/
    void updatePoint(){
        scoreLbBlue.setText(players[0].getPoints()+"");
        scoreLbYellow.setText(players[1].getPoints()+"");
        scoreLbGreen.setText(players[2].getPoints()+"");
        scoreLbRed.setText(players[3].getPoints()+"");
    }

}
