package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        // using port 9000 as default
        final int PORT = 9000;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server running on PORT " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerRunnable(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }

    }
}
