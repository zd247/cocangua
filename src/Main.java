import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader menu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
        FXMLLoader game = new FXMLLoader(getClass().getResource("view/game.fxml"));

        Parent menuDisplay = menu.load(); // load main menu

        MenuController menuController = menu.getController();

        //go to main game, if no players is null then the game plays itself
        menuController.startBtn.setOnAction(actionEvent -> {
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
