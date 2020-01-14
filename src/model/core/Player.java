package model.core;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import helper.StaticContainer.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.*;
import java.io.*;

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
    boolean isClickedOn;
    Text addText = new Text("Click on the pane to add new player...");
    VBox playerDisplayVBox = new VBox();
    TextField textField = new TextField();
    CheckBox toggler = new CheckBox("BOT");
    Dice dice = new Dice();
    boolean isRolled = false;
    int numOfFace = 0;

    private ClientSideConnection csc;


    public Player (int nestId) {
        this.nestId = nestId;
        points = 0;
        this.connectionStatus = ConnectionStatus.OFF;

        this.getChildren().add(addText);
        addText.setLayoutX(70);
        addText.setLayoutY(50);

        //Set default layout
        this.getChildren().add(playerDisplayVBox);
        this.setBackground(new Background(new BackgroundFill(getColorByNestId(nestId), CornerRadii.EMPTY, Insets.EMPTY)));
        textField.setText("Player " + nestId);
        this.name = textField.getText(); //set name

        numberOfPlayer = 0;
        //Game menu set up
        //Set default value when first clicked
        isClickedOn = false;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isClickedOn){
                    playerDisplayVBox.getChildren().addAll(textField, toggler, dice);
                    rollForGetTurn(dice);
                    isClickedOn = true;
                    numberOfPlayer++;
                    //invisible at end of logic
                    addText.setVisible(false);
                   /* connectToServer(); // populate the csc
                    csc.sendNestId(nestId);*/
                }
                else {
                    playerDisplayVBox.getChildren().clear();
                    isClickedOn = false;
                    numberOfPlayer--;
                    addText.setVisible(true);
                }
            }
        });
    }

    public void rollForGetTurn(Dice dice){
        players[nestId].setPointForTurn(dice.roll());
        dice.setDisable(true);
    }

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

    public void rolled(){rolled = true;}

    public boolean isRolled(){ return rolled;}

    public void resetCheck(){rolled = false;}

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

