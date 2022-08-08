package com.newper.entity;

import com.newper.constant.MenuType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
/** 대메뉴 */
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuName;
    private String menuUrl;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    private byte menuOrder;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @OrderBy(value = "smOrder asc")
    private List<SubMenu> subMenuList;

    /** ERP menu인 경우 true*/
    public boolean isErpMenu(){
        return getMenuType().equals("ERP");
    }

    /** param url이 menu url or submenu url 인 경우 active를 return*/
    public String getActiveClass(String url){
        if(menuUrl != null && menuUrl.equals(url)){
            return "active";
        }

        Optional<SubMenu> subMenu = subMenuList.stream().filter(sm -> {
            return sm.getSmUrl().equals(url);
        }).findAny();

        if(subMenu.isPresent()){
            return "active";
        }
        return "";
    }
}
