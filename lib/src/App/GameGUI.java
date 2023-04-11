package lib.src.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private JLabel statusLabel;
    private JButton[][] boardButtons;
    private Game game;

    // Constructor for GameGUI class
    public GameGUI(Game game) {
        super("Tic Tac Toe 5 Dimension");
        this.game = game;

        // Set up the game board
        boardButtons = new JButton[game.getBoardSize()][game.getBoardSize()];
        JPanel board = new JPanel(new GridLayout(game.getBoardSize(), game.getBoardSize()));
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                boardButtons[i][j] = new JButton("");
                boardButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                boardButtons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        actionsPerformed(e);
                    }
                });
                board.add(boardButtons[i][j]);
            }
        }

        // Set up the status label
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateStatusLabel();

        // Add components to the content pane
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(board, BorderLayout.CENTER);
        content.add(statusLabel, BorderLayout.SOUTH);
        setContentPane(content);

        // Set status label alignment to center
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to handle button click actions
    public void actionsPerformed(ActionEvent e) {
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                if (e.getSource() == boardButtons[i][j]) {
                    int[] move = new int[5]; // 5D move
                    move[0] = i; // Row
                    move[1] = j; // Column
                    move[2] = 0; // Depth
                    move[3] = 0; // Diagonal
                    move[4] = 0; // Anti-diagonal
                    boolean validToMove = game.makeMove(move);
                    if (validToMove) {
                        String symbol = (game.getCurrentPlayer() == Player.PLAYER1) ? "X" : "O";
                        boardButtons[i][j].setText(symbol);
                        updateStatusLabel();
                    }
                }
            }
        }
    }


    // Method to update the status label
    private void updateStatusLabel() {
        switch (game.getGameState()) {
            case PLAYING -> statusLabel.setText(game.getCurrentPlayer() + "'s turn.");
            case DRAW -> statusLabel.setText("Game ended -> Draw.");
            case X_WON -> statusLabel.setText("X won the game.");
            case O_WON -> statusLabel.setText("O won the game.");
        }
    }
}
