package com.dipanjal.batch1.socket.example2.async;

import com.dipanjal.batch1.socket.example2.RequestRouter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HTTPRequestHandler implements Runnable {

    private final Socket socket;
    private final RequestRouter router;

    public HTTPRequestHandler(Socket socket, RequestRouter router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        String currentThreadName = Thread.currentThread().getName();
        try (BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter response = new PrintWriter(socket.getOutputStream(), true)) {

            int lineNo = 1; String path = "/";
            String line = request.readLine();

            while(!line.isEmpty()) {
                if(lineNo == 1) {
                    path = extractPathFromLine(line);
                }
                System.out.println(lineNo+" From "+currentThreadName+": "+line);
                lineNo++;
                line = request.readLine();
            }
            router.route(path, response);
            closeClientConnection(socket, request, response);
            setDelay(5);
            System.out.println(currentThreadName+" Disconnected");
        } catch (IOException e) {
            System.out.println(currentThreadName+" Disconnected");
            closeClientConnection(socket);
        }
    }

    private void setDelay(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String extractPathFromLine(String line) {
        if(line.startsWith("GET")) {
            String[] parts = line.trim().split(" ");
            return parts[1];
        }
        return "";
    }

    private void closeClientConnection(Socket socket) {
        try{
            if(socket != null) socket.close();
        }catch (IOException e){
        }
    }

    private void closeClientConnection(Socket socket, BufferedReader request, PrintWriter response) {
        try{
            if(request != null) request.close();
            if(response != null) response.close();
            if(socket != null) socket.close();
        }catch (IOException e){
        }
    }
}
