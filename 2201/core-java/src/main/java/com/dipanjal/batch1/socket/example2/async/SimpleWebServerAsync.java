package com.dipanjal.batch1.socket.example2.async;

import com.dipanjal.batch1.socket.example2.RequestRouter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class SimpleWebServerAsync {

    private final int port;
    private int clientCount = 1;

    public SimpleWebServerAsync(int port){
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening at 127.0.0.1:"+port);
        listenAndRespondAsync(serverSocket);
    }

     private void listenAndRespondAsync(ServerSocket serverSocket) throws IOException {
        while(!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client (%s) connected at %s %n", clientCount, LocalDateTime.now());
            startClientRequestHandlerThread(clientSocket);
        }
     }

    private void startClientRequestHandlerThread(Socket clientSocket) {
        Thread clientHandlerThread = new Thread(new HTTPRequestHandler(clientSocket));
        clientHandlerThread.setName("Client "+ clientCount++);
        clientHandlerThread.start();
    }

    public static void main(String[] args) throws IOException {
        SimpleWebServerAsync server = new SimpleWebServerAsync(8000);
        server.start();
    }

}
