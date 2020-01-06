package model;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

// Dice class
public class Dice extends ImageView {
    Image diceFace = new Image("images/dice1.png");

    // Constructor
    public Dice() {
        setFitWidth(85);
        setFitHeight(85);
        setImage(diceFace);

        // Slight "jump" effect when hovered
        this.setOnMouseEntered(hover -> {
            this.setTranslateY(-5);
        });

        this.setOnMouseExited(endHover -> {
            this.setTranslateY(0);
        });

        // Roll when clicked on
        this.setOnMouseClicked(click -> this.roll());
    }

    // Roll a dice and return rolled number
    int roll() {
        Random rand = new Random();
        int num = rand.nextInt(6) + 1;

        // Play roll animation
        RotateTransition rt = new RotateTransition(Duration.millis(200), this);
        rt.setByAngle(360);
        rt.setAutoReverse(true);
        rt.setCycleCount(3);
        rt.setAxis(Rotate.Y_AXIS);
        rt.play();  // Play roll animation
        Sound.playSound(Sound.ROLL);    // Play roll sound

        // Set new diceFace image after roll
        rt.setOnFinished(finishRoll -> {
            switch (num) {
                case 1: this.setImage(new Image("images/dice1.png")); break;
                case 2: this.setImage(new Image("images/dice2.png")); break;
                case 3: this.setImage(new Image("images/dice3.png")); break;
                case 4: this.setImage(new Image("images/dice4.png")); break;
                case 5: this.setImage(new Image("images/dice5.png")); break;
                case 6: this.setImage(new Image("images/dice6.png")); break;
            }
        });
        return num;
    }
}
