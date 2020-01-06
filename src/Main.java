import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader game = new FXMLLoader(getClass().getResource("view/game.fxml"));
        Parent root = game.load(); // load main game
        primaryStage.setTitle("Co Ca Ngua");

        /**Test code goes here*/

        /***/

        Scene scene = new Scene(root, 1000 , 900);
        scene.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
