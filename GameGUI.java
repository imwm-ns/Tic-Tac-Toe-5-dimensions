import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {
    private JLabel statusLabel;
    private JButton[] boardButtons;
    private Game game;

    // Overview of board game.
    public GameGUI(Game game) {
        super("Tic Tac Toe");
        this.game = game;

        //Set a board
        boardButtons = new JButton[game.getBoardSize()];
        JPanel board = new JPanel(new GridLayout(game.getBoardSize(), game.getBoardSize()));
        for (int i = 0; i < boardButtons.length; i++) {
            boardButtons[i] = new JButton("");
            boardButtons[i].setFont(new Font("Arial", Font.PLAIN, 80));
            boardButtons[i].addActionListener(this);
            board.add(boardButtons[i]);
        }

        // Set a status of label
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateStatusLabel();

        // Add component into board.
        JPanel content = new JPanel(new BoardLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(board, BorderLayout.center);
        content.add(statusLabel, BorderLayout.SOUTH);
        setContentPane(content);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Create Method to add action of button into a board.
    public void actionsPerform(ActionEvent e) {
        for (int i = 0; i < boardButtons.length; i++) {
            if (e.getSource() == boardButtons[i]) {
                boolean validToMove = game.makeMove(i);
                if (validToMove) {
                    boardButtons[i].setText(Character.toString(game.getBoard()[i]));
                    updateStatusLabel();
                }
            }
        }
    }

    // Create method to update status of label in south position.
    private void updateStatusLabel() {
        switch (game.getGameState()) {
            case PLAYING:
                statusLabel.setText(game.getCurrentPlayer() + "'s turn.");
                break;
            case DRAW:
                statusLabel.setText("Game ended -> Draw.");
                break;
            case X_WON:
                statusLabel.setText("X won the game.");
                break;
            case O_WON:
                statusLabel.setText("O won the game.");
                break;
        }
    }
}