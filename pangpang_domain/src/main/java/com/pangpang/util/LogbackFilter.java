package com.pangpang.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if(event.getMessage().contains("USER_LOG")) { // filter가 들어간 로그는 출력 안함
            return FilterReply.DENY;
        } else {
            return FilterReply.ACCEPT;
        }
    }
}