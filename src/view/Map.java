package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;

import static javafx.scene.paint.Color.*;

/* MAP CLASS THAT CONTAINS ALL GUI ELEMENTS + HASHMAP FOR INDEX */
public class Map extends Pane {
    // Size constants for map drawing
    final static double CIRCLE_RADIUS = 20;
    final static double POS_GAP = 5;
    final static double ARRIVAL_GAP = 45;
    final static double MAP_PADDING = 20;

    // Defined indexes for reference
    final public static int BLUE_ARRIVAL = 0;
    final public static int YELLOW_ARRIVAL = 12;
    final public static int GREEN_ARRIVAL = 24;
    final public static int RED_ARRIVAL = 36;
    final public static int BLUE_START = 1;
    final public static int YELLOW_START = 13;
    final public static int GREEN_START = 25;
    final public static int RED_START = 37;

    // A map to store all circle positions
    HashMap<Integer, Space> spaceMap = new HashMap<>();

    // A map to store all nests with colors as their key
    HashMap<Color, MyNest> nestMap = new HashMap<>();

    // Map's width and height
    public static double MAP_WIDTH;
    public static double MAP_HEIGHT;

    /* FUNCTIONS BELOW */

    // Construct a map with Spaces and Nests
    public Map() {
        // Draw all the GUI elements
        drawSpaces();
        defineMapSize();    // Vital for future draws
        drawNests();
    }

    /* DRAW THE CIRCLE SPACES AND ADD THEM TO SPACEMAP */
    void drawSpaces() {
        // Draw Blue region
        drawArrivalSpace(0, 0, DODGERBLUE, spaceMap.size());
        drawVerticalSpaces(-(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), DODGERBLUE, spaceMap.size(), true);
        drawHorizontalSpaces(-(CIRCLE_RADIUS * 2 + POS_GAP), 0, DODGERBLUE, spaceMap.size(), false);

        // Draw Yellow region
        drawArrivalSpace(0, CIRCLE_RADIUS * 2 + ARRIVAL_GAP, GOLD, spaceMap.size());
        drawHorizontalSpaces(0, CIRCLE_RADIUS * 2 + ARRIVAL_GAP, GOLD, spaceMap.size(), true);
        drawVerticalSpaces(CIRCLE_RADIUS * 2 + POS_GAP, GOLD, spaceMap.size(), true);

        // Draw Green region
        drawArrivalSpace(CIRCLE_RADIUS * 2 + ARRIVAL_GAP, 0, SEAGREEN, spaceMap.size());
        drawVerticalSpaces(CIRCLE_RADIUS * 2 + ARRIVAL_GAP, SEAGREEN, spaceMap.size(), false);
        drawHorizontalSpaces(CIRCLE_RADIUS * 2 + POS_GAP, 0, SEAGREEN, spaceMap.size(), true);

        // Draw Red region
        drawArrivalSpace(0, -(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), TOMATO, spaceMap.size());
        drawHorizontalSpaces(0, -(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), TOMATO, spaceMap.size(), false);
        drawVerticalSpaces(-(CIRCLE_RADIUS * 2 + POS_GAP), TOMATO, spaceMap.size(), false);

        // Mark starting positions
        markSpace(spaceMap.get(BLUE_START));
        markSpace(spaceMap.get(YELLOW_START));
        markSpace(spaceMap.get(GREEN_START));
        markSpace(spaceMap.get(RED_START));
    }

    /* DRAW THE NESTS IN MAP's 4 CORNERS AND ADD TO NESTMAP */
    void drawNests() {
        drawNestByCoordinates(MAP_PADDING, MAP_PADDING, DODGERBLUE);
        drawNestByCoordinates(MAP_PADDING, MAP_HEIGHT - MAP_PADDING - MyNest.NEST_SIZE, GOLD);
        drawNestByCoordinates(MAP_WIDTH - MAP_PADDING - MyNest.NEST_SIZE, MAP_HEIGHT - MAP_PADDING - MyNest.NEST_SIZE, SEAGREEN);
        drawNestByCoordinates(MAP_WIDTH - MAP_PADDING - MyNest.NEST_SIZE, MAP_PADDING, TOMATO);
    }

