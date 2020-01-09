package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;

import static javafx.scene.paint.Color.*;

/* MAP CLASS THAT CONTAINS ALL GUI ELEMENTS + HASHMAP FOR INDEX */
public class Map extends Pane {
    // Size constants for map drawing
    final static double CIRCLE_RADIUS = 20;
    final static double HOUSE_LONG_SIDE = 86;
    final static double HOUSE_SHORT_SIDE = 32;
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
    final public static int BLUE_HOUSE_1 = 48;
    final public static int YELLOW_HOUSE_1 = 54;
    final public static int GREEN_HOUSE_1 = 60;
    final public static int RED_HOUSE_1 = 66;

    // A map to store all circle positions
    static HashMap<Integer, Space> spaceMap = new HashMap<>();

    // A map to store all nests with colors as their key
    static HashMap<Integer, Nest> nestMap = new HashMap<>();

    // Maps to store all houses
    static HashMap<Integer, House> houseMap = new HashMap<>();

    // Map's width and height
    public static double MAP_WIDTH;
    public static double MAP_HEIGHT;

    // Set color of region
    final public static Color[] REGION_COLOR = {Color.DODGERBLUE, Color.GOLD, Color.SEAGREEN,Color.TOMATO};

    /* FUNCTIONS BELOW */

    // Construct a map with Spaces and Nests
    public Map() {
        // Draw all the GUI elements
        drawSpaces();
        defineMapSize();    // Vital for future draws
        drawHouses();
        drawNests();

        // Add css file
        getStylesheets().add(getClass().getResource("/cocangua.css").toExternalForm());
        this.setId("map");
    }

    /* DRAW THE CIRCLE SPACES AND ADD THEM TO SPACEMAP */
    void drawSpaces() {
        // Draw Blue region
        drawArrivalSpace(0, 0, REGION_COLOR[0], spaceMap.size());
        drawVerticalSpaces(-(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), REGION_COLOR[0], spaceMap.size(), true);
        drawHorizontalSpaces(-(CIRCLE_RADIUS * 2 + POS_GAP), 0, REGION_COLOR[0], spaceMap.size(), false);

        // Draw Yellow region
        drawArrivalSpace(0, CIRCLE_RADIUS * 2 + ARRIVAL_GAP, REGION_COLOR[1], spaceMap.size());
        drawHorizontalSpaces(0, CIRCLE_RADIUS * 2 + ARRIVAL_GAP, REGION_COLOR[1], spaceMap.size(), true);
        drawVerticalSpaces(CIRCLE_RADIUS * 2 + POS_GAP,REGION_COLOR[1], spaceMap.size(), true);

        // Draw Green region
        drawArrivalSpace(CIRCLE_RADIUS * 2 + ARRIVAL_GAP, 0, REGION_COLOR[2], spaceMap.size());
        drawVerticalSpaces(CIRCLE_RADIUS * 2 + ARRIVAL_GAP, REGION_COLOR[2], spaceMap.size(), false);
        drawHorizontalSpaces(CIRCLE_RADIUS * 2 + POS_GAP, 0, REGION_COLOR[2], spaceMap.size(), true);

        // Draw Red region
        drawArrivalSpace(0, -(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), REGION_COLOR[3], spaceMap.size());
        drawHorizontalSpaces(0, -(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), REGION_COLOR[3], spaceMap.size(), false);
        drawVerticalSpaces(-(CIRCLE_RADIUS * 2 + POS_GAP), REGION_COLOR[3], spaceMap.size(), false);

        // Mark starting positions
        markSpace(spaceMap.get(BLUE_START));
        markSpace(spaceMap.get(YELLOW_START));
        markSpace(spaceMap.get(GREEN_START));
        markSpace(spaceMap.get(RED_START));
    }

    /* DRAW THE NESTS IN MAP's 4 CORNERS AND ADD TO NESTMAP */
    void drawNests() {
        drawNestAndPiecesByCoordinates(MAP_PADDING, MAP_PADDING, 0);
        drawNestAndPiecesByCoordinates(MAP_PADDING, MAP_HEIGHT - MAP_PADDING - Nest.NEST_SIZE, 1);
        drawNestAndPiecesByCoordinates(MAP_WIDTH - MAP_PADDING - Nest.NEST_SIZE, MAP_HEIGHT - MAP_PADDING - Nest.NEST_SIZE, 2);
        drawNestAndPiecesByCoordinates(MAP_WIDTH - MAP_PADDING - Nest.NEST_SIZE, MAP_PADDING, 3);
    }

