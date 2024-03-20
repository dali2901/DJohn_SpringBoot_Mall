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

    @Bean(name = "testIfMatchJdbcTemplate") //這邊名稱打這樣 但daot曾還是可以吃到不知道為什麼>?????
    public NamedParameterJdbcTemplate djohnmallJdbcTemplate(@Qualifier("djohnmallDataSource") DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
    @Primary
    @Bean(name = "mallsecurityJdbcTemplate")
    public NamedParameterJdbcTemplate mallsecurityJdbcTemplate(@Qualifier("mallsecurityDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}