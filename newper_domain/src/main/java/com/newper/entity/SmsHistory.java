package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SmsHistory extends CreatedEntity {
    @Id
    @GeneratedValue
    private Long shIdx;

    private String shType;

    private String shTo;

    private String shFrom;

    private String shContent;

    private LocalDate shDate;

    private LocalTime shTime;

    private String shReq;

    private String shRes;

    private String shCode;





}
