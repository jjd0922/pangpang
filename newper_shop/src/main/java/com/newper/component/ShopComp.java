package com.newper.component;

import com.newper.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 분양몰 정보 저장하는 class*/
@Component
@Getter
@Setter
public class ShopComp {


    /** key domain, value shop info*/
    private Map<String, Shop> shopMap = new HashMap<>();

    /** 도메인으로 header메뉴 가져오기*/
    public List<HeaderMenu> getHeaderMenu(String domain){
        return shopMap.get(domain).getHeaderMenulist();
    }

    /** 분양몰 컬러 */
    private Map<String,Object> shopColorMap;
    /** 분양몰 디자인 */
    private String shopDesign;
    /** 전시 대분류 */
    private List<ShopCategory> shopCategoryList;
    /** 메인섹션 */
    private List<MainSection> mainSectionList;
    /** 도메인으로 headerOrder 가져오기*/
    public List<HeaderOrder> getHeaderOrder(String domain){return shopMap.get(domain).getHeaderOrderList(); }

    /** 분양몰 디자인 class*/
    public String setShopDesignClass(Map<String, Object> shopDesignMap){
        String FONT_SIZE = " ";
        String SPACES =" ";
        String BTN =" ";
        String EDGE =" ";
        String THUMBNAIL =" ";
        String FOOTER =" ";
        String SEARCH_BRAND =" ";
        if(shopDesignMap.get("FONT_SIZE") != null && !shopDesignMap.get("FONT_SIZE").equals("")){
            FONT_SIZE = (String)shopDesignMap.get("FONT_SIZE");
        }
        if(shopDesignMap.get("SPACES") != null && !shopDesignMap.get("SPACES").equals("")){
            SPACES = (String)shopDesignMap.get("SPACES");
        }
        if(shopDesignMap.get("BTN") != null && !shopDesignMap.get("BTN").equals("")){
            BTN = (String)shopDesignMap.get("BTN");
        }
        if(shopDesignMap.get("EDGE") != null && !shopDesignMap.get("EDGE").equals("")){
            EDGE = (String)shopDesignMap.get("EDGE");
        }
        if(shopDesignMap.get("THUMBNAIL") != null && !shopDesignMap.get("THUMBNAIL").equals("")){
            THUMBNAIL = (String)shopDesignMap.get("THUMBNAIL");
        }
        if(shopDesignMap.get("FOOTER") != null && !shopDesignMap.get("FOOTER").equals("")){
            FOOTER = (String)shopDesignMap.get("FOOTER");
        }
        if(shopDesignMap.get("SEARCH_BRAND") != null && !shopDesignMap.get("SEARCH_BRAND").equals("")){
            SEARCH_BRAND = (String)shopDesignMap.get("SEARCH_BRAND");
        }
        shopDesign = " " + FONT_SIZE + " "+ SPACES + " "+ BTN + " "+ EDGE + " "+ THUMBNAIL + " "+ FOOTER + " "+ SEARCH_BRAND + " ";
        return shopDesign;
    }
}