    /* DRAW THE HOME RECTANGLES AND ADD TO HOUSEMAP */
    void drawHouses() {
        double x, y;
        Space space;

        // Get coordinates of Blue arrival
        space = spaceMap.get(BLUE_ARRIVAL);
        x = space.getLayoutX();
        y = space.getLayoutY();

        // Draw blue houses based on arrival space's coordinates
        drawHousesVertically(x - HOUSE_LONG_SIDE / 2, y + CIRCLE_RADIUS + 25, DODGERBLUE, spaceMap.size(), true);

        // Same with Yellow houses
        space = spaceMap.get(YELLOW_ARRIVAL);
        x = space.getLayoutX();
        y = space.getLayoutY();
        drawHousesHorizontally(x + CIRCLE_RADIUS + 25, y - HOUSE_LONG_SIDE / 2, GOLD, spaceMap.size() + 6, true);

        // Green houses
        space = spaceMap.get(GREEN_ARRIVAL);
        x = space.getLayoutX();
        y = space.getLayoutY();
        drawHousesVertically(x - HOUSE_LONG_SIDE / 2, y - (CIRCLE_RADIUS + 25 + HOUSE_SHORT_SIDE), SEAGREEN, spaceMap.size() + 12, false);

        // Same with Red houses
        space = spaceMap.get(RED_ARRIVAL);
        x = space.getLayoutX();
        y = space.getLayoutY();
        drawHousesHorizontally(x - (CIRCLE_RADIUS + 25 + HOUSE_SHORT_SIDE), y - HOUSE_LONG_SIDE / 2, TOMATO, spaceMap.size() + 18, false);
    }

    // Draw 6 rectangles of specified color horizontally
    void drawHousesHorizontally(double x, double y, Color color, int startIndex, boolean isLtR) {
        for (int i = 0; i < 6; i++) {
            House house = new House(color, HOUSE_SHORT_SIDE, HOUSE_LONG_SIDE, i + 1);
            house.setLayoutY(y);

            if (isLtR)
                house.setLayoutX(x + i * (HOUSE_SHORT_SIDE + POS_GAP));
            else
                house.setLayoutX(x - i * (HOUSE_SHORT_SIDE + POS_GAP));

            this.getChildren().add(house);
            houseMap.put(i + startIndex, house);
        }
    }

    // Draw 6 rectangles of specified color vertically
    void drawHousesVertically(double x, double y, Color color, int startIndex, boolean isUtD) {
        for (int i = 0; i < 6; i++) {
            House house = new House(color, HOUSE_LONG_SIDE, HOUSE_SHORT_SIDE, i + 1);
            house.setLayoutX(x);

            if (isUtD)
                house.setLayoutY(y + i * (HOUSE_SHORT_SIDE + POS_GAP));
            else
                house.setLayoutY(y - i * (HOUSE_SHORT_SIDE + POS_GAP));

            this.getChildren().add(house);
            houseMap.put(i + startIndex, house);
        }
    }

    // Draw a single nest based on position and color
    void drawNestAndPiecesByCoordinates(double x, double y, int nestId) {
        Nest nest = new Nest(nestId);
        nest.setLayoutX(x);
        nest.setLayoutY(y);
        nestMap.put(nestId, nest);
        this.getChildren().add(nest);

        // Get the pieces in pieceList to display
        for (int pieceID = 0; pieceID < 4 ; pieceID++){
            Piece piece = nest.getPieceList()[pieceID];
            piece.pieceInTheNest(nestId);
            getChildren().add(piece);
        }
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
        space.setStrokeWidth(4);
    }

    // Get coordinates of targeted house
    public static double getHouseX(int key) {
        House house = houseMap.get(key);
        // Return different coordinates for vertical houses
        if ((YELLOW_HOUSE_1 <= key && key < GREEN_HOUSE_1) || (RED_HOUSE_1 <= key && key < 72))
            return house.getLayoutX() + HOUSE_SHORT_SIDE / 2;
        else
            return house.getLayoutX() + HOUSE_LONG_SIDE / 2;
    }

    public static double getHouseY(int key) {
        House house = houseMap.get(key);
        // Return different coordinates for vertical houses
        if ((YELLOW_HOUSE_1 <= key && key < GREEN_HOUSE_1) || (RED_HOUSE_1 <= key && key < 72))
            return house.getLayoutY() + HOUSE_LONG_SIDE / 2;
        else
            return house.getLayoutY() + HOUSE_SHORT_SIDE / 2;
    }

    public static double getSpaceX(int key) {
        Space space = spaceMap.get(key);
        return space.getLayoutX();
    }

    public static double getSpaceY(int key) {
        Space space = spaceMap.get(key);
        return space.getLayoutY();
    }

    // Simple getters
    public static HashMap<Integer, Space> getSpaceMap() {
        return spaceMap;
    }

    public static HashMap<Integer, Nest> getNestMap() { return nestMap; }

    public static HashMap<Integer, House> getHouseMap() {
        return houseMap;
    }
}