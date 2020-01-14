/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/

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

    /**
     * save player score to score.txt
     * @throws Exception
     */
    public void savePoint() throws Exception{
        Scanner fileInput = new Scanner(file);
        fileInput.useDelimiter(",|\n"); //player's name and score are separated by "," and "\n" is used to separate from other another player

        //Read score file
        while (fileInput.hasNext()) {
            playerName.add(fileInput.next());
            playerScore.add(fileInput.nextInt());
        }

        fileInput.close();

        //Write to score file
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
            output.write(playerName.get(j) + "," + playerScore.get(j) + "\n");
        }
        output.close();
    }

    /**
     * load the saved score from score.txt and update the player's score
     * @throws Exception
     */
    public void getPoint() throws Exception {
        Scanner fileInput = new Scanner(file);
        fileInput.useDelimiter(",|\n");
        //Read score file
        while (fileInput.hasNext()) {
            playerName.add(fileInput.next());
            playerScore.add(fileInput.nextInt());
        }
        fileInput.close();
        for (int i = 0; i < players.length; i++){
            for (int j = 0; j < playerName.size(); j++) {
                //Only load point to PLAYER
                if (players[i].getName().equals(playerName.get(j)) && players[i].getConnectionStatus() == StaticContainer.ConnectionStatus.PLAYER) {
                    players[i].setPoints(playerScore.get(j));
                    break;
                }
            }
        }

    }
}