    // Draw a single nest based on position and color
    void drawNestByCoordinates(double x, double y, Color color) {
        MyNest nest = new MyNest(color);
        nest.setLayoutX(x);
        nest.setLayoutY(y);

        this.getChildren().add(nest);
        this.nestMap.put(color, nest);
    }

    // Set map size based on drawn Spaces
    void defineMapSize() {
        // Get distance between top & bottom arrival spaces (660 x 660)
        double x = spaceMap.get(RED_START - 1).getLayoutX() - spaceMap.get(YELLOW_START - 1).getLayoutX();
        double y = spaceMap.get(GREEN_START - 1).getLayoutY() - spaceMap.get(BLUE_START - 1).getLayoutY();

        // Add padding to extracted width and height
        MAP_WIDTH = x + CIRCLE_RADIUS * 2 + MAP_PADDING * 2;
        MAP_HEIGHT = y + CIRCLE_RADIUS * 2 + MAP_PADDING * 2;

        // Wrap map around circles
        setPrefSize(MAP_WIDTH, MAP_HEIGHT);
        setMinSize(MAP_WIDTH, MAP_HEIGHT);
        setMaxSize(MAP_WIDTH, MAP_HEIGHT);
        setStyle("-fx-border-color: black");
    }

    // Draw 5 horizontal circles of specified color and direction
    void drawHorizontalSpaces(double xGap, double yGap, Color color, int startIndex, boolean isLtR) {
        // Update the latest coordinates for reference
        double x = spaceMap.get(spaceMap.size() - 1).getLayoutX() + xGap;
        double y =  spaceMap.get(spaceMap.size() - 1).getLayoutY() + yGap;

        // Print 5
        for (int i = 0; i < 5; i++) {
            Space space = new Space(color);
            space.setLayoutY(y);   // Horizontal circles have same Y

            // Print circles by specified direction
            if (isLtR)  // Print left to right
                space.setLayoutX(x + i * (CIRCLE_RADIUS * 2 + POS_GAP));
            else
                space.setLayoutX(x - i * (CIRCLE_RADIUS * 2 + POS_GAP));

            // Add to pane
            this.getChildren().add(space);
            spaceMap.put(i + startIndex, space);
        }
    }

    // Draw 6 vertical circles of specified color and direction
    void drawVerticalSpaces(double xGap, Color color, int startIndex, boolean isUtD) {
        // Update the latest coordinates for reference
        double x = spaceMap.get(spaceMap.size() - 1).getLayoutX() + xGap;
        double y =  spaceMap.get(spaceMap.size() - 1).getLayoutY();

        // Print 6
        for (int i = 0; i < 6; i++) {
            Space space = new Space(color);
            space.setLayoutX(x);

            // Print circles by specified direction
            if (isUtD)  // Print up to down
                space.setLayoutY(y + i * (CIRCLE_RADIUS * 2 + POS_GAP));
            else
                space.setLayoutY(y - i * (CIRCLE_RADIUS * 2 + POS_GAP));

            // Add to pane
            this.getChildren().add(space);
            spaceMap.put(i + startIndex, space);
        }
    }

    // Draw the home arrival circle
    void drawArrivalSpace(double xGap, double yGap, Color color, int startIndex) {
        double x = 310 + MAP_PADDING + CIRCLE_RADIUS; // Blue arrival coordinates (default)
        double y = MAP_PADDING + CIRCLE_RADIUS;

        // Update the latest coordinates for reference
        if (spaceMap.size() != 0) {
            x = spaceMap.get(spaceMap.size() - 1).getLayoutX() + xGap;
            y =  spaceMap.get(spaceMap.size() - 1).getLayoutY() + yGap;
        }

        Space space = new Space(color);
        space.setLayoutX(x);
        space.setLayoutY(y);

        this.getChildren().add(space);
        spaceMap.put(startIndex, space);
    }

    // Change border of space to black
    void markSpace(Space space) {
        space.setStroke(BLACK);
        space.setStrokeWidth(2);
    }

    // Getters
    public HashMap<Integer, Space> getSpaceMap() {
        return spaceMap;
    }
    public HashMap<Color, MyNest> getNestMap() { return nestMap; }
    public static double getMapWidth() {
        return MAP_WIDTH;
    }
    public static double getMapHeight() {
        return MAP_HEIGHT;
    }
}
