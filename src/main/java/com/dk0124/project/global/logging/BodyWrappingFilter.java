package com.dk0124.project.global.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;



/***
 *
 * Body 를 2번 읽을 수 있게 Wrapping 하는 필터를 등록 !
 * */

@Configuration
public class BodyWrappingFilter {
    @Bean
    public FilterRegistrationBean<RequestBodyFilter> reReadableRequestFilter() {
        FilterRegistrationBean<RequestBodyFilter> filterRegistrationBean = new FilterRegistrationBean<>(new RequestBodyFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }


    public static class RequestBodyFilter extends GenericFilterBean {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            var requestWrapper = new ReadableRequestBodyWrapper((HttpServletRequest) request);

            chain.doFilter(requestWrapper, response);
        }
    }
}
