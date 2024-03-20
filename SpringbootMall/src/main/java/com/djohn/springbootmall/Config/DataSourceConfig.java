package com.djohn.springbootmall.Config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    @Bean(name = "djohnmallDataSource")
    @Qualifier("djohnmallDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.djohnmall")
    public DataSource djohnmallDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mallsecurityDataSource")
    @Qualifier("mallsecurityDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mallsecurity")
    @Primary
    public DataSource mallsecurityDataSource(){
        return DataSourceBuilder.create().build();
    }
}
