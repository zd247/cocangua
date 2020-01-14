package model.core;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import helper.StaticContainer.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static helper.StaticContainer.*;


public class Player extends Pane {
    // Player attributes
    private String name;
    private int nestId;
    private ConnectionStatus connectionStatus;
    private int points;
    private boolean rolled;
    private int pointForTurn = 0;
    private int getToHouse ;

    //player main menu display -- these are set first
    private boolean isClickedOn;
    private Text addText = new Text("Click on the pane to add new player...");
    private VBox playerDisplayVBox = new VBox();
    private TextField textField = new TextField();
    private CheckBox toggler = new CheckBox("BOT");
    private Dice dice = new Dice();

    private ClientSideConnection csc;

    /**
     * Constructor for player
     * @param nestId
     */
    public Player (int nestId) {

        this.nestId = nestId;
        points = 0;
        this.connectionStatus = ConnectionStatus.OFF;

        //Add to the player field before click
        this.getChildren().add(addText);
        displayHandEffect();

        addText.setLayoutX(50);
        addText.setLayoutY(50);
        addText.setStyle("-fx-font-size: 16");

        //Set default layout
        this.setBackground(new Background(new BackgroundFill(getColorByNestId(nestId), CornerRadii.EMPTY, Insets.EMPTY)));
        textField.setText("Player " + nestId);

        numberOfPlayer = 0; //number of player attends the game

        //Set default value when first clicked
        isClickedOn = false;

        //Click on the player field
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isClickedOn){
                    //When the pane is clicked, clear all the children before adding more
                    getChildren().clear();
                    getChildren().add(playerDisplayVBox);

                    playerDisplayVBox.getChildren().addAll(textField, toggler, dice);
                    rollForGetTurn(dice);
                    isClickedOn = true;
                    numberOfPlayer++;   //increase number of player
                    //invisible at end of logic
                    addText.setVisible(false);
                   /* connectToServer(); // populate the csc
                    csc.sendNestId(nestId);*/
                }
                else {
                    displayHandEffect();
                    getChildren().add(addText);
                    playerDisplayVBox.getChildren().clear();
                    isClickedOn = false;
                    numberOfPlayer--;
                    addText.setVisible(true);
                }
            }
        });

    }

    /**
     * roll to get the first move
     * @param dice
     */
    public void rollForGetTurn(Dice dice){
        players[nestId].setPointForTurn(dice.roll());
        dice.setDisable(true);
    }

    /**
     * get TextField in playerfield
     * @return
     */
    public TextField getTextField() {
        return textField;
    }

    public Text getAddText() {
        return addText;
    }


    public CheckBox getToggler() {
        return toggler;
    }

    public boolean isClickedOn() {
        return isClickedOn;
    }

    public void setPointForTurn(int point){ pointForTurn = point;}

    public int getPointForTurn(){return pointForTurn;}

    // Player name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNestId() {
        return nestId;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public ConnectionStatus getConnectionStatus() {
        return this.connectionStatus;
    }

    // If this player already rolled
    public void rolled(){rolled = true;}

    public boolean isRolled(){ return rolled;}

    // If this player's turn is done
    public void resetRolled(){rolled = false;}

    public void setPoints (int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }


    public Boolean isGetToHouse() {
        return getToHouse == 4;
    }

    public void increaseGetToHouse() {
        this.getToHouse++;
    }

    public void decreaseGetToHouse(){
        this.getToHouse--;
    }

    /**
     * To set point if the player in the file
     * @throws Exception
     */

    /**
     * set the animation for making it nicely
     */
    private void displayHandEffect(){
        ImageView touchHand = new ImageView("images/touch.png");
        touchHand.setFitWidth(150);
        touchHand.setPreserveRatio(true);
        touchHand.setLayoutX(100);
        touchHand.setLayoutY(100);
        getChildren().add(touchHand);
        TranslateTransition upDown = new TranslateTransition();
        upDown.setNode(touchHand);
        upDown.setByY(50);
        upDown.setDuration(Duration.seconds(1));
        upDown.setCycleCount(Animation.INDEFINITE);
        upDown.setAutoReverse(true);
        upDown.play();
    }

    /**
     * Establish ClientSideConnection to listen to server's messages
     */
    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    /**
     *  Connecting to server by plugging into server socket.
     */
    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
            System.out.println("---- Client -----");
            try {
                //connect to server
                socket = new Socket ("localhost", 8688); //create a socket
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());

                //receiving from server
//                nestId = dataIn.readInt(); // test reading

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        //outer class sender accessor, this will send msg to server
        public void sendNestId(int nestId) {
            try {
                dataOut.writeInt(nestId);
                dataOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

