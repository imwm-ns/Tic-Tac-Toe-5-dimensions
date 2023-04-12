package lib.src.Server;

import lib.src.Game.Game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int PORT = 12345;
    public static void main(String[] args) throws Exception {
        // Create a socket to connect between 2 client.
        ServerSocket listener = new ServerSocket(PORT);
        try {
            System.out.println("Server is running in port: " + PORT);
            while (true) {
                Game game = new Game();
                Game.Player player1 = game.new Player(listener.accept(), 'X');
                Game.Player player2 = game.new Player(listener.accept(), 'O');
                // Set player opponent of Player 1.
                player1.setOpponent(player2);
                // Set player opponent of Player 2.
                player2.setOpponent(player1);
                game.currentPlayer = player1;
                // Start game Player1, Player2.
                player1.start();
                player2.start();
            }
        } catch (IOException e) { // If It has exception. Throw it to catch function and display it on terminal.
          e.printStackTrace();
        } finally { // Close server.
            listener.close();
        }
    }
}
