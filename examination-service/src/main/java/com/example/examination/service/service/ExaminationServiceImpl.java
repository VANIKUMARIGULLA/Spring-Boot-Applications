package com.example.examination.service.service;


import com.example.examination.service.dto.ExaminationDTO;

import com.example.examination.service.entity.Examination;
import com.example.examination.service.repository.ExaminationRepository;
import com.example.examination.service.exception.ExaminationNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExaminationServiceImpl implements ExaminationService {

	@Autowired
    private ExaminationRepository repository;


    @Override
    public ExaminationDTO create(ExaminationDTO dto) {
        validateInput(dto);

        Examination entity = Examination.builder()
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .examDate(dto.getExamDate())
                .durationMinutes(dto.getDurationMinutes())
                .studentId(dto.getStudentId())
                .build();

        Examination saved = repository.save(entity);
        log.info("Examination created with ID: {}", saved.getId());

        return convertToDTO(saved);
    }

    @Override
    public ExaminationDTO update(String id, ExaminationDTO dto) {
        validateInput(dto);

        Examination existing = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Examination not found for update. ID: {}", id);
                    return new ExaminationNotFoundException("Examination not found with ID: " + id);
                });

        BeanUtils.copyProperties(dto, existing, "id");

        Examination updated = repository.save(existing);
        log.info("Examination updated with ID: {}", updated.getId());

        return convertToDTO(updated);
    }

    @Override
    public ExaminationDTO partialUpdate(String id, ExaminationDTO dto) {
        Examination existing = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Examination not found for partial update. ID: {}", id);
                    return new ExaminationNotFoundException("Examination not found with ID: " + id);
                });

        if (dto.getSubject() != null) existing.setSubject(dto.getSubject());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getExamDate() != null) existing.setExamDate(dto.getExamDate());
        if (dto.getDurationMinutes() > 0) existing.setDurationMinutes(dto.getDurationMinutes());
        if (dto.getStudentId() != null) existing.setStudentId(dto.getStudentId());

        Examination updated = repository.save(existing);
        log.info("Partially updated examination with ID: {}", updated.getId());

        return convertToDTO(updated);
    }

    @Override
    public ExaminationDTO getById(String id) {
        log.debug("Fetching examination by ID: {}", id);
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> {
                    log.error("Examination not found for ID: {}", id);
                    return new ExaminationNotFoundException("Examination not found with ID: " + id);
                });
    }

    @Override
    public List<ExaminationDTO> getAll() {
        log.debug("Fetching all examinations");
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExaminationDTO delete(String id) {
        Examination exam = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Examination not found for deletion. ID: {}", id);
                    return new ExaminationNotFoundException("Examination not found with ID: " + id);
                });

        ExaminationDTO dto = convertToDTO(exam); // Capture before deletion
        repository.delete(exam);
        log.warn("Deleted examination with ID: {}", id);

        return dto;
    }

    // Convert Entity to DTO
    private ExaminationDTO convertToDTO(Examination entity) {
        return ExaminationDTO.builder()
                .id(entity.getId())
                .subject(entity.getSubject())
                .description(entity.getDescription())
                .examDate(entity.getExamDate())
                .durationMinutes(entity.getDurationMinutes())
                .studentId(entity.getStudentId())
                .build();
    }

    // Extra server-side validation (in addition to controller-level @Valid)
    private void validateInput(ExaminationDTO dto) {
        if (dto.getDurationMinutes() <= 0) {
            log.warn("Invalid duration: {}", dto.getDurationMinutes());
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        if (dto.getSubject() == null || dto.getSubject().trim().isEmpty()) {
            log.warn("Subject is required but missing");
            throw new IllegalArgumentException("Subject is required");
        }
    }
}
