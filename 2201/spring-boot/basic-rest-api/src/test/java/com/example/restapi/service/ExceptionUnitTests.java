package com.example.restapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionUnitTests {

    @Test
    public void nullPointerTestException() {
        String expected = "Jhon";
        String actual = null;
        Executable myExceptionHandler = new Executable() {
            @Override
            public void execute() throws Throwable {
                actual.equals(expected);
            }
        };

        assertThrows(NullPointerException.class, () -> actual.equals(expected));
    }
}
