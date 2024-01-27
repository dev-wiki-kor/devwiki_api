package com.dk0124.project.config.security.authProvider;

import com.dk0124.project.config.security.session.LoginSession;
import com.dk0124.project.config.security.NoActiveLoginSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;


@Slf4j
public class SessionBasedAuthFilter extends OncePerRequestFilter {

    private static final String USER_INFO = "USER_INFO";

    private static final String LOGIN_PATH = "/v1/user/login/github";
    private static final String SIGNIN_PATH = "/v1/user/signIn/github";

    /*
     * use JSESSIONID ( based64 encoded ) as redis key for user session
     * user role & unique id stored in attribute ("USER_INFO")
     * */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 로그인 경로의 요청인 경우 세션 검증을 생략
            if (!request.getRequestURI().equals(LOGIN_PATH) && !request.getRequestURI().equals(SIGNIN_PATH)) {
                var session = request.getSession(false);
                if (session != null) {
                    var userInfo = getUserFromSession(session);
                    if (userInfo != null) {
                        var authentication = new PreAuthenticatedAuthenticationToken(
                                userInfo.getUsername(), null, userInfo.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            // 예외 로깅 등
            log.info("Reason : {}  on {} ", request.getRequestURI(), e.getLocalizedMessage());
        } finally {
            // 다음 필터로 요청을 계속 전달
            filterChain.doFilter(request, response);
        }
    }

    private User getUserFromSession(HttpSession session) {

        if (session == null)
            throw new NoActiveLoginSession("No active session found");

        if (session.getAttribute(USER_INFO) == null)
            throw new NoActiveLoginSession("No active valid user info on session");

        var loginSession = (LoginSession) session.getAttribute(USER_INFO);

        return new User(
                String.valueOf(loginSession.getUserId()),
                "",
                loginSession.getRoles()
                        .stream()
                        .map(e -> new SimpleGrantedAuthority("ROLE_" + e.name()))
                        .collect(Collectors.toSet())
                , true, true, true, true
        );
    }
}
