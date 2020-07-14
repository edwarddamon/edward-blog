package com.lhamster.config;

import com.lhamster.filter.CORSFilter;
import com.lhamster.interceptor.JwtInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
public class GlobalConfig implements WebMvcConfigurer {
    /**
     * 配置jwt拦截器
     */
    /*public void addInterceptors(InterceptorRegistry registry) {
        // 手机验证码、注册、登录不拦截
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").excludePathPatterns("/register/**", "/login");
    }*/

    /**
     * 配置跨域过滤器
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CORSFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        return registrationBean;
    }
}
