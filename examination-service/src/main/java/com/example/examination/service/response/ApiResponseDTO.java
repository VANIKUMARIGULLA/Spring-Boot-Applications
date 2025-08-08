package com.example.examination.service.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private int statusCode; // <-- for 200, 400, 404, etc.
    private String status;  // <-- for OK, BAD_REQUEST, etc.

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private T data;
}

