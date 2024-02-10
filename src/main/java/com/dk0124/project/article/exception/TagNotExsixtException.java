package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class TagNotExsixtException extends ApplicationException {
    public TagNotExsixtException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public TagNotExsixtException() {
        super(ExceptionCode.TAG_NOT_EXIST);
    }
}
