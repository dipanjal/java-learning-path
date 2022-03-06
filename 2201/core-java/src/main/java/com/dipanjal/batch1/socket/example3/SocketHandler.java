package com.dipanjal.batch1.socket.example3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private final Socket socket;

    public SocketHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        try (BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter response = new PrintWriter(socket.getOutputStream(), true)) {
            String line;
            while((line = request.readLine()) != null) {
                System.out.println("From "+currentThreadName+": "+line);
                response.println("Response: "+line.toUpperCase());
            }
        } catch (IOException e) {
            System.out.println(currentThreadName+" Disconnected");
            closeClientConnection(socket);
        }
    }

    private void closeClientConnection(Socket clientSocket) {
        try{
            if(clientSocket != null)
                clientSocket.close();
        }catch (IOException e){
            System.out.println("Error occurred while disconnecting client socket");
        }
    }
}
