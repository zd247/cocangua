package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import model.*;

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
    private Button pauseBtn;
    @FXML
    private ToggleButton soundBtn;
    @FXML
    private ChoiceBox<String> languageBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map map = new Map();
        // draw board
        container.setCenter(map);

        /**===========================[Test code goes here]===========================*/

//        for (int i = 0; i < players.size(); i++) {
////            System.out.println(players.get(i).getName());
//            //TODO:
//            //get piece using player
//
//            //get piece using nest
//
//            //get piece
//        }

        Sound.playSound(THEME); // play sound
        Dice dice1 = new Dice();    // add dices
        Dice dice2 = new Dice();
        topBar.getChildren().addAll(dice1, dice2);

        /**===========================[End of view.test code]===========================*/
    }



    @FXML
    private void setSound(ActionEvent event) {
        if (Sound.isMute == false) {
            Sound.isMute = true;
            Sound.playSound(THEME);
        } else {
            Sound.isMute = false;
            Sound.playSound(THEME);
        }
    }

}

