package com.newper.entity;


import com.newper.constant.SState;
import com.newper.constant.SType;
import com.newper.converter.ConvertList;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
/**영업활동*/
public class Schedule {
    
    /**영업활동 IDX*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer	sIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "S_COM_IDX", referencedColumnName = "comIdx")
    private Company company;
    private String sComName;
    private String sComNum;

    @Enumerated(EnumType.STRING)
    private SState sState;
    @Enumerated(EnumType.STRING)
    private SType sType;

    @Builder.Default
    @Convert(converter = ConvertList.class)
    private List<Map<String, String>> sCheck  = new ArrayList<>();

    private String sTitle;
    private short sCount;
    private String sAttendees;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate sRequestDate;

    private LocalDate sDate;
    private LocalTime sTime;
    private LocalDate sCompletionDate;
    private LocalTime sCompletionTime;

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
        } else if (getSCompletionDate()!=null && getSDate().isAfter(getSCompletionDate())) {
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
        setCompany(newSchedule.getCompany());
        setSComName(newSchedule.getSComName());
        setSComNum(newSchedule.getSComNum());
        setSState(newSchedule.getSState());
        setSType(newSchedule.getSType());
        setSCheck(newSchedule.getSCheck());
        setSTitle(newSchedule.getSTitle());
        setSCount(newSchedule.getSCount());
        setSAttendees(newSchedule.getSAttendees());
        setSRequestDate(newSchedule.getSRequestDate());
        setSDate(newSchedule.getSDate());
        setSTime(newSchedule.getSTime());
        setSCompletionDate(newSchedule.getSCompletionDate());
        setSCompletionTime(newSchedule.getSCompletionTime());
        setSContent(newSchedule.getSContent());
    }
}
