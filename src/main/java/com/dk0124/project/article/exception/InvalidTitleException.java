package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidTitleException extends ApplicationException {
    public InvalidTitleException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidTitleException() {
        super(ExceptionCode.INVALID_TITLE);
    }
}

