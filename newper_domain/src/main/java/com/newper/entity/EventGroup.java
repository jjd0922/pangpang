package com.newper.entity;

import com.newper.constant.EgMenu;
import com.newper.constant.EgType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long egIdx;

    /** 분양몰 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EG_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    /** 이벤트 메뉴*/
    @Enumerated(EnumType.STRING)
    private EgMenu egMenu;
    /** 이벤트 타입*/
    @Enumerated(EnumType.STRING)
    private EgType egType;
    /** 이벤트그룹명*/
    private String egTitle;
    /** 이벤트 내용 - 에디터 사용*/
    private String egContent;
    /** 노출 상태*/
    private boolean egState;

    /** 시작일*/
    private LocalTime egOpenTime;
    private LocalDate egOpenDate;
    /** 종료일*/
    private LocalTime egCloseTime;
    private LocalDate egCloseDate;
    /** 메모*/
    private String egMemo;

    /** 이벤트 그룹 카테고리 */
    @OneToMany(mappedBy = "eventGroup", cascade = CascadeType.ALL)
    @OrderBy(value = "ecOrder asc")
    @Builder.Default
    private List<EventCategory> eventCategoryList = new ArrayList<>();

    /**
     * key : value
     * EG_THUMBNAIL_WEB : 썸네일파일(WEB)
     * EG_THUMBNAIL_MOBILE : 썸네일파일(MOBILE)
     * */
    @Builder.Default
    private Map<String,Object> egJson = new HashMap<>();

}
