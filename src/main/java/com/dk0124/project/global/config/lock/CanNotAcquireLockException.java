package com.dk0124.project.global.config.lock;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class CanNotAcquireLockException extends ApplicationException {
    public CanNotAcquireLockException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
