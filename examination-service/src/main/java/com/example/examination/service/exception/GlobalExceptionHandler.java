package com.example.examination.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.examination.service.response.ApiResponseDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the Examination Service.
 * Captures and returns formatted error responses for validation and system exceptions.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors triggered by @Valid annotations.
     *
     * @param ex the MethodArgumentNotValidException
     * @return ResponseEntity with error messages and BAD_REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );

        log.warn("Validation failed: {}", errors);

        ApiResponseDTO<Map<String, String>> response = ApiResponseDTO.<Map<String, String>>builder()
                .success(false)
                .message("Validation Failed")
                .data(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all unhandled exceptions.
     *
     * @param ex the generic Exception
     * @return ResponseEntity with internal server error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAll(Exception ex) {
        log.error("Internal Server Error: {}", ex.getMessage(), ex);

        ApiResponseDTO<String> response = ApiResponseDTO.<String>builder()
                .success(false)
                .message("Internal Server Error: " + ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
