package com.example.restapi.advice;

import com.example.restapi.exception.InvalidArgumentException;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.model.response.ApiError;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundHandler(RecordNotFoundException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<?> badRequestHandler(InvalidArgumentException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> otherRunTimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> requestBodyValidationExceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        List<String> errorMessageList = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiError.<String>builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .errors(errorMessageList)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> commonExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body("Internal Server error");
    }
}
