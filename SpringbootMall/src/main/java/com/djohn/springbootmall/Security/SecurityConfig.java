package com.djohn.springbootmall.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
//    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .httpBasic(Customizer.withDefaults())
      .formLogin(Customizer.withDefaults())

      .authorizeHttpRequests(request -> request
        .requestMatchers("/register").permitAll()
        .anyRequest().authenticated() // deny by default 原則  避免意外暴露風險
      )

      .build();
  }

//  @Bean
//  public InMemoryUserDetailsManager userDetailsManager(){
//    UserDetails user1 = User
//      .withUsername("DJohn")
//      .password("{noop}djohn777")
//      .roles("ADMIN","USER")
//      .build();
//
//    UserDetails user2 = User
//      .withUsername("PRoot")
//      .password("{bcrypt}$2a$12$eY47.CfXHGnJ.rkYbKQrteN4Wm.hnlzkKyZI9vDnEPHI/BQrP0/Gm")
//      //  { } 這個括號裡面放的是SpringbootSecurity的加密方式 這邊使用bcrypt
//      // 解碼後是 proot777
//      .roles("USER")
//      .build();
//
//    return new InMemoryUserDetailsManager(user1, user2);
//
//    // InMemory的寫法通常只有測試會使用
//  }
}
