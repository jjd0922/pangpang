package com.newper.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.Map;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@Getter
@Setter
/** 세션 정보 저장하는 class*/
public class SessionInfo implements Serializable {

    private Integer idx;
    private String id;
    private Integer authIdx;

    /** 로그인 처리*/
    public void login(Map<String,Object> userMap) {
        idx = Integer.parseInt(userMap.get("U_IDX")+"");
        id = userMap.get("U_ID")+"";
        authIdx = Integer.parseInt(userMap.get("U_AUTH_IDX")+"");
    }
}
