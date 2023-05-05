package lib.src.server;

import lib.src.game.Game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int PORT = 8901;
    public static void main(String[] args) throws Exception {
        // Create a socket to connect between 2 client.
        ServerSocket listener = new ServerSocket(PORT);
        try {
            System.out.println("Server is running in port: " + PORT);
            while (true) {
                Game game = new Game();
                Game.Player player1 = game.new Player(listener.accept(), 'X');
                System.out.println("Player 1 connected.");
                Game.Player player2 = game.new Player(listener.accept(), 'O');
                System.out.println("Player 2 connected.");
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
