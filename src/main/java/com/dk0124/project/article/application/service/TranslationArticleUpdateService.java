package com.dk0124.project.article.application.service;

import com.dk0124.project.article.adapter.in.TranslationArticleUpdateRequest;
import com.dk0124.project.article.application.TranslationArticleUpdateUsecase;
import com.dk0124.project.article.domain.ArticleValidator;
import com.dk0124.project.article.domain.SaveNewContentCommand;
import com.dk0124.project.article.exception.CanNotGenerateVersionException;
import com.dk0124.project.global.config.lock.CanNotAcquireLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationArticleUpdateService implements TranslationArticleUpdateUsecase {

    private final ArticleVersionPort articleVersionPort;

    private final VersionContentSavePort versionContentSavePort;


    @Override
    @Transactional
    public void update(TranslationArticleUpdateRequest request, Long userId) {

        Long newVersion;

        ArticleValidator.validateContent(request.content());

        try {
            newVersion = articleVersionPort.newVersion(request.articleId());
        } catch (CanNotAcquireLockException e) {
            log.warn("버전 생성 실패 : request: {}, userId : {}", request, userId);
            throw new CanNotGenerateVersionException();
        }

        versionContentSavePort.saveNewContent(new SaveNewContentCommand(
                userId, request.articleId(), request.content(), newVersion, request.version()
        ));

    }
}
