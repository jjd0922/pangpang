package com.newper.repository;

import com.newper.entity.TemplateForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateFormRepo extends JpaRepository<TemplateForm,Integer> {

    public TemplateForm findTemplateFormBytfIdx(Integer tfIdx);



}
