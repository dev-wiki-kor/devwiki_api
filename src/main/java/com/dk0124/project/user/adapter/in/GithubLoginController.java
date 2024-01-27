package com.dk0124.project.user.adapter.in;

import com.dk0124.project.config.security.csrf.ConditionalCsrfTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.GithubLoginUsecase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/login")
@RequiredArgsConstructor
public class GithubLoginController {

    private final GithubLoginUsecase githubLoginUsecase;
    private final ConditionalCsrfTokenRepository conditionalCsrfTokenRepository;

    @PostMapping
    @RequestMapping("/github")
    public ResponseEntity<CsrfToken> githubLogin(@RequestBody GithubLoginRequest requestBody, HttpServletRequest request) {
        githubLoginUsecase.login(new GithubLoginRequest(requestBody.code()));

        return ResponseEntity.ok(conditionalCsrfTokenRepository.loadToken(request));
    }


}
