import java.io.*;
import java.net.*;

public class Client {
    private String serverHost;
    private int serverPort;
    private Socket socket;
    private BufferedRead in;
    private PrintWriter out;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            socket = new Socket(serverHost, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedRead(new InputStreamReader(socket.getInputStream()));
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
            System.out.println("Can't disconnecting from server.")
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
        }
    }

    public static void main(String[] args) {
        String serverHost = args[0];
        int serverPort = Integer.parseInt(args[1]);
        Client client = new Client(serverHost, serverPort);
        Game game = new Game(5);
        GameGUI gui = new GameGUI(game);
        client.connect();
    }
}