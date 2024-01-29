package com.dk0124.project.user.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class NickNameAlreadyExistException extends ApplicationException {

    public NickNameAlreadyExistException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public NickNameAlreadyExistException() {
        super(ExceptionCode.NICKNAME_ALREADY_USED);
    }
}
