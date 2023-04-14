package lib.src.client;

import lib.src.game.GameGUI;

import javax.swing.*;

public class Client {
    public static void main(String[] args) throws Exception{
        // Input server address in argument from execute file on terminal or Use local address from device.
        String serverAddress = (args.length == 0) ? "localhost" : args[1];
        // Add server address to GameGUI, set window size and play game.
        GameGUI gui = new GameGUI(serverAddress);
        gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.frame.setSize(500, 500);
        gui.frame.setVisible(true);
        gui.frame.setResizable(false);
        gui.play();
    }
}
