package sample.model;

import java.util.Random;

class Die {
    private int id;

    private int value;
    private Random random;

    public Die(int id){
        this.id=id;
        this.value = 1;
        random = new Random();
    }

    public int getId() {
        return id;
    }

    public void diceRoll() {
            setValue(random.nextInt(6) + 1);
    }

    private void setValue(int side){
        value = side;
    }

    private int getValue(){
        return value;
    }
}