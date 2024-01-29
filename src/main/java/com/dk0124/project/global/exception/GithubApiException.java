package com.dk0124.project.global.exception;


public class GithubApiException extends ApplicationException {
    public GithubApiException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public GithubApiException() {
        super(ExceptionCode.GITHUB_API_EXCEPTION);
    }
}
