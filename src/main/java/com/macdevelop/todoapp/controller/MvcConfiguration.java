package com.macdevelop.todoapp.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;


@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private final Set<HandlerInterceptor> interceptors;

    MvcConfiguration(Set<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }


}
