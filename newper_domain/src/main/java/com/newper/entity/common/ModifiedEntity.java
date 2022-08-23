package com.newper.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class ModifiedEntity {

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @LastModifiedDate
    private LocalTime modifiedTime;

}
