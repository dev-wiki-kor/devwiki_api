package com.dk0124.project.user.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class UserNotExistException extends ApplicationException {
    public UserNotExistException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public UserNotExistException() {
        super(ExceptionCode.USER_NOT_EXIST);
    }
}
