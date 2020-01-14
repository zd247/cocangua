package model;

import helper.StaticContainer;

import java.util.ArrayList;
import java.util.Scanner;

import static helper.StaticContainer.players;

public class Score {
    private static java.io.File file = new java.io.File("score.txt");

    private ArrayList<String> playerName;
    private ArrayList<Integer> playerScore;
    public Score () {
        playerName = new ArrayList<>();
        playerScore = new ArrayList<>();
    }

    public void savePoint() throws Exception{
        Scanner fileInput = new Scanner(file);
        fileInput.useDelimiter(",|\n");

        while (fileInput.hasNext()) {
            playerName.add(fileInput.next());
            playerScore.add(fileInput.nextInt());
        }

        fileInput.close();

        java.io.PrintWriter output = new java.io.PrintWriter(file);
        for (model.core.Player player : players) {
            System.out.println(player.getConnectionStatus());
            if (player.getConnectionStatus() == StaticContainer.ConnectionStatus.PLAYER) {
                System.out.println("what the ");
                for (int j = 0; j < playerName.size(); j++) {
                    if (playerName.get(j).equals(player.getName())) {
                        int newScore = player.getPoints();
                        playerScore.set(j, newScore);
                        break;
                    } else if (j == playerName.size() - 1) {
                        playerName.add(player.getName());
                        playerScore.add(player.getPoints());
                        break;
                    }
                }
                if (playerName.size() == 0)
                    output.write(player.getName() + "," + player.getPoints() + "\n");
            }
        }
        for (int j = 0; j < playerName.size(); j++) {
            output.write(playerName.get(j) + "," + playerScore.get(j) + '\n');
        }
        output.close();
    }

    public void getPoint() throws Exception {
        Scanner fileInput = new Scanner(file);
        fileInput.useDelimiter(",|\n");

        while (fileInput.hasNext()) {
            playerName.add(fileInput.next());
            playerScore.add(fileInput.nextInt());
        }
        fileInput.close();
        for (int i = 0; i < players.length; i++){
            for (int j = 0; j < playerName.size(); j++) {
                if (players[i].getName().equals(playerName.get(j)) && players[i].getConnectionStatus() == StaticContainer.ConnectionStatus.PLAYER) {
                    players[i].setPoints(playerScore.get(j));
                    break;
                }
            }
        }

    }
}
