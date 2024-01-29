package com.dk0124.project.global.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final int code;
    private final String message;

    public ApplicationException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
