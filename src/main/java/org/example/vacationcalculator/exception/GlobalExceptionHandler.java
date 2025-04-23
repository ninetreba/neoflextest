package org.example.vacationcalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Bad Request",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationError(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        var fieldError = ex.getBindingResult().getFieldError();
        String message = (fieldError != null) ? fieldError.getDefaultMessage() : "Validation error";

        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Validation Failed",
                "message", message
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralError(Exception ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Internal Server Error",
                "message", "Unexpected error: " + ex.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}