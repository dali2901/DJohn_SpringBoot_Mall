package com.djohn.springbootmall.Config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class JdbcTemplateConfig {


    //因為希望是使用  NamedParameterJdbcTemplate
    @Primary
    @Bean(name = "primaryJdbcTemplate")
    public NamedParameterJdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "secondJdbcTemplate")
    public NamedParameterJdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}