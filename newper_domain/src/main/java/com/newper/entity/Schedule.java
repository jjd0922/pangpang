package com.newper.entity;


import com.newper.constant.SState;
import com.newper.constant.SType;
import com.newper.converter.ConvertList;
import com.newper.entity.common.CreatedEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
/**영업활동*/
public class Schedule extends CreatedEntity {
    
    /**영업활동 IDX*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer	sIdx;

    private String sComName;
    private String sComNum;

    @Enumerated(EnumType.STRING)
    private SState sState;
    @Enumerated(EnumType.STRING)
    private SType sType;

    @Builder.Default
    private List<Map<String, String>> sCheck  = new ArrayList<>();

    private String sTitle;
    private short sCount;
    private String sAttendees;

    private LocalDate sDate;
    private LocalTime sTime;
    private LocalDate sDoneDate;
    private LocalTime sDoneTime;

    private String sContent;
    private String sFile1;
    private String sFileName1;
    private String sFile2;
    private String sFileName2;
    private String sFile3;
    private String sFileName3;

    @PreUpdate
    @PrePersist
    public void preSave() { 
        if (getSState() == null) {
            throw new MsgException("영업활동상태를 입력해주세요.");
        } else if (!StringUtils.hasText(getSComName())) {
            throw new MsgException("업체명을 입력해주세요");
        } else if (getSDate() == null) {
            throw new MsgException("유효한 미팅일을 설정해 주세요.");
        } else if (getSDoneDate()!=null && getSDate().isAfter(getSDoneDate())) {
            throw new MsgException("유효한 미팅일을 설정해 주세요.");
        } else if (getSTime() == null) {
            throw new MsgException("유효한 미팅시각을 설정해 주세요.");
        } else if (getSType() == null) {
            throw new MsgException("미팅구분을 입력해주세요.");
        } else if (!StringUtils.hasText(getSTitle())) {
            throw new MsgException("미팅명을 입력해주세요.");
        } else if (!StringUtils.hasText(getSAttendees())) {
            throw new MsgException("미팅참석자를 입력해주세요.");
        }
    }

    /**스케줄 수정(일정변경)시 파일 그대로*/
    public void rescheduled(Schedule oldSchedule) {
        setSFile1(oldSchedule.getSFile1());
        setSFileName1(oldSchedule.getSFileName1());
        setSFile2(oldSchedule.getSFile2());
        setSFileName2(oldSchedule.getSFileName2());
        setSFile3(oldSchedule.getSFile3());
        setSFileName3(oldSchedule.getSFileName3());
    }

    /**스케줄 수정*/
    public void updateSchedule(Schedule newSchedule) {
        setSComName(newSchedule.getSComName());
        setSComNum(newSchedule.getSComNum());
        setSState(newSchedule.getSState());
        setSType(newSchedule.getSType());
        setSCheck(newSchedule.getSCheck());
        setSTitle(newSchedule.getSTitle());
        setSCount(newSchedule.getSCount());
        setSAttendees(newSchedule.getSAttendees());
        setSDate(newSchedule.getSDate());
        setSTime(newSchedule.getSTime());
        setSDoneDate(newSchedule.getSDoneDate());
        setSDoneTime(newSchedule.getSDoneTime());
        setSContent(newSchedule.getSContent());
    }
}
