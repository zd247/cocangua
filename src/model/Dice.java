package model;
import java.util.Random;

public class Dice {
    Dice(){}

    int roll(){
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}