package lib.src.client;

import java.io.*;
import java.net.*;

import lib.src.App.Game;
import lib.src.App.GameGUI;


public class Client {
    private String serverHost;
    private int serverPort;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            socket = new Socket(serverHost, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server.");
        } catch (IOException e) {
            System.out.println("Can't connecting to server.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.close();
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.out.println("Can't disconnecting from server.");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Can't receiving message from server.");
            return null;
        }
    }

    public static void main(String[] args) {
        String serverHost = args[0];
        int serverPort = 12345;
        Client client = new Client(serverHost, serverPort);
        Game game = new Game(5);
        GameGUI gui = new GameGUI(game);
        client.connect();
    }
}