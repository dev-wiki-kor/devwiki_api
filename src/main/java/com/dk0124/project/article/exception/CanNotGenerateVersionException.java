package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class CanNotGenerateVersionException extends ApplicationException {
    public CanNotGenerateVersionException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public CanNotGenerateVersionException() {
        super(ExceptionCode.CANNOT_GENERATE_ARTICLE_VERSION);
    }
}
