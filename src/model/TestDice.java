package model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

// TEST DICE: Button with background image
public class TestDice extends Button {

    TestDice() {
        this.setPrefSize(80, 80);

        this.setBackground(
                new Background(new BackgroundImage(
                new Image("view/images/diceFace1.jpg"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(50, 50, false, false, true, false))));

        // TEST HOVER EFFECT
        this.setOnMouseEntered(hover -> this.setStyle("-fx-border-color: BLUEVIOLET;" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2"));


        this.setOnMouseExited(endHover -> this.setStyle(""));
    }
}
