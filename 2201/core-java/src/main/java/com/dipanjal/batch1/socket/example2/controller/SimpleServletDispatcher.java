package com.dipanjal.batch1.socket.example2.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface SimpleServletDispatcher {

    void dispatch(String path, BufferedReader request, PrintWriter response);

    default String getPageNotFoundHtml() {
        return "<html> <h1> 404 Page Not Found </h1> </html>";
    }
}
