package com.newper.config;

import com.newper.component.ShopComp;
import com.newper.component.ShopSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewperInterceptor implements HandlerInterceptor {
    @Autowired
    private ShopSession shopSession;
    @Autowired
    private ShopComp shopComp;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //js img css 등
        if(handler.getClass().equals(ResourceHttpRequestHandler.class)) {
            return true;
        }

        //error ex) 404 page
        if(request.getRequestURI().equals("/error")){
            return true;
        }

        //세션에 분양몰 idx 세팅
//        if(shopSession.getShopIdx() == null){
//            shopSession.setShopIdx(shopComp.getShopMap().get(request.getServerName()).getShopIdx());
//        }

//        String requestURI = request.getRequestURI();
//        requestURI=requestURI.substring(request.getContextPath().length());
//
//        //;jession 있는 경우 uri로 인식되는 문제
//        if(requestURI.indexOf(";jsessionid")!=-1){
//            requestURI=requestURI.substring(0, requestURI.indexOf(";"));
//            response.sendRedirect(request.getContextPath()+requestURI);
//            return false;
//        }
//
//        //로그인, 로그인 처리
//        if(requestURI.indexOf("/login")!=-1 || requestURI.equals("/admin/test/login.ajax")){
//            return true;
//        }


        return true;
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
