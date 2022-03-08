package com.kh146.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //  cors 허용을 위한 글로벌(전역) 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                                             //요청 url
                .allowedOrigins("http://192.168.168.103:5500", "http://localhost:5500")            //요청 client
                .allowedMethods("*")                                                             //모든 메소드 허용
                .maxAge(3000);                                                                   //캐시(cache?) 시간
    }
}