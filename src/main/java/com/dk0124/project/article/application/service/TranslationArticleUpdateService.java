package com.dk0124.project.article.application.service;

import com.dk0124.project.article.adapter.in.dto.TranslationArticleUpdateRequest;
import com.dk0124.project.article.application.port.in.TranslationArticleUpdateUsecase;
import com.dk0124.project.article.application.port.out.ArticleVersionPort;
import com.dk0124.project.article.application.port.out.VersionContentSavePort;
import com.dk0124.project.article.domain.ArticleValidator;
import com.dk0124.project.article.domain.SaveNewContentCommand;
import com.dk0124.project.global.config.lock.DistributedLock;
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
    @DistributedLock(key = "'article_version_lock_'+#request.articleId()")
    public void update(TranslationArticleUpdateRequest request, Long userId) {

        ArticleValidator.validateContent(request.content());
        var newVersion = articleVersionPort.newVersion(request.articleId());
        versionContentSavePort.saveNewContent(new SaveNewContentCommand(
                userId, request.articleId(), request.content(), newVersion, request.version()
        ));
    }
}
