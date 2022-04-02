package com.dipanjal.batch1.socket.example2.controller;

import com.dipanjal.batch1.annotation.MyController;
import com.dipanjal.batch1.socket.example2.factory.ControllerFactory;
import com.dipanjal.batch1.socket.example2.model.ApiError;
import com.dipanjal.batch1.socket.example2.model.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.charset.CharsetEncoder;
import java.util.List;

//@MyController
public class JSONController implements SimpleServletDispatcher {

    private final String GET_USER;
    private final String GET_USERS;

    {
        GET_USER = ControllerFactory.API_CONTROLLER_PREFIX + "/user";
        GET_USERS = ControllerFactory.API_CONTROLLER_PREFIX + "/users";
    }

    @Override
    public void dispatch(String path, BufferedReader request, PrintWriter response) {
        response.println("HTTP/1.1 200 OK");
        response.println("Content-Type: application/json");
        response.println("charset=UTF-8");
        response.println(); // \r\n

        try {
            if(path.equals(GET_USER))
                response.println(new UserDTO(1, "Test User", 25));
            else if (path.equals(GET_USERS))
                response.println(getUsers());
            else
                response.println(getErrorResponse("Record not found"));
        } catch (JsonProcessingException e) {
            response.println(getErrorResponse("Internal Server Error"));
        }
    }

    private String getUser() throws JsonProcessingException {
        return getJsonFromDTO(
                new UserDTO(1, "Kazuki Yamashita", 27)
        );
    }

    private String getUsers() throws JsonProcessingException {
        return getJsonFromDTO(
                List.of(
                        new UserDTO(1, "Kazuki Yamashita", 27),
                        new UserDTO(2, "Athena", 20),
                        new UserDTO(3, "Iori", 25)
                )
        );
    }

    private String getErrorResponse(String message) {
        try{
            return getJsonFromDTO(new ApiError(message));
        } catch (JsonProcessingException exception) {
            return "Failed";
        }
    }

    private <T> String getJsonFromDTO(T dto) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dto);
    }

    private <T> String getJsonFromDTO(List<T> dtoList) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dtoList);
    }
}
