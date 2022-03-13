package com.dipanjal.batch1.socket.example2.controller;

import com.dipanjal.batch1.socket.example2.factory.ControllerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class HTMLController implements SimpleServletDispatcher {

    private final String USER_HOME;
    private final String USER_DASHBOARD;

    {
        USER_HOME = "/user";
        USER_DASHBOARD = "/user/dashboard";
    }

    @Override
    public void dispatch(String path, BufferedReader request, PrintWriter response) {
        response.println("HTTP/1.1 200 OK");
        response.println("Content-Type: text/html");
        response.println(); // \r\n

        if(path.equals(USER_HOME)) {
            response.println(getUserIndex());
        }else if (path.equals(USER_DASHBOARD)){
            response.println(getUserDashboard());
        }else {
            response.println(getPageNotFoundHtml());
        }
    }

    public String getUserIndex() {
        return "<html> <h1> User Index Page </h1> </html>";
    }

    public String getUserDashboard() {
        return "<html> <h1> User Dashboard Page </h1> </html>";
    }

}
