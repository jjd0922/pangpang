package com.newper.controller;

import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.exception.NoSessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(value = "com.newper.controller.rest")
public class RestAdvise {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exception(Exception e){
        Map<String, Object> returnMap = new HashMap<>();
//        e.printStackTrace();
        log.error("controller error", e);

        Throwable ee = e.getCause();
        for (int i = 0; i < 5; i++) {
            if (ee == null) {
                break;
            }
            if(ee instanceof MsgException){
                return msgException((MsgException) ee);
            }else if(ee instanceof NumberFormatException){
                return msgException(new MsgException("숫자, 금액 입력값에 문자 입력값을 제거 후 다시 시도해주세요.", ee));
            }

            ee=ee.getCause();
        }

        log.error("rest controller error ", e);

        returnMap.put("timestamp", LocalDateTime.now());
        returnMap.put("status", 500);
        returnMap.put("error", e.getClass()+"");
        returnMap.put("error_message", e.getMessage());
        returnMap.put("message", "오류 발생.\n잠시 후 시도해주세요");

        return returnMap;
    }

    @ExceptionHandler(MsgException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object msgException(MsgException e){
        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("timestamp", LocalDateTime.now());
        returnMap.put("status", 400);
        returnMap.put("error", e.getClass()+"");
        returnMap.put("message", e.getMessage());
//      returnMap.put("path", );

        return returnMap;
    }

    @ExceptionHandler(NoSessionException.class)
    public ReturnMap noSessionException(){
        ReturnMap rm = new ReturnMap();



        return rm;
    }
}
