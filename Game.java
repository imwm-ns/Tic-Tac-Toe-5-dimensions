import java.util.*;

public class Game {
    private char[][][][][] board;
    private int currentPlayer;

    public Game() {
        this.board = new char[3][3][3][3][3];
        resetBoard();
        this.currentPlayer = 1;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        Arrays.fill(board[i][j][k][l], '-');
                    }
                }
            }
        }
    }

    public boolean makeMove(int[] move) {
        int i = move[0];
        int j = move[1];
        int k = move[2];
        int l = move[3];
        int m = move[4];

        if (board[i][j][k][l][m] != '-') {
            return false;
        }

        char symbol = (currentPlayer == 1) ? 'X' : 'O';
        board[i][j][k][l][m] = symbol;

        currentPlayer = 3 - currentPlayer;
        return true;
    }

    public int checkWinner() {
        //Check rows and columns
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    // rows
                    if (board[i][j][k][0][0] != '-' &&
                    board[i][j][k][0][0] == board[i][j][k][1][1] &&
                    board[i][j][k][1][1] == board[i][j][k][2][2]) {
                        return (board[i][j][k][0][0] == 'X') ? 1 : 2;
                    }
                    if (board[i][j][k][2][0] != '-' &&
                    board[i][j][k][2][0] == board[i][j][k][1][1] &&
                    board[i][j][k][1][1] == board[i][j][k][0][2]) {
                        return (board[i][j][k][2][0] == 'X') ? 1 : 2;
                    }

                    // Columns
                    if (board[i][j][0][k][0] != '-' &&
                    board[i][j][0][k][0] == board[i][j][1][k][0] &&
                    board[i][j][1][k][0] == board[i][j][2][k][0]) {
                        return (board[i][j][0][k][0] == 'X') ? 1 : 2;
                    }

                    if (board[i][j][0][k][2] != '-' &&
                    board[i][j][0][k][2] == board[i][j][1][k][2] &&
                    board[i][j][1][k][2] == board[i][j][2][k][2]) {
                        return (board[i][j][0][k][0] == 'X') ? 1 : 2;
                    }
                }
            }
        }

        // Check main diag
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j][0][0][0] != '-' &&
                board[i][j][0][0][0] == board[i][j][1][1][1] &&
                board[i][j][1][1][1] == board[i][j][2][2][2]) {
                    return (board[i][j][0][0][0] == 'X') ? 1 : 2;
                }

                if (board[i][j][0][2][0] != '-' &&
                board[i][j][0][2][0] == board[i][j][1][1][1] &&
                board[i][j][1][1][1] == board[i][j][2][0][2]) {
                    return (board[i][j][0][0][0] == 'X') ? 1 : 2;
                }
            }
        }
    }
}