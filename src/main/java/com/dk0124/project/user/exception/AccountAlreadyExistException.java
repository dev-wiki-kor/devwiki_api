package com.dk0124.project.user.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class AccountAlreadyExistException extends ApplicationException {


    public AccountAlreadyExistException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public AccountAlreadyExistException(){
        super(ExceptionCode.ACCOUNT_ALREADY_EXIST);
    }
}
