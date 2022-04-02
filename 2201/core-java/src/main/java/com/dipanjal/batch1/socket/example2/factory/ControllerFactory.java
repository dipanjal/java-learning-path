package com.dipanjal.batch1.socket.example2.factory;

import com.dipanjal.batch1.socket.example2.controller.HTMLController;
import com.dipanjal.batch1.socket.example2.controller.JSONController;
import com.dipanjal.batch1.socket.example2.controller.SimpleServletDispatcher;

import java.util.concurrent.ConcurrentHashMap;

public class ControllerFactory {

    private static final ConcurrentHashMap<String, SimpleServletDispatcher> controllerMap;
    public static final String API_CONTROLLER_PREFIX;
    public static SimpleServletDispatcher viewDispatcher;
    static {
        API_CONTROLLER_PREFIX = "/api";

        controllerMap = new ConcurrentHashMap<>();
        viewDispatcher = new HTMLController();

        populateControllerMap();
    }

    public static SimpleServletDispatcher getController(String path) {
        String pathPrefix = getPathPrefix(path);
        return controllerMap.getOrDefault(pathPrefix, viewDispatcher);
    }

    private static void populateControllerMap() {
        controllerMap.put(API_CONTROLLER_PREFIX, new JSONController());
    }

    private static String getPathPrefix(String path) {
        return "/" + path.strip().split("/")[1];
    }
}
