package com.dk0124.project.article.application.service;

import com.dk0124.project.article.adapter.in.TranslationArticleUploadRequest;
import com.dk0124.project.article.adapter.out.TranslationArticleUploadAdapter;
import com.dk0124.project.article.application.TranslationArticleUploadUsecase;
import com.dk0124.project.article.domain.TranslationArticleUploadCommand;
import com.dk0124.project.article.domain.ArticleValidator;
import com.dk0124.project.global.constants.TechTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationArticleUploadService implements TranslationArticleUploadUsecase {

    private final TranslationArticleUploadAdapter uploadAdapter;

    @Override
    public void upload(TranslationArticleUploadRequest request, Long uploader) {
        validateRequest(request);

        uploadAdapter.upload(
                new TranslationArticleUploadCommand(
                        uploader,
                        request.title(),
                        request.content(),
                        request.techTags().stream()
                                .map(TechTag::fromString)
                                .map(Optional::get).collect(Collectors.toSet())
                )
        );

    }

    private void validateRequest(TranslationArticleUploadRequest request) {
        ArticleValidator.validateTitle(request.title());
        ArticleValidator.validateContent(request.content());
        ArticleValidator.validateTags(request.techTags());
    }
}
