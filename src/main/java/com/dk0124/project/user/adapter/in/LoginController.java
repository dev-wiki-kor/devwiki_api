package com.dk0124.project.user.adapter.in;

import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.GithubLoginUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/login")
@RequiredArgsConstructor
public class LoginController {

    private final GithubLoginUsecase githubLoginUsecase;

    @PostMapping
    @RequestMapping("/github")
    public ResponseEntity<String> githubLogin(@RequestBody GithubLoginRequest requestBody, @CookieValue(value = "myCookie", required = false) String cookie){
        githubLoginUsecase.login(new GithubLoginRequest(requestBody.code()));
        // do session
        return ResponseEntity.ok("ok");
    }
}
