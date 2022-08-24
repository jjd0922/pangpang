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
public class CheckGroup extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cgIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_CHECK_IDX", referencedColumnName = "checkIdx")
    private Check check;

    private String cgState;

    private LocalDate cgRequestDate;

    private String cgReqMemo;

    private String cgCheckMemo;

    private String cgDoneMemo;

    private LocalDate cgStartDate;

    private LocalDate cgEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_U_IDX", referencedColumnName = "uIdx")
    private User user;


}
