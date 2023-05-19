package lib.src.server;

import java.io.IOException;
import java.net.ServerSocket;

import lib.src.App.Game;

public class Server {
    private static final int PORT = 8901;
    private static int numberPlayer = 1;
    private static int match = 1;
    public static void main(String[] args) throws Exception {
        // Create a socket to connect between 2 client.
        ServerSocket listener = new ServerSocket(PORT);
        try {
            System.out.println("Server is running in port: " + PORT);
            while (true) {
                Game game = new Game();
                Game.Player player1 = game.new Player(listener.accept(), 'X');
                System.out.println("Match " + match);
                System.out.println("Player " + numberPlayer + " connected.");
                numberPlayer++;
                Game.Player player2 = game.new Player(listener.accept(), 'O');
                System.out.println("Player " + numberPlayer + " connected.");
                numberPlayer++;
                // Set player opponent of Player 1.
                player1.setOpponent(player2);
                // Set player opponent of Player 2.
                player2.setOpponent(player1);
                game.currentPlayer = player1;
                // Start game Player1, Player2.
                player1.start();
                player2.start();
                match++;
            }
        } catch (IOException e) { // If It has exception. Throw it to catch function and display it on terminal.
          e.printStackTrace();
        } finally { // Close server.
            listener.close();
        }
    }
}
