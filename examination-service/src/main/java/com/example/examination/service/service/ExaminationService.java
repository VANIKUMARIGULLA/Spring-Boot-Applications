package com.example.examination.service.service;



import java.util.List;

import com.example.examination.service.dto.ExaminationDTO;

public interface ExaminationService {

    ExaminationDTO create(ExaminationDTO dto);

    ExaminationDTO update(String id, ExaminationDTO dto);

    ExaminationDTO partialUpdate(String id, ExaminationDTO dto);

    ExaminationDTO getById(String id);

    List<ExaminationDTO> getAll();

    ExaminationDTO delete(String id);

}
