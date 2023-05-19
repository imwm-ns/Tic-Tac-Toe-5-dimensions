package lib.src.App;


import java.net.*;
import java.io.*;

public class Game {
    // Initialize of Variable.

    // A board of 25 squares.
    private Player[] board = new Player[25];

    // Set current player.
    public Player currentPlayer;

    // Check who is a winner.
    public boolean hasWinner() {
        return
                // Check for wins in 2D planes
                (board[0] != null && board[0] == board[1] && board[0] == board[2] && board[0] == board[3] && board[0] == board[4])
                        || (board[5] != null && board[5] == board[6] && board[5] == board[7] && board[5] == board[8] && board[5] == board[9])
                        || (board[10] != null && board[10] == board[11] && board[10] == board[12] && board[10] == board[13] && board[10] == board[14])
                        || (board[15] != null && board[15] == board[16] && board[15] == board[17] && board[15] == board[18] && board[15] == board[19])
                        || (board[20] != null && board[20] == board[21] && board[20] == board[22] && board[20] == board[23] && board[20] == board[24])

                        // Check for wins in 3D planes
                        || (board[0] != null && board[0] == board[5] && board[0] == board[10] && board[0] == board[15] && board[0] == board[20])
                        || (board[1] != null && board[1] == board[6] && board[1] == board[11] && board[1] == board[16] && board[1] == board[21])
                        || (board[2] != null && board[2] == board[7] && board[2] == board[12] && board[2] == board[17] && board[2] == board[22])
                        || (board[3] != null && board[3] == board[8] && board[3] == board[13] && board[3] == board[18] && board[3] == board[23])
                        || (board[4] != null && board[4] == board[9] && board[4] == board[14] && board[4] == board[19] && board[4] == board[24])

                        // Check for wins in diagonals
                        || (board[0] != null && board[0] == board[6] && board[0] == board[12] && board[0] == board[18] && board[0] == board[24])
                        || (board[4] != null && board[4] == board[8] && board[4] == board[12] && board[4] == board[16] && board[4] == board[20]);
    }

    // Check a fill of box hasn't empty.
    public boolean boardFilledUp() {
        for (int i = 0; i < 25; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }
    // Select location of empty board with symbol.
    public synchronized boolean makeMove(int location, Player player) {
        // Check second parameter is current play and location in board is empty
        if (player == currentPlayer && board[location] == null) {
            // Place symbol on empty board.
            board[location] = currentPlayer;
            // Set current player is opponent player.
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    // Create Thread by Player
    public class Player extends Thread {
        // Initialize of Variable.
        char participant;
        Player opponent;
        Socket socket;
        BufferedReader input;
        PrintWriter output;

        // A constructor of player when connect to the game.
        public Player(Socket socket, char participant) {
            this.socket = socket;
            this.participant = participant;
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + participant);
                output.println("MESSAGE Waiting for opponent to connect");
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void otherPlayerMoved(int location) {
            output.println("OPPONENT_MOVED " + location);
            output.println(
                    hasWinner() ? "DEFEAT" : boardFilledUp() ? "TIE" : "");
        }

        public void run() {
            try {
                // Tell the second player to wait the fist player.
                output.println("MESSAGE Please wait the first player move.");

                // Tell the first player that it is his/her turn.
                if (participant == 'X') {
                    output.println("MESSAGE Your move");
                }

                // The main logic of the game. if command variable read from output stream of socket.
                while (true) {
                    String response = input.readLine();
                    // If the first of word in response is equal move. Read location of the point and call makeMove method to select symbol on the board.
                    if (response.startsWith("MOVE")) {
                        int location = Integer.parseInt(response.substring(5));
                        if (makeMove(location, this)) {
                            output.println("VALID_MOVE");
                            output.println(hasWinner() ? "VICTORY"
                                    : boardFilledUp() ? "TIE"
                                    : "");
                        } else { // If You click an empty box on the board without your turn. A status show a message to you.
                            output.println("MESSAGE Not your turn.");
                        }
                    } else if (response.startsWith("QUIT")) {
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}