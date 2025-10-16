package com.payflow.transaction_service.config;

import com.payflow.transaction_service.config.decoders.UserClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserClientErrorDecoder();
    }
}
