package com.pangpang.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/** BCrypt 암호화 클래스*/
public class PasswordHash {

    /**  encoding  ( length : 기본값 10 ) */
    public String encodeBcrypt(String pwd){
        return BCrypt.hashpw(pwd, BCrypt.gensalt(10));
    };
    /**  encoding  ( length : 기본값 10 ) */
    public String encodeBcrypt(String pwd, int length){
        return BCrypt.hashpw(pwd, BCrypt.gensalt(length));
    };
    /** 비교 */
    public boolean matchBcrypt(String pwd, String hashpwd){
        return BCrypt.checkpw(pwd, hashpwd);
    };
}
