package com.newper.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
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
public class BaseEntityWithoutBy {

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalTime createdTime;

    @LastModifiedDate
    private LocalDate modifiedDate;
    @LastModifiedDate
    private LocalTime modifiedTime;
}
