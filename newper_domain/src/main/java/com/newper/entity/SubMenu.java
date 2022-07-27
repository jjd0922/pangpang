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
    private String subMenuName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUB_MENU_MENU_NAME", referencedColumnName = "menuName")
    private Menu menu;

    private String subMenuUrl;
    private byte subMenuOrder;



}
