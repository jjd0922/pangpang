package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SM_MENU_NAME", referencedColumnName = "menuName")
    private Menu menu;

    private String smUrl;
    private byte smOrder;



}
