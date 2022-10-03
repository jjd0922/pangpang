package com.newper.entity;

import com.newper.constant.HmType;
import com.newper.entity.common.CreatedEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HeaderMenu extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hmIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HM_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    @Enumerated(EnumType.STRING)
    private HmType hmType;
    private String hmName;
    private String hmUrl;
    private String hmClass;
    private byte hmOrder;

    /** 헤더메뉴 클릭시 이동할 url return */
    public String getUrl(){
        if (getHmType().isBasic()) {
            return hmType.getUrl();
        }else{
            return hmUrl;
        }
    }
    /** url 일치하는 경우 active class추가 . url;jsession 처럼 ;오는 경우는 처리 안됨. */
    public String getClass(String url){
        if(url.equals(getUrl())){
            return hmClass+" active";
        }
        return hmClass;
    }

    public void updateHmOrder(int hmOrder) {
        if(getHmOrder() < 0){
            setHmOrder((byte) (hmOrder * -1));
        }else{
            setHmOrder((byte) hmOrder);
        }
    }

    @PrePersist
    @PreUpdate
    public void preSave(){
        if(!StringUtils.hasText(getHmName())){
            throw new MsgException("메뉴명을 입력해주세요.");
        }else if(!StringUtils.hasText(getUrl())){
            throw new MsgException("URL을 입력해주세요.");
        }
    }
}
