package com.pangpang.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
@Audited
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
    private String modifiedBy;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @LastModifiedDate
    private LocalTime modifiedTime;


    @Transient
    private String modifiedBy_old;
    @Transient
    private LocalDate modifiedDate_old;
    @Transient
    private LocalTime modifiedTime_old;


    /** 이름(아이디)로 되어있는 것 중 이름 가져오기*/
    public String createdName(){
        return nameString(getCreatedBy());
    }
    /** 이름(아이디)로 되어있는 것 중 이름 가져오기*/
    public String modifiedName(){
        return nameString(getModifiedBy());
    }
    /** 마지막 ( 기준으로 앞에것 가져오기*/
    private String nameString(String name){
        if (name == null) {
            return "";
        }
        if (name.indexOf("(") != -1) {
            return name.substring(0, name.lastIndexOf("("));
        }
        return name;
    }

    @PreUpdate
    public void preUp(){
        //수정일시 변경X
        if(modifiedDate_old != null){
            setModifiedDate(modifiedDate_old);
            setModifiedTime(modifiedTime_old);
            setModifiedBy(modifiedBy_old);
        }
    }
    /** 수정일시 변경원하지 않는 경우 호출하는 method. ex) api통신등*/
    public void sameModified(){
        setModifiedDate_old(getModifiedDate());
        setModifiedTime_old(getModifiedTime());
        setModifiedBy_old(getModifiedBy());
    }
}
