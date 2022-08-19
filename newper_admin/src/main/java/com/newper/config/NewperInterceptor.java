package com.newper.config;

import com.newper.component.SessionInfo;
import com.newper.controller.NoLogin;
import com.newper.exception.NoSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class NewperInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionInfo sessionInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //js img css 등
        if(handler.getClass().equals(ResourceHttpRequestHandler.class)) {
            return true;
        }

        if(((HandlerMethod) handler).hasMethodAnnotation(NoLogin.class)) {
            return true;
        }

        if (sessionInfo.getIdx() != null) {
            return true;
        }
        throw new NoSessionException();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //js img css 등
        if(handler.getClass().equals(ResourceHttpRequestHandler.class)) {
            return;
        }


//            String requestURI = request.getRequestURI();
//            requestURI=requestURI.substring(request.getContextPath().length());
//
//            if(requestURI.indexOf(";")!=-1){
//                requestURI=requestURI.substring(0, requestURI.indexOf(";"));
//            }
    }
}
