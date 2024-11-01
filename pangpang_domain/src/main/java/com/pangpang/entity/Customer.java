package com.pangpang.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuIdx;

    private String cuName;
    private String cuNickname;
    private String cuId;
    private String cuPw;
    private String cuMail;
    private String cuTelecom;
    private String cuPhone;
    private String cuState;
    private String cuBirth;
    private String cuGender;
    private String cuMemo;
    private LocalDate cuJoinDate;
    private LocalTime cuJoinTime;
    private String cuCi;

}
