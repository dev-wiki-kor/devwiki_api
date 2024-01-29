package com.dk0124.project.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * 예: 1000-1999는 인증 에러, 2000-2999는 데이터베이스 에러 등
 */
@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(0000, "올바르지 않은 요청입니다."),
    GITHUB_API_EXCEPTION(1000, "깃허브 통신이 실패했습니다."),


    ACCOUNT_ALREADY_EXIST(2000, "이미 가입된 깃헙계정이 있습니다."),
    NICKNAME_ALREADY_USED(2001, "사용할 수 없는 닉네임"),

    USER_NOT_EXIST(2003, "유저가 존재하지 않음"),

    INTERNAL_SEVER_ERROR(9999, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int code;
    private final String message;
}
