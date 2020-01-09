package controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ResourceBundle;

import model.Language;
import model.PlayerField;
import static statics.StaticContainer.*;



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

    private Language language = new Language("en", "US");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLanguage();
        //populate the player container with default value
        for (int i = 0; i < players.length;i++) {
            players[i] = createPlayer(i);
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
                PlayerField addPlayerField = new PlayerField(id); // nestId
                playerFields[id] = addPlayerField;
                playerFieldContainer.add(addPlayerField, i, j);
            }
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
        TitleName.setText(language.getTitleName());
        startBtn.setText(language.getStartButton());
    }
}

