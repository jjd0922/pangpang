package com.newper.entity;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/** 암복호화하는 문자열은 BASE64로 인코딩된 값*/
public class AesEncrypt {

    private Key keySpec;
    private IvParameterSpec ips=null;
    private String cipher = "AES/CBC/PKCS5Padding";
    private String charset = "UTF-8";

    public AesEncrypt() {
        String key = "kBxRTCLglSWQwNu9jZAwUh+VMvX2jIJ1f7+fykv8tQE=";
        String iv = "JjYnEGc4yFXbLZdXY3TxWg==";
        this.keySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        ips=new IvParameterSpec( Base64.getDecoder().decode(iv));
    }

    public AesEncrypt(byte[] key, byte[] iv) throws UnsupportedEncodingException{
        this.keySpec = new SecretKeySpec(key, "AES");
        ips=new IvParameterSpec(iv);
    }
    /** key, iv는 base64 encoding 문자열*/
    public AesEncrypt(String key, String iv) {
        this.keySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        ips=new IvParameterSpec( Base64.getDecoder().decode(iv));
    }
    /** 암호화 후 인코딩 */
    public String encrypt(String str){
        if(str==null || str.trim().equals("")) {
            return "";
        }
        try{
            if(ips==null){
                return encryptRandom(str);
            }

            Cipher c = Cipher.getInstance(cipher);

            c.init(Cipher.ENCRYPT_MODE, keySpec, ips);
            byte[] encrypted = c.doFinal(str.getBytes(charset));
            return new String(Base64.getEncoder().encode(encrypted));
        }catch (Exception e){
            return str;
        }
    }
    /** 디코딩 후 복호화*/
    public String decrypt(String str){
        try {
            if(str==null || str.trim().equals("")) {
                return "";
            }

            if(ips==null){
                return decryptRandom(str);
            }

            Cipher c = Cipher.getInstance(cipher);
            byte[] byteStr = Base64.getDecoder().decode(str);
            c.init(Cipher.DECRYPT_MODE, keySpec, ips);

            String dec =new String(c.doFinal(byteStr), charset);
            if(dec.equals("")){
                return str;
            }
            return dec;
        }catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**임의 iv사용*/
    public String encryptRandom(String str){
        try {
            if (str == null || str.trim().equals("")) {
                return "";
            }
            Cipher c = Cipher.getInstance(cipher);

            byte[] random_byte = new byte[16];
            new SecureRandom().nextBytes(random_byte);
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(random_byte));
            byte[] encrypted = c.doFinal(str.getBytes(charset));

            byte[] iv_enc = new byte[16 + encrypted.length];
            System.arraycopy(random_byte, 0, iv_enc, 0, 16);
            System.arraycopy(encrypted, 0, iv_enc, 16, encrypted.length);

            String enStr = new String(Base64.getEncoder().encode(iv_enc));
            return enStr;
        } catch (Exception e) {
            return str;
        }
    }
    /**임의 iv사용*/
    public String decryptRandom(String str){
        try {
            Cipher c = Cipher.getInstance(cipher);
            byte[] byteStr = Base64.getDecoder().decode(str.getBytes());
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(byteStr, 0, 16));

            int enc_length=byteStr.length-16;
            byte[] enc=new byte[enc_length];
            System.arraycopy(byteStr, 16, enc, 0, enc_length);
            return new String(c.doFinal(enc), charset);
        }catch (Exception e) {
            return str;
        }
    }
}
