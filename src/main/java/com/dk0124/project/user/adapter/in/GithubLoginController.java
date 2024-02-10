package com.dk0124.project.user.adapter.in;

import com.dk0124.project.global.config.security.csrf.ConditionalCsrfTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.port.in.GithubLoginUsecase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;


/**
 * Github 로그인 컨트롤러
 *
 * - Github 로그인 요청을 처리합니다.
 * - 로그인 성공 시, 세션을 생성하고 CSRF 토큰을 바디에 담아 응답합니다.
 */
@RestController
@RequestMapping("/v1/user/login")
@RequiredArgsConstructor
public class GithubLoginController {

    private final GithubLoginUsecase githubLoginUsecase;
    private final ConditionalCsrfTokenRepository conditionalCsrfTokenRepository;

    /**
     * Github 로그인 요청을 처리하고 CSRF 토큰을 반환합니다.
     *
     * @param requestBody Github 로그인 요청 정보
     * @param request     HTTP 요청 객체
     * @return CSRF 토큰을 담은 응답
     */
    @PostMapping
    @RequestMapping("/github")
    public ResponseEntity<CsrfToken> githubLogin(@RequestBody GithubLoginRequest requestBody, HttpServletRequest request) {
        // login 성공 여부 확인
        githubLoginUsecase.login(new GithubLoginRequest(requestBody.code()));
        // 성공 시, csrf 토큰을 바디에 담아서 전송
        return ResponseEntity.ok(conditionalCsrfTokenRepository.loadToken(request));
    }


}
