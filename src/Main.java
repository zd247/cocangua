import controller.GameController;
import controller.MenuController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

import java.io.IOException;

import static helper.StaticContainer.*;

public class Main extends Application {
    private Stage window;
    public static AnimationTimer timer;

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


    private void gameStop(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < players.length ; i++){
                    if (players[i].isGetToHouse()){
                        displayMessage(i);
                        timer.stop();
                    }
                }
            }
        };
        timer.start();
    }
    private void displayMessage(int player){
        Stage alertBox = new Stage();

        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle("WIN");
        alertBox.setMinWidth(250);

        Label label = new Label(players[player].getName() + " has reached to all of the house");

        HBox hBox = new HBox(30);

        Button closeButton = quitGameBtn();

        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(actionEvent -> {
            alertBox.close();
            try {
                start(window);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });;

        hBox.getChildren().addAll(closeButton, newGameBtn);

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(label,hBox);
        alertBox.setScene(new Scene(vBox));
        alertBox.show();
    }

    public Button quitGameBtn() {
        Button closeButton = new Button("Quit");
        closeButton.setOnAction(e -> System.exit(0));
        return closeButton;
    }

    /**===========================[End of view.test code]===========================*/


    public static void main(String[] args) {
        launch(args);
    }
}
