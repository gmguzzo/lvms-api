/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author heits
 */
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsHandlerInterceptor()).order(1);
    }

    @Bean
    public CORSHandlerInterceptor corsHandlerInterceptor() {
        return new CORSHandlerInterceptor();
    }

}
