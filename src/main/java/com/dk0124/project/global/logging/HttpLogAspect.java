package com.dk0124.project.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;


/***
 * 보안을 통과한 api 호출 시, MDC 로깅을 세팅하고 , Post 요청일 경우 바디를 출력합니다 .
 * {
 *      "요청URL" :{request.getRequestURL()}
 *      "호출IP"  :{request.getRemoteAddr()},
 *      "브라우저정보" : { request.getHeader("User-Agent")},
 *      "userId" : {...},
 *      "body": {...}
 * }
 */@Aspect
@Component
@Slf4j
public class HttpLogAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Before("controller()")
    public void logWebRequest(JoinPoint joinPoint) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = getCurrentUserId();

        setMDCContext(request, userId);
        printPostBody(request);
    }

    @AfterReturning("controller()")
    public void clearMdcAfterWebRequest(JoinPoint joinPoint) {
        // 요청 처리 완료 후 MDC 클리어
        MDC.clear();
    }

    private String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return "anonymous"; // Default value
    }

    private void printPostBody(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod()) && request instanceof ReadableRequestBodyWrapper) {
            ReadableRequestBodyWrapper bodyWrapper = (ReadableRequestBodyWrapper) request;
            log.info("body : {}", bodyWrapper.getBody());
        }
    }

    private void setMDCContext(HttpServletRequest request, String userId) {
        MDC.put("URL", request.getRequestURL().toString());
        MDC.put("userId", userId);
        MDC.put("IP", request.getRemoteAddr());
        MDC.put("Browser", request.getHeader("User-Agent"));
    }
}