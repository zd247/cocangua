import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import view.Map;
import view.Space;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
//        primaryStage.setTitle("Co Ca Ngua");
//        primaryStage.setScene(new Scene(root, 1200 , 900));
//        primaryStage.show();

        // Create layout
        HBox hb = new HBox();
        Map map = new Map();
        Pane pane = new Pane();
        hb.getChildren().addAll(map, pane);

        // Test move + occupied
        Circle c = new Circle(20);
        c.setFill(BLUE);
        map.getChildren().add(c);

        // Move c from BLUE_START 3 steps
        movePiece(Map.BLUE_START, 3, c);

        // Test move new circle to same space
        Circle c2 = new Circle(20);
        c2.setFill(BLUE);
        map.getChildren().add(c2);
        // Move will fail, circle remains at 0,0
        movePiece(Map.BLUE_START + 1, 2, c2);

        // Scene stuff
        primaryStage.setScene(new Scene(hb, 800, 800));
        primaryStage.show();
    }

    // Move a specified circle a certain amount
    public void movePiece(int startIndex, int moveAmount, Circle c) {
        Space sp1 = Map.spaceMap.get(startIndex + moveAmount);

        double x = sp1.getLayoutX();
        double y = sp1.getLayoutY();

        if (!sp1.getOccupancy()) {
            c.setLayoutX(x);
            c.setLayoutY(y);
            sp1.setOccupancy(true);
        }

        else
            System.out.println("Space is occupied");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
