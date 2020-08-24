package com.example.demo.config;

import com.example.demo.interruptor.AuthenticationInterruptor;
import com.example.demo.interruptor.LoginInterruptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: XF
 * @Date: 2020/6/6 14:58
 */
//@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterruptor()).addPathPatterns("/**");
        //registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AuthenticationInterruptor securityInterceptor() {
        return new AuthenticationInterruptor();
    }

    @Bean
    public LoginInterruptor loginInterruptor(){
        return new LoginInterruptor();
    }

    /*@Bean
    public RingBufferWheel ringBufferWheel(){
        return new RingBufferWheel(Executors.newFixedThreadPool(2),64);
    }*/
}
