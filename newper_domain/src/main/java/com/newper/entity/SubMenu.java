package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Optional;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
/** 중메뉴 */
public class SubMenu {

    @Id
    private String smName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SM_MENU_NAME", referencedColumnName = "menuName")
    private Menu menu;

    private String smUrl;
    private byte smOrder;

    /** param url이 submenu url 인 경우 active를 return*/
    public String getActiveClass(String url){
        if(smUrl.equals(url)){
            return "active";
        }
        return "";
    }


}
