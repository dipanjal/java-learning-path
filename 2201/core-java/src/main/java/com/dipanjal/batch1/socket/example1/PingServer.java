package com.dipanjal.batch1.socket.example1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class PingServer {

    private final int port;

    public PingServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening at 127.0.0.1:"+port);
//        listenAndRespond(serverSocket);
        listenAndRespondAndAutoDisconnect(serverSocket);
    }

    public void listenAndRespond(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = null;
        BufferedReader request = null;
        PrintWriter response = null;
        try{
            clientSocket = serverSocket.accept();
            System.out.println("Client connected....");
            request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            response = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while((line = request.readLine()) != null) {
                System.out.println(line);
                response.println("Response: "+line.toUpperCase());
            }
        } catch (SocketException e) {
            closeClientConnection(clientSocket, request, response);
            listenAndRespond(serverSocket);
        }
    }

    private void closeClientConnection(Socket clientSocket, BufferedReader request, PrintWriter response) throws IOException {
        if(request != null) request.close();
        if(response != null) response.close();
        if(clientSocket != null) clientSocket.close();
    }

    /**
     * Using try with Resource for auto closing Socket, BufferedReader and PrintWriter
     * Basically Socket, BufferedReader, PrintWriter implements Closeable interface
     * So when the try block exists, the resources will be automatically closed
     */
    public void listenAndRespondAndAutoDisconnect(ServerSocket serverSocket) {
        while(!serverSocket.isClosed()) {
            try(Socket socket = serverSocket.accept();
                BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter response = new PrintWriter(socket.getOutputStream(), true)) {

                String line;
                while((line = request.readLine()) != null) {
                    System.out.println(line);
                    response.println("Response: "+line.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Client Disconnected...");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PingServer pingServer = new PingServer(6000);
        pingServer.start();
    }
}
