package com.newper.controller;

import com.newper.exception.MsgException;
import com.newper.exception.NoSessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(value = "com.newper.controller.view")
public class ViewAdvise {

    @ExceptionHandler
    public ModelAndView exception(Exception e){
        ModelAndView mav = new ModelAndView("error/error");

        log.error("", e);


        return mav;
    }
    @ExceptionHandler(MsgException.class)
    public ModelAndView msgException(MsgException e){
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("msg", e.getMessage());
        return mav;
    }

    @ExceptionHandler(NoSessionException.class)
    public ModelAndView noSessionException(){
        ModelAndView mav= new ModelAndView("main/needLogin");

        return mav;
    }

}
