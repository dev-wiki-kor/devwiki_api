package com.dk0124.project.common.user.join;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/user/join")
public class GithubJoinController {

    private final GithubUserJoinUsecase githubUserJoinUsecase;

    private final GithubUserJoinPreCheckUsecase githubUserJoinPreCheckUsecase;

    @PostMapping("/checkCode")
    public ResponseEntity<JoinPreCheckResponse> checkGithubCode(@Valid @RequestBody GithubCodeCheckRequest githubCodeCheckRequest) {
        log.info("Checking GitHub code: {}", githubCodeCheckRequest.code());
        var canRegister = githubUserJoinPreCheckUsecase.canRegister(githubCodeCheckRequest.code());
        log.info("Can register with code {}: {}", githubCodeCheckRequest.code(), canRegister);
        return ResponseEntity.ok(new JoinPreCheckResponse(canRegister));
    }

    @PostMapping("/github")
    public ResponseEntity<JoinResponse> joinWithGithub(@Valid @RequestBody GithubUserJoinRequest githubUserJoinRequest) {
        log.info("Joining with GitHub: {}", githubUserJoinRequest);
        githubUserJoinUsecase.join(githubUserJoinRequest);
        log.info("User joined with GitHub code: {}", githubUserJoinRequest.code());
        return ResponseEntity.ok(new JoinResponse(true));
    }
}
