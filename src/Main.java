import controller.GameController;
import controller.MenuController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import helper.StaticContainer;
import model.Sound;
import model.core.Player;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static helper.StaticContainer.*;
import static helper.StaticContainer.numberOfPlayer;

public class Main extends Application implements Initializable{
    static java.io.File file = new java.io.File("score.txt");

    @FXML
    public Label endGameLb;

    public TextField winner;
    public Button newGame;
    public Button quit;

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
            if (numberOfPlayer > 1){
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
            }
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
                            displayMessage();
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
     * @throws IOException
     */
    private void displayMessage() throws IOException {
        alertBox = new Stage();
        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setMinWidth(250);

        FXMLLoader end = new FXMLLoader(getClass().getResource("view/end.fxml"));
        Parent load = end.load();

//        ArrayList<String> playerName = new ArrayList<>();
//        ArrayList<Integer> playerScore = new ArrayList<>();
//        Scanner fileInput = new Scanner(file);
//        fileInput.useDelimiter(",|\n");
//
//        while (fileInput.hasNext()) {
//            playerName.add(fileInput.next());
//            playerScore.add(fileInput.nextInt());
//        }
//
//        fileInput.close();
//
//        System.out.println(playerName.size() + " playerName.size()");
//        java.io.PrintWriter output = new java.io.PrintWriter(file);
//        for (int i = 0; i < players.length; i++ ) {
//            if (players[i].getConnectionStatus() != ConnectionStatus.OFF) {
//                boolean duplcated = false;
//                for (int j = 0; j < playerName.size(); j++) {
//                    if (playerName.get(j).equals(players[i].getName())) {
//                        int newScore = playerScore.get(j) + players[i].getPoints();
//                        playerScore.set(j,newScore);
//                        duplcated = true;
//                    } else if (j == playerName.size() - 1 && !duplcated) {
//                        playerName.add(players[i].getName());
//                        playerScore.add(players[i].getPoints());
//                        break;
//                    }
//                }
//                if (playerName.size() == 0)
//                    output.write(players[i].getName() + "," + players[i].getPoints() + "\n");
//            }
//        }
//        for (int j = 0; j < playerName.size(); j++) {
//            output.write(playerName.get(j) + "," + playerScore.get(j) + '\n');
//        }
//        output.close();

        load.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
        alertBox.setScene(new Scene(load));
        alertBox.setTitle(language.getWinScreenTitle());
        //endGameLb.setText(language.getEndGameLabel());
        alertBox.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newGame.setText(language.getNewGame());
        quit.setText(language.getQuit());
        String max_person = players[0].getName();
        int max_score = players[0].getPoints();
        for (int i = 0; i < players.length ; i++){
            if (players[i].getPoints() > max_score){
                max_person = players[i].getName();
                max_score = players[i].getPoints();
            }
        }
        winner.setText(max_person + " " + language.getResultStatement() + " " + max_score);
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
