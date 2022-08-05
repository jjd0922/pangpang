package com.newper.config.p6spy;

import com.p6spy.engine.spy.P6SpyLoadableOptions;
import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile(value = "windowinfo")
public class P6Config {

    @PostConstruct
    public void postConstruct(){
        P6SpyLoadableOptions p6_option = P6SpyOptions.getActiveInstance();
        p6_option.setLogMessageFormat((P6spyPrettySqlFormatter.class.getName()));
//        p6_option.setAppender("com.p6spy.engine.spy.appender.Slf4JLogger");
        p6_option.setAppender("com.p6spy.engine.spy.appender.StdoutLogger");
    }

}
