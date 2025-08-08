package com.example.examination.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationDTO {

    private String id;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Exam date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Make sure Postman uses this format
    @Future(message = "Exam date must be in the future")
    private LocalDateTime examDate;

    @Min(value = 30, message = "Duration must be at least 30 minutes")
    private int durationMinutes;

    @NotBlank(message = "Student ID is required")
    private String studentId;
}
