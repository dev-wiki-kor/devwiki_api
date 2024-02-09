package com.dk0124.project.article.application.port.out;

import com.dk0124.project.article.domain.TranslationArticleUploadCommand;

public interface TranslationArticleUploadPort {
    void upload(TranslationArticleUploadCommand command);
}