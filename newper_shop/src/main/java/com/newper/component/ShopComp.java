package com.newper.component;

import com.newper.constant.etc.ShopDesign;
import com.newper.entity.HeaderMenu;
import com.newper.entity.Shop;
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

    private String shopDesign;
    public String setShopDesignClass(Map<String, Object> shopDesignMap){
        String FONT_SIZE = " ";
        String SPACES =" ";
        String BTN =" ";
        String EDGE =" ";

        // 색상
        String BLACK =" ";
        String WHITE =" ";
        String POINT =" ";
        String POINT_FONT =" ";
        String MENU =" ";
        String MENU_FONT =" ";
        String TITLE =" ";
        String BACK =" ";
        String LINE =" ";

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
        if(shopDesignMap.get("BLACK") != null && !shopDesignMap.get("BLACK").equals("")){
            BLACK = (String)shopDesignMap.get("BLACK");
        }
        if(shopDesignMap.get("WHITE") != null && !shopDesignMap.get("WHITE").equals("")){
            WHITE = (String)shopDesignMap.get("WHITE");
        }
        if(shopDesignMap.get("POINT") != null && !shopDesignMap.get("POINT").equals("")){
            POINT = (String)shopDesignMap.get("POINT");
        }
        if(shopDesignMap.get("POINT_FONT") != null && !shopDesignMap.get("POINT_FONT").equals("")){
            POINT_FONT = (String)shopDesignMap.get("POINT_FONT");
        }
        if(shopDesignMap.get("MENU") != null && !shopDesignMap.get("MENU").equals("")){
            MENU = (String)shopDesignMap.get("MENU");
        }
        if(shopDesignMap.get("MENU_FONT") != null && !shopDesignMap.get("MENU_FONT").equals("")){
            MENU_FONT = (String)shopDesignMap.get("MENU_FONT");
        }
        if(shopDesignMap.get("TITLE") != null && !shopDesignMap.get("TITLE").equals("")){
            TITLE = (String)shopDesignMap.get("TITLE");
        }
        if(shopDesignMap.get("BACK") != null && !shopDesignMap.get("BACK").equals("")){
            BACK = (String)shopDesignMap.get("BACK");
        }
        if(shopDesignMap.get("LINE") != null && !shopDesignMap.get("LINE").equals("")){
            LINE = (String)shopDesignMap.get("LINE");
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
        shopDesign = " " + FONT_SIZE + " "+ SPACES + " "+ BTN + " "+ EDGE + " "+ BLACK + " "+ WHITE + " "
                + POINT + " "+ POINT_FONT + " "+ MENU + " "+ MENU_FONT + " "+ TITLE + " "+ BACK + " "
                + LINE + " "+ THUMBNAIL + " "+ FOOTER + " "+ SEARCH_BRAND + " ";
        return shopDesign;
    }
}
