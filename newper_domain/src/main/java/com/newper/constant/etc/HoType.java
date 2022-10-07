package com.newper.constant.etc;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HoType implements EnumOption {

    LOGO("로고")
    ,SEARCH("검색창")
    ,LOGIN("로그인 그룹")
    ,SEARCH_LOGIN("검색창 + 로그인 그룹")
    ,MENU_LEFT("메뉴(좌측 정렬)")
    ,MENU_CENTER("메뉴(중앙 정렬)")
    ,HEAD_INNER_BANNER("헤더내부_배너")
    ,FIX_MENU("고정 메뉴")
    ,MENU_ALL_CATEGORY_BTN("메뉴(전체 카테고리 버튼)")
    ,NONE("없음")
    ;

    private String option;

    /**단 열에 따라서 class 가져오기*/
    public String getHoClass(int row, int col) {
        if (this.equals(HoType.LOGO)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return "middle-logo-left";
                case 22: return "middle-logo-center";
                case 23: return null;
                case 31: return null;
                case 32: return null;
                case 33: return null;
            }
        }
        if (this.equals(HoType.MENU_ALL_CATEGORY_BTN)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return null;
                case 23: return null;
                case 31: return "all-category-btn";
                case 32: return null;
                case 33: return null;
            }
        }
        /*SEARCH : top-search, middle-search, bottom-search*/
        if (this.equals(HoType.SEARCH)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return "middle-search";
                case 23: return "middle-search";
                case 31: return null;
                case 32: return null;
                case 33: return null;
            }
        }
        /*LOGIN : top-login-group*/
        if (this.equals(HoType.LOGIN)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return null;
                case 23: return "middle-login-group";
                case 31: return null;
                case 32: return null;
                case 33: return "bottom-login-group";
            }
        }
        /*SEARCH_LOGIN : 1단 top-search-login-group, 2,3단 middle-search-login-group*/
        if (this.equals(HoType.SEARCH_LOGIN)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return null;
                case 23: return null;
                case 31: return null;
                case 32: return null;
                case 33: return null;
            }
        }
        if (this.equals(HoType.MENU_LEFT)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return null;
                case 23: return null;
                case 31: return "menu-left";
                case 32: return null;
                case 33: return null;
            }
        }
        if (this.equals(HoType.MENU_CENTER)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return "menu-center";
                case 23: return null;
                case 31: return null;
                case 32: return "menu-center";
                case 33: return null;
            }
        }
        if (this.equals(HoType.HEAD_INNER_BANNER)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return null;
                case 23: return "middle-banner";
                case 31: return null;
                case 32: return null;
                case 33: return null;
            }
        }
        if (this.equals(HoType.FIX_MENU)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return null;
                case 23: return null;
                case 31: return null;
                case 32: return null;
                case 33: return "menu-fixed";
            }
        }
        if (this.equals(HoType.NONE)) {
            switch (row*10+col){
                case 11: return "block-none";
                case 12: return "block-none";
                case 13: return "block-none";
                case 21: return "block-none";
                case 22: return "block-none";
                case 23: return "block-none";
                case 31: return "block-none";
                case 32: return "block-none";
                case 33: return "block-none";
            }
        }

        return null;
    }
}
