package com.dk0124.project.global.config.feign;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.dk0124.project")
@Configuration
public class FeignConfig {
}

