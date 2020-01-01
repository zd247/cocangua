import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Board;

import java.util.HashMap;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
//        primaryStage.setTitle("Co Ca Ngua");
//        primaryStage.setScene(new Scene(root, 1200 , 900));
//        primaryStage.show();

        // Draw board with circles of 4 colors
        Board board = new Board();

        // Scene stuff
        primaryStage.setScene(new Scene(board, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
