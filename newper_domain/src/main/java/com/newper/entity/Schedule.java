package com.newper.entity;


import com.newper.constant.SState;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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

    @Enumerated(EnumType.STRING)
    private SState sState;

    private String sCheck;
    private String sTitle;
    private short sCount;
    private String sAttendees;
    private LocalDate sRequestDate;
    private LocalDate sDate;
    private LocalTime sTime;
    private LocalDate sCompletionDate;
    private LocalTime sCompletionTime;

}
