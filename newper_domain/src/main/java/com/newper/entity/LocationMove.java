package com.newper.entity;

import com.newper.constant.LmState;
import com.newper.constant.LocForm;
import com.newper.constant.LocType;
import com.newper.entity.common.CreatedEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LocationMove extends CreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lmIdx;

    @Enumerated(EnumType.STRING)
    private LmState lmState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LM_U_IDX", referencedColumnName = "uIdx")
    private User user;


    private LocalDate lmInDate;

    private LocalDate lmOutDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LM_LOC_IDX", referencedColumnName = "locIdx")
    private Location location1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LM_LOC_IDX2", referencedColumnName = "locIdx")
    private Location location2;

    private int lmCount;

    private String lmMemo;




}
