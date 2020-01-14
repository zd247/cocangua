package controller;

import helper.InputHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import model.*;
import helper.Map;
import model.core.Dice;


import static helper.StaticContainer.*;
import static helper.LayoutContainer.*;


import java.net.URL;
import java.util.ResourceBundle;

import static model.Sound.THEME;


/**
 * Input listeners
 */

public class GameController implements Initializable {
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
    @FXML public Text activityLog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Sound.isMute = false;
        chooseLanguage();

        // draw board
        Map map = new Map();
        container.setCenter(map);

        updatePoint(this);

        dice1 = new Dice();
        dice2 = new Dice();

        setDiceOnClick();

        topBar.getChildren().addAll(dice1, dice2);

        for (int i = 0; i < players.length;i++) {
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                nestMap.get(i).setDisplayDisconnected();
            }
        }
        int checker = 0;
        for (int i =3; i >= 0; i--){
            if (players[i].getPointForTurn() >= firstTurn){
                firstTurn = players[i].getPointForTurn();
                checker = i;
            }
        }

        turn = 0;
        globalNestId = checker - 1;

        // INITIAL MUST ROLL INDICATOR FOR FIRST ROUND
        nestMap.get(globalNestId +1).circle.setStroke(nestMustRollColor);

        if (players[globalNestId + 1].getConnectionStatus() == ConnectionStatus.BOT) {
            dice1.setDisable(true);
            dice2.setDisable(true);
            Timeline timeline = new Timeline();
            KeyFrame key = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    diceWork();
                }
            });
            timeline.getKeyFrames().add(key);
            timeline.play();
        }
        //set sound
        Sound.playSound(THEME);


        //============================[test]============================

        //test client-server
        updateName();
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
        loadLanguage();
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
        gameController.activityLog.setText(language.getStartButton());
        //change langauge of default 
        for (int i = 0; i< players.length; i++){
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF){
                switch (i){
                    case 0:
                        nameLbBlue.setText(language.getDefault());
                        break;
                    case 1:
                        nameLbYellow.setText(language.getDefault());
                        break;
                    case 2:
                        nameLbGreen.setText(language.getDefault());
                        break;
                    case 3:
                        nameLbRed.setText(language.getDefault());
                        break;
                }
            }
        }
    }

    private void updateName(){
        nameLbBlue.setText(players[0].getName());
        nameLbYellow.setText(players[1].getName());
        nameLbGreen.setText(players[2].getName());
        nameLbRed.setText(players[3].getName());
    }


}
