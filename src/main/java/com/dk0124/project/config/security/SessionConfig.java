package com.dk0124.project.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;


@Configuration
@EnableRedisHttpSession(redisNamespace = "spring:session:JSESSIONID", maxInactiveIntervalInSeconds = 86400)
public class SessionConfig {

    @Value("${app.var.login-session-id}") // this is JSESSIONID
    private String SESSION_LOCATION;


    // Strategy for reading and writing a cookie value to the HttpServletResponse.
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SESSION_LOCATION);
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");

        // for test - look same in brower and redis storage
        serializer.setUseBase64Encoding(false);
        return serializer;
    }
}
