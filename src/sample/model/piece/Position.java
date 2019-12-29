package sample.model.piece;

public class Position {
    private int x;
    private int y;
    private static int passX = 6;
    private static int passY = 6;

    public boolean equal(Position p){
        return this.x == getX() && this.y == getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isLargerX(int movement){
        int temp;
        if (x + movement > passX){
            //if total of current X and movement is larger than the boundary
            //at first passX will be set 6
            temp = x + movement - passX;
            x = x + movement - temp;
            y = y + temp;

            if (passX == 3){
                passX = 6;
            }
            else passX = 3;
            return true;
        }
        return false;
    }

    public boolean isLargerY(int movement){
        int temp ;
        if (y + movement > passY){
            //if total of current Y and movement is larger than the boundary
            //at first passY will be set 6
            temp = y + movement - passY;
            y = y + movement - temp;
            x = x + temp;

            if (passY == 3){
                passY = 6;
            }

            else passY = 3;
            return true;
        }
        else {
            x += movement;
            return false;
        }
    }
}