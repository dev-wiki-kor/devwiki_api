package com.dk0124.project.article.exception;

import com.dk0124.project.global.exception.ApplicationException;
import com.dk0124.project.global.exception.ExceptionCode;

public class InValidArticleId extends ApplicationException {
    public InValidArticleId(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
    public InValidArticleId(){
        super(ExceptionCode.INVALID_ARTICLE_ID);
    }
}
