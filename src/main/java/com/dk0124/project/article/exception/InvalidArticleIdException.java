package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidArticleIdException extends ApplicationException {
    public InvalidArticleIdException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
    public InvalidArticleIdException(){
        super(ExceptionCode.INVALID_ARTICLE_ID);
    }
}
