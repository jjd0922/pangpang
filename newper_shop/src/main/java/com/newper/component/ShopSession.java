package com.newper.component;

import com.newper.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@Getter
@Setter
/** 세션 정보 저장하는 class*/
public class ShopSession implements Serializable {

    private Long idx;
    private String id;
    private Integer shopIdx;

    private boolean pwdCheck = false;
    private String saIdx;

    public void login(Customer customer) {
        idx = customer.getCuIdx();
        id = customer.getCuId();
    }
}
