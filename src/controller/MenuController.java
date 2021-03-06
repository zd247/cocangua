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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.core.Player;

import static helper.StaticContainer.*;
import static helper.LayoutContainer.*;

/**
 * Handle start game logic that determine which type of gameplay (LAN or Network)
 */
public class MenuController implements Initializable {

    public ChoiceBox<String> languageBox;

    public Label TitleName;

    @FXML
    GridPane playerFieldContainer;

    @FXML
    public Button startBtn; //finalize, populate and move on, called main

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate the player container with default value
        players = new Player[4];
        for (int i = 0; i < players.length;i++) {
            players[i] = new Player(i);
        }
        // populate the player container display field
        for (int i = 0; i < playerFieldContainer.getRowCount();i++) {
            for (int j = 0; j < playerFieldContainer.getColumnCount();j++){
                int id = 0;
                if (i == 0 && j == 1){id = 1;}
                if (i == 1) {
                    if (j == 0) id = 3;
                    if (j == 1) id = 2;
                }
                Player player = new Player(id);
                players[id] = player; // register player to the client player container
                playerFieldContainer.add(player, i, j);
            }
        }

        chooseLanguage();
    }

    /**
     * Choosing language
     */
    private void chooseLanguage() {
        changeChoiceBoxInMenu(this);

        languageBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Tiếng Việt")){
                    language.setLanguage("vi","VN");
                    System.out.println(language.getLocale());
                    loadLanguage();
                }
                else{
                    language.setLanguage("en","US");
                    loadLanguage();
                }
            }
        });
    }

    /**
     * Load the language
     */
    public void loadLanguage(){
        TitleName.setText(language.getTitleName());
        startBtn.setText(language.getStartButton());

        for (Player p : players) {
            p.getAddText().setText(language.getAddPlayerMessage());
        }

    }
}

