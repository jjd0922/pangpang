package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@DynamicUpdate
@Table(name = "GMP_TRACKING")
public class Tracking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GMP_TRACKING_SEQ")
    @SequenceGenerator(name = "GMP_TRACKING_SEQ", allocationSize = 1)
    private Long teIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "te_d_idx", referencedColumnName = "dIdx")
    private DeliveryNum deliveryNum;

    private String teType;
    @Column(insertable = false, updatable = false)
    @ColumnTransformer(read = "MGP.GET_CODE_NAME('TE_TYPE', TE_TYPE)")
    private String teTypeName;

    /**구분값 - 송장번호*/
    private String teNum;

    /**API URI*/
    private String teUrl;

    /**요청*/
    private String teReq;

    /**응답*/
    private String teRes;


    /**트래킹 fids - 추적요청시 송장별로 고유한 값으로 처리*/
    public String getFid(){
        return "ST"+teIdx;
    }
}
