import controller.GameController;
import controller.MenuController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import helper.StaticContainer;
import model.Sound;
import model.core.Player;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static helper.StaticContainer.*;

public class Main extends Application {

    public Label notifier;
    static java.io.File file = new java.io.File("score.txt");

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader menu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
        FXMLLoader game = new FXMLLoader(getClass().getResource("view/game.fxml"));

        Parent menuDisplay = menu.load(); // load main menu

        MenuController menuController = menu.getController();

        //TODO: go to main game, if no players is null then the game plays itself
        menuController.startBtn.setOnAction(actionEvent -> {
            // Pre-process the static array of Player
            for (int i = 0 ; i < players.length;i++){
                if (players[i].isClickedOn()){
                    players[i].setConnectionStatus(players[i].getToggler().isSelected() ? StaticContainer.ConnectionStatus.BOT : StaticContainer.ConnectionStatus.PLAYER);
                }
            }
            //Load main game
            Parent gameDisplay = null;
            try {
                gameDisplay = game.load();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(gameDisplay, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
            primaryStage.setScene(scene);
        });



        gameStop();

        primaryStage.setTitle("Co Ca Ngua");
        Scene scene = new Scene(menuDisplay, 1200 , 900);
        scene.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**===========================[Test code goes here]===========================*/

    /**
     * once all the piece of nest reach 6-5-4-3
     */
    private void gameStop(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < players.length ; i++){
                    if (players[i].isGetToHouse()){
                        try {
                            Sound.playSound(Sound.WIN);
                            displayMessage(i);
                            turn = 2;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        timer.stop();
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * load the end UI
     * @param player
     * @throws IOException
     */
    private void displayMessage(int player) throws IOException {
        alertBox = new Stage();
        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle("WIN");
        alertBox.setMinWidth(250);

        FXMLLoader end = new FXMLLoader(getClass().getResource("view/end.fxml"));
        Parent root = end.load();
//        notifier.setText(players[player].getName() + " has reached to all of the house");
        ArrayList<String> playerName = new ArrayList<>();
        ArrayList<Integer> playerScore = new ArrayList<>();
        Scanner fileInput = new Scanner(file);
        fileInput.useDelimiter(",|\n");
        while (fileInput.hasNext()) {
            playerName.add(fileInput.next());
            playerScore.add(fileInput.nextInt());
        }
        fileInput.close();

        System.out.println(playerName.size() + " playerName.size()");
        java.io.PrintWriter output = new java.io.PrintWriter(file);
        for (int i = 0; i < players.length; i++ ) {
            if (players[i].getConnectionStatus() != ConnectionStatus.OFF) {
                for (int j = 0; j < playerName.size(); j++) {
                    if (playerName.get(j).equals(players[i].getName())) {
                        int newScore = playerScore.get(j) + players[i].getPoints();
                        System.out.println(playerName.get(j));
                        System.out.println(players[i].getName());
                        System.out.println("Yesssssssssssssss");
                        playerScore.set(j,newScore);
                    } else if (j == playerName.size() - 1) {
                        playerName.add(players[i].getName());
                        playerScore.add(players[i].getPoints());
                        System.out.println("Noooooooo");
                        for (int a = 0; a < playerName.size() - 1; a++ ) {
                            if (playerName.get(a) == players[i].getName()) {
                                playerName.remove(playerName.size()-1);
                                playerScore.remove(playerName.size()-1);
                                break;
                            }
                        }
                        break;
                    }
                }
                if (playerName.size() == 0) {
                    output.write(players[i].getName() + "," + players[i].getPoints() + "\n");
                }
            }
        }
        for (int j = 0; j < playerName.size(); j++) {
            output.write(playerName.get(j) + "," + playerScore.get(j) + '\n');
        }
        output.close();

        alertBox.setScene(new Scene(root));
        alertBox.setTitle("WIN");
        alertBox.show();
    }

    /**
     * restart new game
     * @throws Exception
     */
    @FXML
    public void loadNewGame() throws Exception {
        alertBox.close();
        start(window);      //call the start in main
    }

    /**
     * quit game
     */
    @FXML
    public void quitGame(){
        System.exit(0);
    }

    /**===========================[End of view.test code]===========================*/


    public static void main(String[] args) {
        launch(args);
    }
}
