package model;
import java.util.Random;

// Dice class
public class Dice {
    private int face;   // The rolled number (1 - 6)

    // Constructor
    public Dice(){
        int face = 0;
        assert (face >= 0 && face <= 6);
    }

    // Roll function (1 - 6)
    public void roll(){
        Random random = new Random();
        this.face = random.nextInt(6) + 1;
    }

    // Return the rolled number
    public int getFace(){
        return face;
    }
}