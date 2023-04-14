package lib.src.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
public class GameGUI extends JFrame {
    // Initialize of Variable.
    public JFrame frame = new JFrame("Tic Tac Toe 5 Dimensions.");
    private JLabel messageLabel = new JLabel("", JLabel.CENTER);
    private ImageIcon icon;
    private ImageIcon opponentIcon;

    private Square[] board = new Square[25];
    private Square currentSquare;

    private static int PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public GameGUI(String serverAddress) throws Exception {

        // Setup socket to connect a server.
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Setup layout of board game.
        messageLabel.setBackground(Color.lightGray);
        messageLabel.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
        frame.getContentPane().add(messageLabel, "South");

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(5, 5, 2, 2));
        for (int i = 0; i < board.length; i++) {
            final int j = i;
            board[i] = new Square();
            board[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    currentSquare = board[j];
                    out.println("MOVE " + j);}});
            boardPanel.add(board[i]);
        }
        frame.getContentPane().add(boardPanel, "Center");
    }

    // Input a message from socket and read it. Check a message if the condition is equal a start message. It will be to set up and display it on a game gui.
    public void play() throws Exception {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                char participant = response.charAt(8);
                icon = new ImageIcon(participant == 'X' ? "lib/src/assets/x.png" : "lib/src/assets/o.png");
                opponentIcon  = new ImageIcon(participant == 'X' ? "lib/src/assets/o.png" : "lib/src/assets/x.png");
                frame.setTitle("Tic Tac Toe - Player: " + participant);
            }
            while (true) {
                response = in.readLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("You moved. Please wait a opponent player.");
                    currentSquare.setIcon(icon);
                    currentSquare.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    board[loc].setIcon(opponentIcon);
                    board[loc].repaint();
                    messageLabel.setText("Opponent player moved. Your turn.");
                } else if (response.startsWith("VICTORY")) {
                    messageLabel.setText("You win");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    messageLabel.setText("You lose");
                    break;
                } else if (response.startsWith("TIE")) {
                    messageLabel.setText("You tied");
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        }
        finally {
            socket.close();
        }
    }
    // Create a Square class for main widget.
    static class Square extends JPanel {
        JLabel label = new JLabel((Icon)null);

        public Square() {
            setBackground(Color.white);
            add(label);
        }

        public void setIcon(Icon icon) {
            label.setIcon(icon);
        }
    }
}