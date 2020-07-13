package com.lhamster.config;

import com.lhamster.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalConfig implements WebMvcConfigurer {
    /**
     * 配置jwt拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        // 手机验证码、注册、登录不拦截
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").excludePathPatterns("/register", "/check/**", "/login");
    }
}
