package com.dk0124.project.comment.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidCommentIdException extends ApplicationException {
    public InvalidCommentIdException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
    public InvalidCommentIdException(){
        super(ExceptionCode.INVALID_COMMENT_ID);
    }
}
