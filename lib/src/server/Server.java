package lib.src.server;

import lib.src.server.ClientHandler;

import java.net.*;

import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private final int port;

    public Server(int port) {this.port = port;}

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected.");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Can't to starting server on port: " + port);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
        server.start();
    }
}
