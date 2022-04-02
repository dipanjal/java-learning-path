package com.dipanjal.batch1.socket.example3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PingServerAsync {

    private final int port;
    private int clientCount = 1;

    public PingServerAsync(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening at 127.0.0.1:"+port);
        listenAndRespondAsync(serverSocket);
    }

    private void listenAndRespondAsync (ServerSocket serverSocket) throws IOException {
        while(!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client (%s) connected %n", clientCount);
            startClientRequestHandlerThread(clientSocket);
        }
    }

    private void startClientRequestHandlerThread(Socket clientSocket) {
        Thread clientHandlerThread = new Thread(new SocketHandler(clientSocket));
        clientHandlerThread.setName("Client "+ clientCount++);
        clientHandlerThread.start();
    }

    public static void main(String[] args) throws IOException {
        PingServerAsync pingServer = new PingServerAsync(6000);
        pingServer.start();
    }
}
