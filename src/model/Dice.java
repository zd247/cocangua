package model;
import java.util.Random;

public class Dice {
    private int face;

    public Dice(){
        int face = 0;
        assert (face >= 0 && face <= 6);
    }

    public void roll(){
        Random random = new Random();
        this.face = random.nextInt(6) + 1;
    }
}