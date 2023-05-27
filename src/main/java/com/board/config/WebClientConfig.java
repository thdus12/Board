package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient를 설정하는 클래스
 */
@Configuration
public class WebClientConfig {
	
	/**
     * WebClient를 Bean으로 등록합니다. 
     * 이 WebClient는 "http://localhost:8088" 주소로 요청을 보냅니다.
     * 
     * @return WebClient 객체
     */
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8088");
    }
}