package com.dipanjal.batch1.socket.example2.async;

import com.dipanjal.batch1.socket.example2.RequestRouter;
import com.dipanjal.batch1.socket.example2.controller.SimpleServletDispatcher;
import com.dipanjal.batch1.socket.example2.factory.ControllerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HTTPRequestHandler implements Runnable {

    private final Socket socket;

    public HTTPRequestHandler(Socket socket) {
        this.socket = socket;
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
            this.route(path, request, response);
            setDelay(5);

            System.out.println(currentThreadName+" Disconnected");
        } catch (IOException e) {
            System.out.println(currentThreadName+" Disconnected");
            closeClientConnection(socket);
        }
    }

    private void route(String path, BufferedReader request, PrintWriter response) {
        SimpleServletDispatcher controller = ControllerFactory.getController(path);
        System.out.println(controller);
        controller.dispatch(path, request, response);
        closeClientConnection(socket, request, response);
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
