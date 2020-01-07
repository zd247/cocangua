package controller;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Player;
import static statics.StaticContainer.*;

/**
 * Handle start game logic that determine which type of gameplay (LAN or Network)
 */
public class MenuController implements Initializable {
    @FXML
    GridPane playerFieldContainer;

    @FXML
    public Button startBtn; //finalize, populate and move on, called main



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // populate the player container
        for (int i = 0; i < playerFieldContainer.getRowCount();i++) {
            for (int j = 0; j < playerFieldContainer.getColumnCount();j++){
                int id = 0;
                if (i == 0 && j == 1){id = 1;}
                if (i == 1) {
                    if (j == 0) id = 3;
                    if (j == 1) id = 2;
                }
                PlayerField addPlayer = new PlayerField(id); // nestId
                playerFieldContainer.add(addPlayer, i, j);

                //add bot button

            }
        }


    }

    /**
     * Game menu button to create new player
     */
    class PlayerField extends Pane {
        int nestId;

        Text addText = new Text("Click on the pane to add new player...");


        PlayerField(int nestId){
            this.nestId = nestId;

            //--------display
            this.getChildren().add(addText);
            addText.setLayoutX(70);
            addText.setLayoutY(50);

            VBox playerDisplayVBox = new VBox();
            this.getChildren().add(playerDisplayVBox);


            final boolean[] isCreated = {false};
            //register event handler
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (!isCreated[0]){
                        Player player = createPlayer(nestId);
                        players.put(nestId, player); // register player to its global container.

                        //display logic: create VBox, switch here then add it inside a GridPane node
                        TextField textField = new TextField();
                        player.setName(textField.getText());
                        ToggleButton toggler = new ToggleButton("BOT");
                        player.setConnectionStatus(toggler.isSelected());

                        playerDisplayVBox.getChildren().addAll(textField, toggler);
                        isCreated[0] = true;

                        //invisible at end of logic
                        addText.setVisible(false);


                        //debug code:
                        System.out.println(player.getName());
                    }
                }
            });


        }
    }



}

