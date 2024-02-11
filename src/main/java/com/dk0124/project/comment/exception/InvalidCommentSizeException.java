package com.dk0124.project.comment.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidCommentSizeException extends ApplicationException {
    public InvalidCommentSizeException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidCommentSizeException(){
        super(ExceptionCode.INVALID_COMMENT_SIZE);
    }
}
