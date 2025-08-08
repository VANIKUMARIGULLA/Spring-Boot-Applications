package com.example.examination.service.controller;

import com.example.examination.service.config.ExaminationMessageProperties;
import com.example.examination.service.dto.ExaminationDTO;
import com.example.examination.service.response.ApiResponseDTO;
import com.example.examination.service.service.ExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.examination.service.util.ResponseBuilder.buildSuccessResponse;

/**
 * Controller for handling examination-related HTTP requests.
 * Supports CRUD operations with proper logging and response formatting.
 */
@Slf4j
@RestController
@RequestMapping("/api/exam_results")
public class ExaminationController {

    // Service layer dependency for business logic
	@Autowired
    private ExaminationService examinationService;

    // Configurable messages from application.yml
	@Autowired
    private ExaminationMessageProperties messages;

    /**
     * Create a new examination.
     *
     * @param dto Examination data to be created
     * @return ResponseEntity with created examination and 201 status
     */
    @Operation(summary = "Create Examination", description = "Creates a new examination record in the database.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Examination created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ExaminationDTO>> createExamination(
            @Valid @RequestBody ExaminationDTO dto) {

        log.info("Creating examination: {}", dto);
        ExaminationDTO created = examinationService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildSuccessResponse(messages.getCreated(), created, HttpStatus.CREATED));
    }

    /**
     * Get all examinations.
     *
     * @return ResponseEntity with list of all examinations and 200 status
     */
    @Operation(summary = "Get All Examinations", description = "Fetches all examination records from the database.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Examinations fetched successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ExaminationDTO>>> getAllExaminations() {

        log.info("Fetching all examinations");
        List<ExaminationDTO> allExams = examinationService.getAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildSuccessResponse(messages.getFetchedAll(), allExams, HttpStatus.OK));
    }

    /**
     * Get examination by ID.
     *
     * @param id Examination ID
     * @return ResponseEntity with examination and 200 status
     */
    @Operation(summary = "Get Examination By ID", description = "Fetches a specific examination by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Examination fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Examination not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ExaminationDTO>> getExaminationById(@PathVariable String id) {

        log.info("Fetching examination by ID: {}", id);
        ExaminationDTO dto = examinationService.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildSuccessResponse(messages.getFetchedById(), dto, HttpStatus.OK));
    }

    /**
     * Fully update an examination.
     *
     * @param id  Examination ID
     * @param dto Updated examination data
     * @return ResponseEntity with updated examination and 200 status
     */
    @Operation(summary = "Update Examination", description = "Fully updates an examination record.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Examination updated successfully"),
        @ApiResponse(responseCode = "404", description = "Examination not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ExaminationDTO>> updateExamination(
            @PathVariable String id,
            @Valid @RequestBody ExaminationDTO dto) {

        log.info("Updating examination with ID: {}", id);
        ExaminationDTO updated = examinationService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildSuccessResponse(messages.getUpdated(), updated, HttpStatus.OK));
    }

    /**
     * Partially update an examination.
     *
     * @param id  Examination ID
     * @param dto Fields to update
     * @return ResponseEntity with patched examination and 200 status
     */
    @Operation(summary = "Patch Examination", description = "Partially updates an examination record.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Examination patched successfully"),
        @ApiResponse(responseCode = "404", description = "Examination not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ExaminationDTO>> partialUpdate(
            @PathVariable String id,
            @RequestBody ExaminationDTO dto) {

        log.info("Patching examination with ID: {}", id);
        ExaminationDTO patched = examinationService.partialUpdate(id, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildSuccessResponse(messages.getPatched(), patched, HttpStatus.OK));
    }

    /**
     * Delete an examination.
     *
     * @param id Examination ID
     * @return ResponseEntity with deleted ID and 200 status
     */
    @Operation(summary = "Delete Examination", description = "Deletes an examination record by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Examination deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Examination not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteExamination(@PathVariable String id) {

        log.info("Deleting examination with ID: {}", id);
        examinationService.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildSuccessResponse(messages.getDeleted(), id, HttpStatus.OK));
    }
}
