package com.djohn.springbootmall.Security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class MallFilter01 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("執行Filter01");
//        把Request跟Response傳下去 交給下個Filter繼續處理
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
