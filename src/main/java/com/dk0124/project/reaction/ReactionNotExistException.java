package com.dk0124.project.reaction;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class ReactionNotExistException extends ApplicationException {

    public ReactionNotExistException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public ReactionNotExistException(){
        super(ExceptionCode.REACTION_NOT_EXIST);
    }
}
