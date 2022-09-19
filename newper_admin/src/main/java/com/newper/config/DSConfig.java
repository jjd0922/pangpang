package com.newper.config;

import com.newper.entity.AesEncrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/** datasource config*/
@Configuration
public class DSConfig {


    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean(name = "dataSource")
    //@ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        AesEncrypt aesEncrypt = new AesEncrypt();

        DataSource dataSource = DataSourceBuilder.create()
               .url("jdbc:mysql://"+aesEncrypt.decrypt(url))
//                .url("jdbc:mysql://db-atqo0.vpc-cdb.ntruss.com/newpermarket?serverTimezone=UT")
                .username(aesEncrypt.decrypt(username))
                .password(aesEncrypt.decrypt(password))
                .driverClassName(driver)
                .build();
        return dataSource;
    }
}
