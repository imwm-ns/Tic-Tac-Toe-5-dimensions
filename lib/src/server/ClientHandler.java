package lib.src.server;


import java.io.*;
import java.net.*;

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Handle client requests here
            // ...

            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Error handling client request: " + e);
        }
    }
}
