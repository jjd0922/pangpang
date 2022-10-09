package com.newper.constant.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**SHOP.SHOP_DESIGN에 들어가는 JSON MAP KEY 값들*/
@Getter
@AllArgsConstructor
public enum ShopDesign {
    FONT_SIZE("본문 글자크기", "body-text-size-xsmall")
    ,SPACES("화면 여백", "body-padding-xsmall")
    ,BTN("메뉴 & 버튼 크기", "body-button-xsmall")
    ,EDGE("가장자리 둥글리기", "body-radius-none")
    ,BLACK("메뉴 & 본문 검정색", "#000000")
    ,WHITE("어두운 배경 글자색", "#000000")
    ,POINT("포인트색", "#000000")
    ,POINT_FONT("포인트색 배경 글자색", "#000000")
    ,MENU("메뉴 현위치색", "#000000")
    ,MENU_FONT("현위치색 배경 글자색", "#000000")
    ,TITLE("타이틀 글자색", "#000000")
    ,BACK("옅은 배경색", "#000000")
    ,LINE("기본 라인색", "#000000")
    ,THUMBNAIL("썸네일 타입", "")
    ,FOOTER("푸터그룹", "footer-type-a")
    ,SEARCH_BRAND("상품상세 정보상자", "prd-search-bl-sq")


    ,LOGIN_TYPE("로그인 타입", "ICON")
    ,SEARCH_DESIGN("검색창 디자인", "header-search-type-a")
    ,MENU_STYLE("메뉴 현위치 디자인", "menu-style-type-a")
    ,SHOP_LOGO("분양몰 로고","")

    ,FLOAT_BORDER("플로팅 그룹 디자인", "floating-type-a")
   ;
    private String option;
    private String defaultValue;

}
