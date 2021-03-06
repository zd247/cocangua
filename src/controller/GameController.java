/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/

package controller;

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
import javafx.scene.text.Text;
import javafx.util.Duration;
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

        for (int i = 0; i < players.length;i++) {               // Set the nest color to silver if there is an offline player
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                nestMap.get(i).setDisplayDisconnected();
            }
        }
        gameController.activityLog.setText(language.getStartButton());

        int checker = 0;
        for (int i =3; i >= 0; i--){
            if (players[i].getPointForTurn() >= firstTurn && players[i].getConnectionStatus() != ConnectionStatus.OFF){     // Get the first player's turn for whom has the highest value when rolling before starting game
                firstTurn = players[i].getPointForTurn();
                checker = i;
            }
        }

        turn = 0;           //reset the turn
        globalNestId = checker - 1;

        // INITIAL MUST ROLL INDICATOR FOR FIRST ROUND
        nestMap.get(globalNestId +1).circle.setStroke(nestMustRollColor);

        if (players[globalNestId + 1].getConnectionStatus() == ConnectionStatus.BOT) {  // If there is a bot's turn
            dice1.setDisable(true);
            dice2.setDisable(true);
            Timeline timeline = new Timeline();
            KeyFrame key = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    diceWork();
                }           //Auto rolling
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

    /**
     *    Turn on/off the sound
      */
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

    /**
     * Choosing language
     */
    private void chooseLanguage(){
        changeChoiceBoxInGame(this);
        languageBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Tiếng Việt")){
                    language.setLanguage("vi","VN");
                }
                else{
                    language.setLanguage("en","US");
                    System.out.println(language.getLocale());
                }
            }
        });
    }

    /**
     * update name if there is a not a offline player
     */
     private void updateName(){
        nameLbBlue.setText(players[0].getName());
        nameLbYellow.setText(players[1].getName());
        nameLbGreen.setText(players[2].getName());
        nameLbRed.setText(players[3].getName());
    }


}
