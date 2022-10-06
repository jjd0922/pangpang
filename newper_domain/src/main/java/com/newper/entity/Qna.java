package com.newper.entity;

import com.newper.constant.QnaType1;
import com.newper.constant.QnaType2;
import com.newper.entity.common.BaseEntityWithoutBy;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Qna extends BaseEntityWithoutBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_OGG_IDX", referencedColumnName = "oggIdx")
    private OrderGsGroup orderGsGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_QNAA_IDX", referencedColumnName = "qnaaIdx")
    private QnaAnswer qnaAnswer;

    @Enumerated(EnumType.STRING)
    private QnaType1 qnaType1;
    @Enumerated(EnumType.STRING)
    private QnaType2 qnaType2;
    private String qnaMail;
    private boolean qnaMailAlarm;
    private String qnaPhone;
    private boolean qnaPhoneAlarm;
    private String qnaTitle;
    private String qnaContent;
    private List<String> qnaJson;

}
