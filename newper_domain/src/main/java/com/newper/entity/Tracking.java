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
    private Long teIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "te_dn_idx", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;

    private String teType;
    @Column(insertable = false, updatable = false)
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
