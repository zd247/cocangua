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
    @FXML public Label scoreLbBlue;
    @FXML public Label scoreLbYellow;
    @FXML public Label scoreLbGreen;
    @FXML public Label scoreLbRed;
    @FXML private TextField activityLog;    // Update notifications (kick, block etc.)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLanguage();

        // draw board
        Map map = new Map();
        container.setCenter(map);

        Dice dice1 = new Dice();
        Dice dice2 = new Dice();

        setDiceOnClick(dice1, dice2 );

        for (int i = 0; i < players.length;i++) {
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                nestMap.get(i).setDisplayDisconnected();
            }
        }

        /**===========================[Test code goes here]===========================*/

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName());
        }


        /**===========================[End of view.test code]===========================*/
        Sound.playSound(THEME); // play sound
        // this logic can be moved to static class.
        topBar.getChildren().addAll(dice1, dice2);

        // test index
        System.out.println(spaceMap.size() + " " + houseMap.size());

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
