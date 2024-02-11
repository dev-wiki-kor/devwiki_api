package com.dk0124.project.global.exception;


import com.dk0124.project.global.constants.CommentConstant;
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


    // 계정 :2000 ~ 2999
    ACCOUNT_ALREADY_EXIST(2000, "이미 가입된 깃헙계정이 있습니다."),
    NICKNAME_ALREADY_USED(2001, "사용할 수 없는 닉네임입니다."),

    USER_NOT_EXIST(2003, "유저가 존재하지 않습니다."),

    // 게시글 : 3000~ 3999
    NO_LOGIN_SESSION(3000, "로그인 상태를 확인해주세요."),
    TAG_NOT_EXIST(3001, "Tech Tag 가 존재 하지 않습니다"),
    INVALID_ARTICLE_ID(3002, "Article이 존재 하지 않습니다"),
    INVALID_USER(3003, "유저의 권한이 충분하지 않습니다."),

    INVALID_TITLE(3004, "제목의 형식이 바르지 않습니다"),
    INVALID_CONTENT(3005, "본문의 형식이 바르지 않습니다"),
    INVALID_ARTICLE_VERSION(3006, "번역 문서의 버전에 해당하는 문서가 없습니다"),
    CANNOT_GENERATE_ARTICLE_VERSION(3007, "문서를 등록할 수 없습니다. 잠시 후 시도해 주세요."),


    // 댓글, 리액셩 : 4000 ~ 4999
    INVALID_COMMENT_SIZE(4001, "댓글의 크기는 " + CommentConstant.COMMENT_MIN_SIZE + " 이상 " + CommentConstant.COMMENT_MAX_SIZE + " 미만이여야 해요"),
    COMMENT_NOT_EXIST(4002, "댓글이 존재하지 않습니다"),
    INVALID_COMMENT_ID(4003, "댓글 정보가 정확하지 않습니다."),

    REACTION_NOT_EXIST(4500, "리액션 정보가 존재하지 않습니다."),


    //
    INTERNAL_SEVER_ERROR(9999,"서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int code;
    private final String message;
}
