package com.example.examination.service.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "exam_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Examination {
    
    @Id
    private String id;

    private String subject;

    private String description;

    private LocalDateTime examDate;

    private int durationMinutes;

    private String studentId; // reference to student (optional)
}

