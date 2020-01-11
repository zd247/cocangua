import controller.GameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import helper.StaticContainer;

import java.io.IOException;

import static helper.StaticContainer.playerFields;
import static helper.StaticContainer.players;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader menu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
        FXMLLoader game = new FXMLLoader(getClass().getResource("view/game.fxml"));

        Parent menuDisplay = menu.load(); // load main menu

        MenuController menuController = menu.getController();

        //TODO: go to main game, if no players is null then the game plays itself
        menuController.startBtn.setOnAction(actionEvent -> {
            // Pre-process the static array of Player
            for (int i = 0 ; i < players.length;i++){
                //finialize the player field to pass it to player in game
                if (playerFields[i].isClickedOn()){
                    players[i].setConnectionStatus(playerFields[i].getToggler().isSelected() ? StaticContainer.ConnectionStatus.BOT : StaticContainer.ConnectionStatus.PLAYER);
                }
                playerFields[i].getTextField().setText(playerFields[i].getTextField().getText());
                players[i].setName(playerFields[i].getTextField().getText() +  " ( " + players[i].getConnectionStatus() + " ) ");
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





        primaryStage.setTitle("Co Ca Ngua");
        Scene scene = new Scene(menuDisplay, 1000 , 900);
        scene.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
