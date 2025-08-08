package com.example.examination.service.util;

import org.springframework.http.HttpStatus;

import com.example.examination.service.response.ApiResponseDTO;

import java.time.LocalDateTime;

public class ResponseBuilder {

	public static <T> ApiResponseDTO<T> buildSuccessResponse(String message, T data, HttpStatus status) {
	    return ApiResponseDTO.<T>builder()
	            .success(true)
	            .message(message)
	            .statusCode(status.value())       // 200
	            .status(status.name())            // "OK"
	            .timestamp(getCurrentTimestamp())
	            .data(data)
	            .build();
	}



    public static <T> ApiResponseDTO<T> buildFailureResponse(String message, HttpStatus status, T data) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .statusCode(status.value()) // ✅ convert HttpStatus to int
                .timestamp(getCurrentTimestamp())
                .data(data)
                .build();
    }

    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}

