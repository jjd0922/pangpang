package com.newper.entity;

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
/** 중메뉴 */
public class SubMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer smIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SM_MENU_IDX", referencedColumnName = "menuIdx")
    private Menu menu;

    private String smName;
    private String smUrl;
    private byte smOrder;

    /** 중메뉴 사용 가능한 권한 리스트*/
    private List<Long> smAuth;

    /** param url이 submenu url 인 경우 active를 return*/
    public String getActiveClass(String url){
        if(smUrl.equals(url)){
            return "active";
        }
        return "";
    }


}
