package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class NoLoginInfoException extends ApplicationException {
    public NoLoginInfoException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public NoLoginInfoException() {
        super(ExceptionCode.NO_LOGIN_SESSION);
    }
}
