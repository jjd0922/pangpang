package com.newper.config;

import com.newper.component.MenuList;
import com.newper.component.SessionInfo;
import com.newper.controller.NoLogin;
import com.newper.entity.Menu;
import com.newper.entity.SubMenu;
import com.newper.exception.NoSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewperInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionInfo sessionInfo;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //js img css 등
        if(handler.getClass().equals(ResourceHttpRequestHandler.class)) {
            return true;
        }

        if(((HandlerMethod) handler).hasMethodAnnotation(NoLogin.class)) {
            return true;
        }

        //세션 체크
        if (sessionInfo.getIdx() == null || sessionInfo.getAuthIdx() == null) {
            throw new NoSessionException();
        }

        String requestURI = request.getRequestURI();
        for (Menu menu : MenuList.menus) {
            for (SubMenu subMenu : menu.getSubMenuList()) {
                if(!subMenu.hasAuth(sessionInfo.getAuthIdx(), requestURI)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
        }
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
