package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidContentException extends ApplicationException {
    public InvalidContentException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidContentException(){
        super(ExceptionCode.INVALID_CONTENT);
    }
}
