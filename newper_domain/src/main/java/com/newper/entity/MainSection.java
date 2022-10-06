package com.newper.entity;

import com.newper.constant.MsType;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
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
public class MainSection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MS_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;
    /** 메인섹션명 */
    private String msName;
    /** 메인섹션 부제목*/
    private String msSubName;
    /** 순서*/
    private int msOrder;
    /** 섹션타입. 기획인 경우 배너,상품그룹의 순서 100자리수를 열로 사용 */
    @Enumerated(EnumType.STRING)
    private MsType msType;
    /**
     * key : value
     *
     * DISPLAY_TYPE : 디자인 타입 -> mainsection_msIdx 에서 value 확인
     * */
    @Builder.Default
    private Map<String,Object> msJson = new HashMap<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainSection", cascade = CascadeType.ALL)
    @Builder.Default
    @OrderBy(value = "msbnOrder asc")
    private List<MainSectionBanner> mainSectionBanners = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainSection", cascade = CascadeType.ALL)
    @Builder.Default
    @OrderBy(value = "msspOrder asc")
    private List<MainSectionSp> mainSectionSps = new ArrayList<>();

    @PreUpdate
    @PrePersist
    public void preSave(){
        if(getShop().getShopIdx() == null){
            throw new MsgException("분양몰을 선택해주세요;");
        }else if(!StringUtils.hasText(getMsName())){
            throw new MsgException("섹션 타이틀을 입력해주세요;");
        }

        if(msOrder == 0){
            msOrder =1;
        }
    }

    public void updateMsOrder(int msOrder) {
        if(getMsOrder() < 0){
            setMsOrder(msOrder * -1);
        }else{
            setMsOrder(msOrder);
        }
    }
}
