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
@Table(name = "TRACKING")
public class Tracking extends BaseEntity {

    @Id
    private Long traIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tra_dn_idx", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;

    private String traType;
    @Column(insertable = false, updatable = false)
    private String traTypeName;

    /**구분값 - 송장번호*/
    private String traNum;

    /**API URI*/
    private String traUrl;

    /**요청*/
    private String traReq;

    /**응답*/
    private String traRes;


    /**트래킹 fids - 추적요청시 송장별로 고유한 값으로 처리*/
    public String getFid(){
        return "ST"+traIdx;
    }
}
