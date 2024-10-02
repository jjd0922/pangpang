package com.pangpang.config;


import com.p6spy.engine.spy.P6DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
    @Value("${spring.profiles.active}")
    private String profiles_active;
    @Value("${spring.datasource.master.url}")
    private String url1;

    @Primary
    @Bean
    @DependsOn({"routingDataSource"})
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        if(profiles_active.equals("window")){
            P6DataSource pds = new P6DataSource(routingDataSource);
            return new LazyConnectionDataSourceProxy(pds);
        }else{
            return new LazyConnectionDataSourceProxy(routingDataSource);
        }
    }

    @Bean(name = "routingDataSource")
    @DependsOn({"masterDataSource",})  public DataSource routingDataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", masterDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .url(url1)
                .username("pangpang")
                .password("pangpang12!@")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Primary
    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}