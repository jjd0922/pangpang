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
    public DataSource dataSource() {
        AesEncrypt aesEncrypt = new AesEncrypt();
//        System.out.println("decrypt===="+aesEncrypt.decrypt("OX8gM7VWPhTuWFo6bJaSUGn582qhkJX4OiH+RMPoSNt+R2oVNOX/T5gXGwP9nryFg40nr1R8MDQXB4h2w/D2zoQObMPu6ZacsKBQoFXtb6ie22LNXVBc8r+WxCdHiSJW7vSPHvhSb9bQe+ETJ7uW1A=="));
/*        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://"+aesEncrypt.decrypt(url))
                .username(aesEncrypt.decrypt(username))
                .password(aesEncrypt.decrypt(password))
                .driverClassName(driver)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        return dataSource;*/

        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://db-atqo0.vpc-cdb.ntruss.com/newpermarket?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false")
                .username("newpermarket")
                .password("votmdnjem12#$")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        return dataSource;
    }
}
