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
public class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalTime createdTime;

    @LastModifiedBy
    @Column(updatable = false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDate modifiedDate;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalTime modifiedTime;

}
