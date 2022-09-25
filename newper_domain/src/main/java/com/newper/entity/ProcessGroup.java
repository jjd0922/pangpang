package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProcessGroup extends CreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PG_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PG_U_IDX", referencedColumnName = "uIdx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PG_U_IDX2", referencedColumnName = "uIdx")
    private User user2;

    private String pgType;

    private String pgLastState;

    private String pgRequestDate;

    private String pgReqMemo;

    private String pgCheckMemo;

    private String pgDoneMemo;

    private LocalDate pgStartDate;

    private LocalDate pgEndDate;
}
