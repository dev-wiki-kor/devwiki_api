package com.dk0124.project.global.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/***
 *
 * 보안을 통과한 api 호출 시 아래의 정보를 로깅합니다 .
 *
 * {
 *      "요청URL" :{request.getRequestURL()}
 *      "호출IP"  :{request.getRemoteAddr()},
 *      "브라우저정보" : { request.getHeader("User-Agent")},
 *      "userId" : {...},
 *      "body": {...}
 * }
 */
@Aspect
@Component
@Slf4j
public class HttpLogAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Before("controller()")
    public void logWebRequest(JoinPoint joinPoint) throws IOException {
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var bodyWrapper = (ReadableRequestBodyWrapper) request;

        String userId = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername();
        }


        // JSON 객체 생성 및 필드 추가
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("URL", request.getRequestURL().toString());
        logMap.put("UserId", userId);
        logMap.put("IP",  request.getRemoteAddr());
        logMap.put("Browser", request.getHeader("User-Agent"));

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String requestBody = bodyWrapper.getBody();
            logMap.put("RequestBody", requestBody); // 요청 바디도 JSON 객체에 추가
        }

        // JSON 객체를 문자열로 변환하고 로그로 출력
        String logMessage = mapper.writeValueAsString(logMap);
        log.info("[API REQUEST LOG]: {}", logMessage);
    }
}
