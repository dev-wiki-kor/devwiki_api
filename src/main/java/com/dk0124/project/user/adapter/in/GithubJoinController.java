package com.dk0124.project.user.adapter.in;

import com.dk0124.project.user.adapter.in.dto.GithubCodeCheckRequest;
import com.dk0124.project.user.adapter.in.dto.GithubUserJoinRequest;
import com.dk0124.project.user.adapter.in.dto.JoinResponse;
import com.dk0124.project.user.domain.GithubUserCanJoinResult;
import com.dk0124.project.user.application.port.in.GithubUserJoinPreCheckUsecase;
import com.dk0124.project.user.application.port.in.GithubUserJoinUsecase;
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

    /**
     * oauth 인증 code 에 해당하는 유저가 회원가입이 가능한지 확인합니다.
     * canRegister.success = true 라면 가입 가능하며, 회원가입이 가능하면 join api 호출을 위한 토큰값을 넘겨줍니다.
     *
     * @param githubCodeCheckRequest GitHub 코드 확인 요청 객체
     * @return 가입 가능 여부 및 토큰 값
     */
    @PostMapping("/checkCode")
    public ResponseEntity<GithubUserCanJoinResult> checkGithubCode(@Valid @RequestBody GithubCodeCheckRequest githubCodeCheckRequest) {
        var canRegister = githubUserJoinPreCheckUsecase.canRegister(githubCodeCheckRequest.code());
        return ResponseEntity.ok(canRegister);
    }


    /**
     * 회원 가입을 수행합니다.
     * 위의 토큰 값과 회원가입에 쓰일 유저 정보를 입력 받으며, 실패 시 JoinFailException이 발생합니다.
     *
     * @param githubUserJoinRequest GitHub 사용자 가입 요청 객체
     * @return 가입 결과
     */
    @PostMapping("/github")
    public ResponseEntity<JoinResponse> joinWithGithub(@Valid @RequestBody GithubUserJoinRequest githubUserJoinRequest) {
        githubUserJoinUsecase.join(githubUserJoinRequest);
        return ResponseEntity.ok(new JoinResponse(true));
    }
}
