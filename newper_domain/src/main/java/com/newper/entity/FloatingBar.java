package com.newper.entity;

import com.newper.constant.FbType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FloatingBar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fbIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FB_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String fbName;

    private Integer fbDisplay;

    @Enumerated(EnumType.STRING)
    private FbType fbType;

    private String fbUrl;

    private String fbThumbnail;

    private String fbThumbnailMobile;

    @PrePersist
    @PreUpdate
    public void preSave() {

        if (!StringUtils.hasText(getFbThumbnail())) {
            if(fbType.equals("ETC")){
                setFbThumbnail("/default/fb_cart.png");
            }else if(fbType.equals("TEL")){
                setFbThumbnail("/default/fb_tel.png");
            }else if(fbType.equals("BASKET")){
                setFbThumbnail("/default/fb_default.png");
            }
        }
        if (!StringUtils.hasText(fbUrl)) {
            if(fbType.equals("BASKET")){
                setFbUrl("/order/cart/coupon");
            }
        }

        if (fbType.equals("RECENT")) {
            setFbDisplay(1000*fbDisplay);
        }else{
            if(fbDisplay>1000){
                fbDisplay = 999;
            }
        }
    }

    /**순서 절대값으로 가져오기*/
    public int absDisplay(){
        return Math.abs(fbDisplay);
    }
}
