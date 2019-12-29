package sample.model.piece;

public class Position {
    private int x;
    private int y;
    private static int passLength = 6;
    private int count = 0;


                        //=================================[]=====================================

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equal(Position p){
        return this.x == getX() && this.y == getY();
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    boolean ToSwitch(int movement, int x, int y){
        //X or y maybe 0
        if (x + movement > passLength || y + movement > passLength){
            //The total coordiate of X, or Y is higher than the passlength
            int temp ;
            if (y == 0){
                temp = x + movement - passLength;
                this.x = x + movement - temp;//Calculate x moves how far
                this.y = y + temp;//then the remaining will be to y
            }
            else {
                temp = y + movement - passLength;
                this.x = y + temp;
                this.y = x + movement - temp;
            }
            count++;
            //the count will be increased by 1, when the piece increased

            if (count == 2){
                //piece has to pass through 2 times of 6
                passLength = 3;
            }

            if (passLength == 3){
                passLength = 6;
                count = 0;
            }
            return true;
        }
        else {
            this.x += x + movement;
            this.y += y + movement;
            return false;
        }

    }
}