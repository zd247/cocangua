package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
        Pane root = new Pane();
        Circle circle = new Circle(0,0,30);
        circle.setFill(Color.RED);
        primaryStage.setTitle("Duy Branch");
        primaryStage.setScene(new Scene(root, 600 , 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
