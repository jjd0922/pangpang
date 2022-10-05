package com.newper.entity;

import com.newper.constant.EgMenu;
import com.newper.constant.EgType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EventGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long egIdx;

    /** 분양몰 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EG_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    /** 이벤트그룹명*/
    private String egName;
    /** 썸네일 (WEB)*/
    private String egThumbnailWeb;
    /** 썸네일 (MOBILE)*/
    private String egThumbnailMobile;
    /** 이벤트 타입*/
    @Enumerated(EnumType.STRING)
    private EgType egType;
    /** 이벤트 메뉴*/
    @Enumerated(EnumType.STRING)
    private EgMenu egMenu;

    /** 시작일*/
    private LocalTime egOpenTime;
    private LocalDate egOpenDate;
    /** 종료일*/
    private LocalTime egCloseTime;
    private LocalDate egCloseDate;
    /** 이벤트 내용 - 에디터 사용*/
    private String egContent;

//    @Builder.Default
//    private Map<String,Object> egJson = new HashMap<>();

}
