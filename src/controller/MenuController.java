package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Handle start game logic that determine which type of gameplay (LAN or Network)
 */
public class MenuController implements Initializable {
    @FXML
    GridPane playerContainer;

    @FXML
    public Button startBtn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int id = 0;
        // populate the player container
        for (int i = 0; i < playerContainer.getRowCount();i++) {
            for (int j = 0; j < playerContainer.getColumnCount();j++){
                // add 4 create buttons
                CreateButton addButton = new CreateButton("Player " + id, id);
                id++;
                playerContainer.add(addButton, i, j);

            }
        }

    }

    class CreateButton extends Button {
        int nestId;
        CreateButton(String name, int nestId){
            super(name);
            this.nestId = nestId;

            //register event handler
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //create new player with this nest id.
                    System.out.println(getNestId());
                }
            });
        }

        public int getNestId(){
            return this.nestId;
        }


    }



}

