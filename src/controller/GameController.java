package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import model.*;
import helper.Map;
import model.core.Dice;

import static javafx.scene.paint.Color.BLACK;
import static helper.StaticContainer.*;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Sound.THEME;


/**
 * Input listeners
 */

public class GameController implements Initializable, Runnable {
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
    public void run() {
        while (true){
            updatePoint();
            System.out.println("a");
            try {
                Thread.sleep(POLLING_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLanguage();

        // draw board
        Map map = new Map();
        container.setCenter(map);

        for (int i = 0; i < players.length;i++) {
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                nestMap.get(i).setDisplayDisconnected();
            }
        }
        //set sound
        Sound.playSound(THEME);

        //set dice
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        topBar.getChildren().addAll(dice1, dice2);
        setDiceOnClick(dice1, dice2 );

        //update points
        updatePoint();


        //============================[test]============================

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

    void updatePoint(){
        scoreLbBlue.setText(players[0].getPoints()+"");
        scoreLbYellow.setText(players[1].getPoints()+"");
        scoreLbGreen.setText(players[2].getPoints()+"");
        scoreLbRed.setText(players[3].getPoints()+"");
    }



}
