package model;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.Random;

// Dice class
public class Dice extends ImageView {
    private int[] values = new int[2];   // The rolled number (1 - 6)

    /* DOESN"T DISPLAY */
    File file = new File("view/images/diceFace1.jpg");
    Image image = new Image(file.toURI().toString(), 50, 50, false, false);

    // Constructor
    public Dice(){
        this.setImage(this.image);
        //Register event handler
        this.setOnMouseClicked(mouseEvent -> {
            roll();
            //Future implementation: Co-routine to wait until the rolling animation finished then update
            update();
        });
    }

    //this function is called inside event handlers
    private void update() {
        //update attributes..

        //update view display
    }


    // Roll function (1 - 6)
    private void roll(){
        Random random = new Random();
        for (int i = 0; i < values.length;i++) {
            this.values[i] = random.nextInt(6) + 1;
        }

    }

    // total moves
    public int total() {
        return values[0] + values[1];
    }

}