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

        primaryStage.setScene(new Scene(root, 800 , 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
