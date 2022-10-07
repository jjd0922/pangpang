package com.newper.entity;

import com.newper.constant.QspType;
import com.newper.entity.common.BaseEntityWithoutBy;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QnaSp extends BaseEntityWithoutBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qspIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QSP_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QSP_SP_IDX", referencedColumnName = "spIdx")
    private ShopProduct shopProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QSP_QNAA_IDX", referencedColumnName = "qnaaIdx")
    private QnaAnswer qnaAnswer;

    @Enumerated(EnumType.STRING)
    private QspType qspType;
    private String qspTitle;
    private String qspContent;
    private boolean qspSecret;

    /** 상품문의 수정 */
    public void updateQsp(QnaSp newQsp) {

    }

    /** 작성일 format '-' > '.' 변환*/
    public String getDateFormatDot() {
        return getCreatedDate().toString().replaceAll("-", ".");
    }
}
