package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InvalidArticleVersionException extends ApplicationException {
    public InvalidArticleVersionException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidArticleVersionException() {
        super(ExceptionCode.INVALID_ARTICLE_VERSION);
    }
}
