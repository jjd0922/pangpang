package com.newper.entity;

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
public class HeaderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hmIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HM_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String hmName;
    private String hmUrl;
    private String hmClass;
    private byte hmOrder;

    /** 헤더메뉴 클릭시 이동할 url return */
    public String getUrl(){
        if (StringUtils.hasText(hmUrl)) {
            return hmUrl;
        }else{
            return "/hm/"+hmIdx;
        }
    }
    /** url 일치하는 경우 active class추가 . url;jsession 처럼 ;오는 경우는 처리 안됨. */
    public String getClass(String url){
        if(url.equals(getUrl())){
            return hmClass+" active";
        }
        return hmClass;
    }

}