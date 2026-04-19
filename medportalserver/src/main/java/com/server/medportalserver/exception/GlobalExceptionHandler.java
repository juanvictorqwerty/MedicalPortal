package com.server.medportalserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        
        if (ex.getMessage() != null && ex.getMessage().contains("ArrayList")) {
            errorResponse.put("error", "Invalid Request Format");
            errorResponse.put("message", "This endpoint expects a JSON Array [{}], but you sent a single JSON Object {}. Please wrap your request body in square brackets [].");
        } else {
            errorResponse.put("error", "Malformed JSON Request");
            errorResponse.put("message", "The server could not read the HTTP request. Please ensure your JSON is formatted correctly.");
        }
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
