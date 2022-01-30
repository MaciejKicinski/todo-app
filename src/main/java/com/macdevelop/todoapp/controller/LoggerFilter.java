package com.macdevelop.todoapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            var httpRequest = (HttpServletRequest) servletRequest;
            log.info("[doFilter]" + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        }
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("[doFilter] 2");
    }
}
