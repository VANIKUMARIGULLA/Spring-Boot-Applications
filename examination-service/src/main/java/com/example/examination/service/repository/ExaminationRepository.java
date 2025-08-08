package com.example.examination.service.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.examination.service.entity.Examination;

@Repository
public interface ExaminationRepository extends MongoRepository<Examination, String> {
}
