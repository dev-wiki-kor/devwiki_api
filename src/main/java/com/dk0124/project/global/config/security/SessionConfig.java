package com.dk0124.project.global.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;




/**
 * 세션 구성 클래스
 *
 * - Redis를 사용한 HTTP 세션 구성
 * - CookieSerializer 설정
 */

@Configuration
@EnableRedisHttpSession(redisNamespace = "spring:session:JSESSIONID", maxInactiveIntervalInSeconds = 86400
)
public class SessionConfig {

    @Value("${app.var.login-session-id}") //
    private String SESSION_LOCATION;


    /**
     * CookieSerializer 빈 등록
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SESSION_LOCATION);
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");

        /**
         * TODO : 배포 시 true 로 수정
         * */
        // for test - look same in brower and redis storage
        serializer.setUseBase64Encoding(false);
        return serializer;
    }
}
