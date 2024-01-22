package com.dk0124.project.config.security;

import com.dk0124.project.config.security.loginSession.LoginSession;
import com.dk0124.project.config.security.loginSession.NoActiveLoginSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;


public class SessionBasedAuthFilter extends OncePerRequestFilter {

    private final String USER_INFO = "USER_INFO";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var session = request.getSession(false);
            var userInfo = getUserFromSession(session);
            var authentication = new PreAuthenticatedAuthenticationToken(
                    userInfo.getUsername(), null, userInfo.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);

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
