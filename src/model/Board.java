package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.HashMap;

import static javafx.scene.paint.Color.*;

public class Board extends Pane {
    final static double CIRCLE_RADIUS = 20;
    final static double POS_GAP = 5;
    final static double ARRIVAL_GAP = 45;

    // A map to store all circle positions
    static HashMap<Integer, Space> map = new HashMap<>();

    // Create a board (currently only have circles)
    public Board() {

        // Draw Blue region
        drawArrivalSpace(0, 0, DODGERBLUE, map.size());
        drawVerticalSpaces(-(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), DODGERBLUE, map.size(), true);
        drawHorizontalSpaces(-(CIRCLE_RADIUS * 2 + POS_GAP), 0, DODGERBLUE, map.size(), false);

        // Draw Yellow region
        drawArrivalSpace(0, CIRCLE_RADIUS * 2 + ARRIVAL_GAP, GOLD, map.size());
        drawHorizontalSpaces(0, CIRCLE_RADIUS * 2 + ARRIVAL_GAP, GOLD, map.size(), true);
        drawVerticalSpaces(CIRCLE_RADIUS * 2 + POS_GAP, GOLD, map.size(), true);

        // Draw Green region
        drawArrivalSpace(CIRCLE_RADIUS * 2 + ARRIVAL_GAP, 0, SEAGREEN, map.size());
        drawVerticalSpaces(CIRCLE_RADIUS * 2 + ARRIVAL_GAP, SEAGREEN, map.size(), false);
        drawHorizontalSpaces(CIRCLE_RADIUS * 2 + POS_GAP, 0, SEAGREEN, map.size(), true);

        // Draw Red region
        drawArrivalSpace(0, -(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), TOMATO, map.size());
        drawHorizontalSpaces(0, -(CIRCLE_RADIUS * 2 + ARRIVAL_GAP), TOMATO, map.size(), false);
        drawVerticalSpaces(-(CIRCLE_RADIUS * 2 + POS_GAP), TOMATO, map.size(), false);

        // Mark the home arrival positions
        map.get(0).setStroke(BLACK);
        map.get(12).setStroke(BLACK);
        map.get(24).setStroke(BLACK);
        map.get(36).setStroke(BLACK);

        // Mark the starting position for each color
        map.get(1).setFill(LIGHTBLUE);
        map.get(13).setFill(KHAKI);
        map.get(25).setFill(MEDIUMSEAGREEN);
        map.get(37).setFill(CORAL);

        // Print total of circles in HashMap
        System.out.println(map.size());
    }

    // Draw 5 horizontal circles of specified color and direction
    void drawHorizontalSpaces(double xGap, double yGap, Color color, int startIndex, boolean isLtR) {
        // Update the latest coordinates for reference
        double x = map.get(map.size() - 1).getLayoutX() + xGap;
        double y =  map.get(map.size() - 1).getLayoutY() + yGap;

        // Print 5
        for (int i = 0; i < 5; i++) {
            Space space = new Space(color);
            space.setLayoutY(y);   // Horizontal circles have same Y

            // Print circles by specified direction
            if (isLtR)
                space.setLayoutX(x + i * (CIRCLE_RADIUS * 2 + POS_GAP));
            else
                space.setLayoutX(x - i * (CIRCLE_RADIUS * 2 + POS_GAP));

            // Add to pane
            this.getChildren().add(space);
            map.put(i + startIndex, space);
        }
    }

    // Draw 6 vertical circles of specified color and direction
    void drawVerticalSpaces(double xGap, Color color, int startIndex, boolean isUtD) {
        // Update the latest coordinates for reference
        double x = map.get(map.size() - 1).getLayoutX() + xGap;
        double y =  map.get(map.size() - 1).getLayoutY();

        // Print 6
        for (int i = 0; i < 6; i++) {
            Space space = new Space(color);
            space.setLayoutX(x);

            // Print circles by specified direction
            if (isUtD)
                space.setLayoutY(y + i * (CIRCLE_RADIUS * 2 + POS_GAP));
            else
                space.setLayoutY(y - i * (CIRCLE_RADIUS * 2 + POS_GAP));

            // Add to pane
            this.getChildren().add(space);
            map.put(i + startIndex, space);
        }
    }

    // Draw the home arrival circle
    void drawArrivalSpace(double xGap, double yGap, Color color, int startIndex) {
        double x = 360 + CIRCLE_RADIUS; // Blue arrival coordinates (default)
        double y = 20 + CIRCLE_RADIUS;

        // Update the latest coordinates for reference
        if (map.size() != 0) {
            x = map.get(map.size() - 1).getLayoutX() + xGap;
            y =  map.get(map.size() - 1).getLayoutY() + yGap;
        }

        Space space = new Space(color);
        space.setLayoutX(x);
        space.setLayoutY(y);

        this.getChildren().add(space);
        map.put(startIndex, space);
    }
}
