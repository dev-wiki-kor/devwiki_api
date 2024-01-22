package com.dk0124.project.config.session;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

import java.time.Duration;


@Configuration
@EnableRedisHttpSession(redisNamespace = "spring:session:JSESSIONID")
public class SessionConfig {

    @Value("${app.var.login-session-id}") // this is JSESSIONID
    private String SESSION_LOCATION;

    @Value("${app.var.session-refresh-duration}")
    private static final int MAX_INACTIVE_INTERVAL_IN_SECONDS = 86400; // 24 hours

    @Bean
    public RedisSessionRepository sessionRepository(RedisTemplate redisTemplate) {
        RedisSessionRepository sessionRepository = new RedisSessionRepository(redisTemplate);
        sessionRepository.setDefaultMaxInactiveInterval(Duration.ofSeconds(MAX_INACTIVE_INTERVAL_IN_SECONDS));
        return sessionRepository;
    }


    // Strategy for reading and writing a cookie value to the HttpServletResponse.
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SESSION_LOCATION);
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        // for test
        serializer.setUseBase64Encoding(false);
        return serializer;
    }
}
