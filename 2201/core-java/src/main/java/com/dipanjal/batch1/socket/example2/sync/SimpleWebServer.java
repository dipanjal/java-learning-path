package com.dipanjal.batch1.socket.example2.sync;

import com.dipanjal.batch1.socket.example2.RequestRouter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;

public class SimpleWebServer {

    private final int port;
    private final RequestRouter router;
    private int clientCount = 1;

    public SimpleWebServer(int port){
        this.port = port;
        this.router = new RequestRouter();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Web Server Started...");
        listenAndRespond(serverSocket);
    }

    private void listenAndRespond(ServerSocket serverSocket) throws IOException {
        Socket client = null;
        BufferedReader request = null;
        PrintWriter response = null;

        while (!serverSocket.isClosed()) {
            try {
                client = serverSocket.accept(); //accepting the clients
                System.out.printf("Client (%d) connected at %s%n", clientCount++, LocalDateTime.now());
                request = new BufferedReader(new InputStreamReader(client.getInputStream()));
                int lineNo = 1;
                String line;
                String path = "";
                while((line = request.readLine()) != null && !line.isEmpty()) {
                    if(lineNo == 1) {
                        path = extractPathFromLine(line);
                    }
                    System.out.println("From Client: "+line);
                    lineNo++;
                }
                response = new PrintWriter(client.getOutputStream(), true);
                router.route(path, response);
                setDelay(5);
                closeClientConnection(client, request, response);
            } catch (SocketException se) {
                closeClientConnection(client, request, response);
                System.out.println("--------------");
            }
        }
    }

    private String extractPathFromLine(String line) {
        if(line.startsWith("GET")) {
            String[] parts = line.trim().split(" ");
            return parts[1];
        }
        return "";
    }

    private void closeClientConnection(Socket client, BufferedReader request, PrintWriter response) throws IOException {
        System.out.println("Client disconnected");
        if(request != null) request.close();
        if(response != null) response.close();
        if(client != null) client.close();
    }

    private void closeClientConnection(Socket client) throws IOException {
        System.out.println("Client disconnected");
        if(client != null) client.close();
    }

    private void setDelay(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleWebServer webServer = new SimpleWebServer(5000);
        webServer.start();;
    }
}
