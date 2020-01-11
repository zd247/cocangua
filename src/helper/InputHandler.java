package helper;

import static helper.StaticContainer.*;


public class InputHandler implements Runnable {
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < players.length;i++) {
                System.out.println(players[i].getName());
            }
            System.out.println("========================");

            //update per interval
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateScoreLabel () {

    }
}
