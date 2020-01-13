import java.net.*;
import java.io.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection[] sscPlayers = new ServerSideConnection[4]; //player container server side, need to find a way to populate this


    public GameServer() {
        System.out.println("-----Game Server ------");
        numPlayers = 0;
        try {
            ss = new ServerSocket(8688);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establish the connection between the server and clients.
     */
    public void acceptConnections() {
        System.out.println("Waiting for connections");
        try {
            while (numPlayers < 4) { // or start button hit.
                Socket s = ss.accept(); // build a socket match with ss and start accepting who send connectToServer trigger.
                ServerSideConnection ssc = new ServerSideConnection(s);
                System.out.println("A Player has connected!");
                Thread t = new Thread(ssc);
                t.start(); // start sending and receiving data event.
                numPlayers++;
            }
            System.out.println("We now have 4 players. No longer accepting connections");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Once server side connection is establish,
     */
    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int nestId;

        public ServerSideConnection(Socket s){
            socket = s;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //when a thread starts
        @Override
        public void run() {
            try {
                //writing
                dataOut.writeChars("Test Message from server");
                dataOut.flush();

                //receiving
                while (true){
                    nestId = dataIn.readInt();
                    System.out.println("Received Nest Id #" + nestId);
                    sscPlayers[nestId] = this;
                    sendNestId();//send to other player (what comes around goes around)
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }


        //server send msg to all other players
        public void sendNestId() {
            for (int i = 0; i < sscPlayers.length;i++){
                if (i != nestId){
//                    sscPlayers[] // look at this home
                }
            }
        }

    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
