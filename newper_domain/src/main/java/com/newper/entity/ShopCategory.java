package com.newper.entity;

import com.newper.constant.CateSpec;
import com.newper.converter.ConvertList;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShopCategory extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scateIdx;
    

    private String scateName;
    private String scateNick;
    private String scateIcon;
    private String scateThumbnail;
    private String scateImage;
    private int scateOrder;



    /** order 업데이트.*/
    public void updateShopCategoryOrder(int scateOrder){
        if(getScateOrder() < 0){
            setScateOrder(scateOrder * -1);
        }else{
            setScateOrder(scateOrder);
        }
    }



}


