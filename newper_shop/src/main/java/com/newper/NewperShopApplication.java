package com.newper;

import com.newper.component.ShopSession;
import com.newper.exception.NoSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableRedisHttpSession
public class NewperShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewperShopApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvide(){

        return new AuditorAware<String>() {
            @Autowired
            private ShopSession shopSession;

            @Override
            public Optional<String> getCurrentAuditor() {
                if(shopSession.getId()==null){
                    throw new NoSessionException();
                }
                return Optional.of(shopSession.getId());
            }
        };
    }
}