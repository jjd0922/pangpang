package com.newper.entity;

import com.newper.constant.MenuType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

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
}
