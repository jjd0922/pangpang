package com.pangpang.controller;

import com.pangpang.dto.ReturnMap;
import com.pangpang.exception.MsgException;
import com.pangpang.exception.NoRollbackException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(value = "com.pangpang.controller.rest")
public class RestAdvise {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exception(Exception e){
        if(e instanceof IOException){
            log.error("rest controller IOException");
            return null;
        }

        Map<String, Object> returnMap = new HashMap<>();

        Throwable ee = e.getCause();
        try{

        }catch (MsgException me){
            return me;
        }

        log.error("rest controller error ", e);

        returnMap.put("timestamp", LocalDateTime.now());
        returnMap.put("status", 500);
        returnMap.put("error", e.getClass()+"");
        returnMap.put("message", "오류 발생.\n잠시 후 시도해주세요");

        return returnMap;
    }

    @ExceptionHandler(value = {MsgException.class, NoRollbackException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ReturnMap msgException(Exception e){
        ReturnMap rm = new ReturnMap();
        rm.setStatus(400);
        rm.setMessage(e.getMessage());

        return rm;
    }
}
