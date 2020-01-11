package model.core;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import static helper.StaticContainer.*;

/**
 * Game menu button to create new player
 */
public class PlayerField extends Pane {
    int nestId;
    boolean isClickedOn;
    //display
    Text addText = new Text("Click on the pane to add new player...");
    VBox playerDisplayVBox = new VBox();
    TextField textField = new TextField();
    CheckBox toggler = new CheckBox("BOT");


    public PlayerField(int nestId){
        this.nestId = nestId;

        this.getChildren().add(addText);
        addText.setLayoutX(70);
        addText.setLayoutY(50);

        //Set default layout
        this.getChildren().add(playerDisplayVBox);
        this.setBackground(new Background(new BackgroundFill(getColorByNestId(nestId), CornerRadii.EMPTY, Insets.EMPTY)));
        textField.setText("Player " + nestId);

        //Set default value when first clicked
        isClickedOn = false;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isClickedOn){
                    Player player = createPlayer(nestId); //default name and status init
                    playerDisplayVBox.getChildren().addAll(textField, toggler);
                    isClickedOn = true;

                    //invisible at end of logic
                    addText.setVisible(false);

                    players[nestId] = player; // register player to its global container (replace the default)
                }
            }
        });
    }

    public TextField getTextField() {
        return textField;
    }


    public CheckBox getToggler() {
        return toggler;
    }

    public boolean isClickedOn() {
        return isClickedOn;
    }
}
