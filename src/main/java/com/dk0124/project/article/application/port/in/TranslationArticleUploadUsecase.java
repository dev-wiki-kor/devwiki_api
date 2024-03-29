package com.dk0124.project.article.application.port.in;

import com.dk0124.project.article.adapter.in.dto.TranslationArticleUploadRequest;

public interface TranslationArticleUploadUsecase {
    public void upload(TranslationArticleUploadRequest request, Long uploader);
}
