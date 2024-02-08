package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidUserException extends ApplicationException {
    public InvalidUserException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
    public InvalidUserException(){
        super(ExceptionCode.INVALID_USER);
    }
}
