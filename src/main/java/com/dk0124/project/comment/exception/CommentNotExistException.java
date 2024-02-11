package com.dk0124.project.comment.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class CommentNotExistException  extends ApplicationException {
    public CommentNotExistException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public CommentNotExistException(){
        super(ExceptionCode.COMMENT_NOT_EXIST);
    }
}
