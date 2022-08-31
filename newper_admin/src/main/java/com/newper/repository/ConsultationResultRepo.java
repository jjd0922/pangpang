package com.newper.repository;

import com.newper.entity.ConsultationResult;
import com.newper.entity.TemplateForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationResultRepo extends JpaRepository<ConsultationResult, Integer> {

    public ConsultationResult findConsultationResultBycrIdx(Integer crIdx);
}
