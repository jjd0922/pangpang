package com.newper;

import com.newper.component.SessionInfo;
import com.newper.exception.MsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class NewperAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewperAdminApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvide(){

        return new AuditorAware<String>() {
            @Autowired
            private SessionInfo sessionInfo;

            @Override
            public Optional<String> getCurrentAuditor() {
                if(sessionInfo.getId()==null){
                    throw new MsgException("세션이 만료되었습니다. 다시 로그인 해주세요.");
                }
                return Optional.of(sessionInfo.getId());
            }
        };
    }
}
