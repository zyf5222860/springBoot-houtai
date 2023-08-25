package com.fan.springboothoutai.config;

import com.fan.springboothoutai.config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        // 指定controller统一的接口前缀
//        configurer.addPathPrefix("/user", clazz -> clazz.isAnnotationPresent(RestController.class));
//    }

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( jwtInterceptor).addPathPatterns("/**") //拦截所有请求，通过判断token是否登录
        .excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/**","/echart/**","/menu/**","/role/**");

    }


}
