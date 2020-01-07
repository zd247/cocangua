package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import model.*;
import static statics.StaticContainer.*;

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
    private ChoiceBox<String> languageBox;

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
        Map map = new Map();
        // draw board
        container.setCenter(map);

        /**===========================[Test code goes here]===========================*/

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName());
        }


        Sound.playSound(THEME); // play sound
        // this logic can be moved to static class.
        Dice dice1 = new Dice();    // add dices
        Dice dice2 = new Dice();
        topBar.getChildren().addAll(dice1, dice2);

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

}

