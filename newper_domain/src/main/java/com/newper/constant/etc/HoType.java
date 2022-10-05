package com.newper.constant.etc;

import lombok.Getter;

public enum HoType {

    로고("로고")
    ,검색창("검색창")
    ,로그인그룹("로그인 그룹")
    ,검색창_로그인그룹("검색창 + 로그인 그룹")
    ,메뉴_좌측("메뉴(좌측 정렬)")
    ,메뉴_중앙("메뉴(중앙 정렬)")
    ,헤더내부_배너("헤더내부 배너")
    ,고정_메뉴("고정 메뉴")
    ,없음("없음")
    ,전체_카테고리_버튼("전체 카테고리 버튼")
    ;

    @Getter
    private String name;

    HoType(String name) {
        this.name = name;
    }

    /**단 열에 따라서 class 가져오기*/
    public String getHoClass(int row, int col) {
        if (this.equals(HoType.로고)) {
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
        if (this.equals(HoType.전체_카테고리_버튼)) {
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
        if (this.equals(HoType.검색창)) {
            switch (row*10+col){
                case 11: return "top-search";
                case 12: return "top-search";
                case 13: return "top-search";
                case 21: return null;
                case 22: return "middle-search";
                case 23: return "middle-search";
                case 31: return null;
                case 32: return null;
                case 33: return "bottom-search";
            }
        }
        if (this.equals(HoType.로그인그룹)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return "top-login-group";
                case 13: return "top-login-group";
                case 21: return null;
                case 22: return null;
                case 23: return "middle-login-group";
                case 31: return null;
                case 32: return null;
                case 33: return "bottom-login-group";
            }
        }
        if (this.equals(HoType.검색창_로그인그룹)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return "top-search-login-group";
                case 13: return "top-search-login-group";
                case 21: return null;
                case 22: return null;
                case 23: return "middle-search-login-group";
                case 31: return null;
                case 32: return null;
                case 33: return "bottom-search-login-group";
            }
        }
        if (this.equals(HoType.메뉴_좌측)) {
            switch (row*10+col){
                case 11: return null;
                case 12: return null;
                case 13: return null;
                case 21: return null;
                case 22: return "menu-left";
                case 23: return null;
                case 31: return "menu-left";
                case 32: return null;
                case 33: return null;
            }
        }
        if (this.equals(HoType.메뉴_중앙)) {
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
        if (this.equals(HoType.헤더내부_배너)) {
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
        if (this.equals(HoType.고정_메뉴)) {
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
        if (this.equals(HoType.없음)) {
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
