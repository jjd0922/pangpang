package com.pangpang.util;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    /**전화번호 형식으로*/
    public static String getPhoneRegx(String phone) {
        if(phone==null) {
            return "";
        }
        if(phone.indexOf("*")!=-1) {
            return phone;
        }

        phone=phone.replaceAll("[^0-9]", "");
        if(phone.indexOf("02")==0){
            if(phone.length()>6){
                return phone.replaceAll("(02)(\\d{1,4})(\\d{4,})", "$1-$2-$3");
            }else{
                return phone.replaceAll("(02)(\\d{0,})", "$1-$2");
            }
        }else{
            if(phone.indexOf("1")==0){
                return phone.replaceAll("(\\d{4})(\\d{1,})", "$1-$2");
            }else{
                return phone.replaceAll("(\\d{3})(\\d{1,4})(\\d{4,})", "$1-$2-$3");
            }
        }
    }
}
