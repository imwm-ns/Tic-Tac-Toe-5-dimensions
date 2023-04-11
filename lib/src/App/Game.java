package lib.src.App;


import java.util.*;

public class Game {
    private char[][][][][] board;
    private int size;
    private Player currentPlayer;

    public Game(int size) {
        this.board = new char[size][size][size][size][size];
        this.size = size;
        this.currentPlayer = Player.PLAYER1;
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        for (int m = 0; m < size; m++) {
                            Arrays.fill(board[i][j][k][l], '-');
                        }
                    }
                }
            }
        }
    }

    public int getBoardSize() {return size;}
    public Player getCurrentPlayer() {return currentPlayer;}

    public boolean makeMove(int[] move) {
        int row = move[0];
        int col = move[1];
        int depth = move[2];
        int diagonal = move[3];
        int antiDiagonal = move[4];

        // Check if the move is valid
        if (!isValidMove(row, col, depth, diagonal, antiDiagonal)) {
            return false;
        }

        // Update the game board
        char symbol = (currentPlayer == Player.PLAYER1) ? 'X' : 'O';
        board[row][col][depth][diagonal][antiDiagonal] = symbol;

        // Update game state and current player
        currentPlayer = (currentPlayer == Player.PLAYER1) ? Player.PLAYER1 : Player.PLAYER2;

        return true;
    }

    private boolean isValidMove(int row, int col, int depth, int diagonal, int antiDiagonal) {
        // Check if the move is within the bounds of the board
        if (row < 0 || row >= size || col < 0 || col >= size ||
                depth < 0 || depth >= size || diagonal < 0 || diagonal >= size ||
                antiDiagonal < 0 || antiDiagonal >= size) {
            return false;
        }
        return board[row][col][depth][diagonal][antiDiagonal] == '-';
    }


    public int checkWinner() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        for (int m = 0; m < size; m++) {
                            if (checkRows(i, j, k, l, m) != 0) {
                                return checkRows(i, j, k, l, m);
                            }
                            if (checkColumns(i, j, k, l, m) != 0) {
                                return checkColumns(i, j, k, l, m);
                            }
                            if (checkDiagonals(i, j) != 0) {
                                return checkDiagonals(i, j);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public boolean checkDraw() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        for (int m = 0; m < size; m++) {
                            if (board[i][j][k][l][m] == '-') return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private int checkRows(int i, int j, int k, int l, int m) {
        if (board[i][j][k][l][0] != '-' &&
        board[i][j][k][l][0] == board[i][j][k][l][1] &&
        board[i][j][k][l][1] == board[i][j][k][l][2] &&
        board[i][j][k][l][2] == board[i][j][k][l][3] &&
        board[i][j][k][l][3] == board[i][j][k][l][4]) {
            return (board[i][j][k][l][0] == 'X') ? 1 : 2;
        }

        if (board[i][j][k][0][m] != '-' &&
            board[i][j][k][0][m] == board[i][j][k][1][m] &&
            board[i][j][k][1][m] == board[i][j][k][2][m] &&
            board[i][j][k][2][m] == board[i][j][k][3][m] &&
            board[i][j][k][3][m] == board[i][j][k][4][m]) {
                return (board[i][j][k][0][m] == 'X') ? 1 : 2;
        }

        if (board[i][0][k][l][m] != '-' &&
            board[i][0][k][l][m] == board[i][1][k][l][m] &&
            board[i][1][k][l][m] == board[i][2][k][l][m] &&
            board[i][2][k][l][m] == board[i][3][k][l][m] &&
            board[i][3][k][l][m] == board[i][4][k][l][m]) {
                return (board[i][0][k][l][m] == 'X') ? 1 : 2;
        }

        if (board[0][j][k][l][m] != '-' &&
            board[0][j][k][l][m] == board[1][j][k][l][m] &&
            board[1][j][k][l][m] == board[2][j][k][l][m] &&
            board[2][j][k][l][m] == board[3][j][k][l][m] &&
            board[3][j][k][l][m] == board[4][j][k][l][m]) {
                return (board[0][j][k][l][m] == 'X') ? 1 : 2;
        }
        return 0;
    }

    private int checkColumns(int i, int j, int k, int l, int m) {
        if (board[i][j][0][l][m] != '-' &&
                board[i][j][0][l][m] == board[i][j][1][l][m] &&
                board[i][j][1][l][m] == board[i][j][2][l][m] &&
                board[i][j][2][l][m] == board[i][j][3][l][m] &&
                board[i][j][3][l][m] == board[i][j][4][l][m] &&
                board[i][j][4][l][m] == board[i][j][5][l][m]) {
            return (board[i][j][0][l][m] == 'X') ? 1 : 2;
        }
        return 0;
    }

    private int checkDiagonals(int i, int j) {
        char c = board[i][j][1][1][1];
        if (c != '-') {
            if ((board[i][j][0][0][0] == c && board[i][j][1][1][1] == c && board[i][j][2][2][2] == c) ||
                    (board[i][j][0][0][2] == c && board[i][j][1][1][1] == c && board[i][j][2][2][0] == c) ||
                    (board[i][j][0][2][0] == c && board[i][j][1][1][1] == c && board[i][j][2][0][2] == c) ||
                    (board[i][j][0][2][2] == c && board[i][j][1][1][1] == c && board[i][j][2][0][0] == c) ||
                    (board[i][j][0][0][1] == c && board[i][j][1][1][1] == c && board[i][j][2][2][1] == c) ||
                    (board[i][j][0][1][0] == c && board[i][j][1][1][1] == c && board[i][j][2][1][2] == c) ||
                    (board[i][j][0][1][2] == c && board[i][j][1][1][1] == c && board[i][j][2][1][0] == c) ||
                    (board[i][j][0][2][1] == c && board[i][j][1][1][1] == c && board[i][j][2][0][1] == c)) {
                return (c == 'X') ? 1 : 2;
            }
        }
        return 0;
    }

    private int checkSubDiagonals(int i, int j, int k, int l, int m) {
        if (board[0][j][k][l][m] != '-' &&
                board[0][j][k][l][m] == board[i][1][k][l][m] &&
                board[i][1][k][l][m] == board[i][j][2][l][m] &&
                board[i][j][2][l][m] == board[i][j][k][3][m] &&
                board[i][j][k][3][m] == board[i][j][k][l][4]) {
            return (board[0][j][k][l][m] == 'X') ? 1 : 2;
        }
        return 0;
    }


    public State getGameState() {
        int winner = checkWinner();
        if (winner == 1) {
            return State.X_WON;
        } else if (winner == 2) {
            return State.O_WON;
        } else if (checkDraw()) {
            return State.DRAW;
        } else {
            return State.PLAYING;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[][][][] chars : board) {
            for (char[][][] aChar : chars) {
                for (char[][] value : aChar) {
                    for (char[] item : value) {
                        for (char c : item) {
                            sb.append(c);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
    
}