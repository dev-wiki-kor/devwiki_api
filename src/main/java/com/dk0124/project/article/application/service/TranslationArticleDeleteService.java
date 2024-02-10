package com.dk0124.project.article.application.service;

import com.dk0124.project.article.adapter.in.TranslationArticleDeleteRequest;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleEntity;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.application.port.in.TranslationArticleDeleteUsecase;
import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.dk0124.project.article.exception.InvalidArticleVersionException;
import com.dk0124.project.article.exception.InvalidUserException;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationArticleDeleteService implements TranslationArticleDeleteUsecase {

    private final TranslationArticleVersionContentEntityRepository versionContentRepository;
    private final TranslationArticleEntityRepository translationArticleRepository;

    private final Long INITIAL_VERSION = 0L;

    @Override
    @Transactional
    public void delete(TranslationArticleDeleteRequest request, Long userId) {

        var article = translationArticleRepository.findById(request.articleId())
                .orElseThrow(() -> new InvalidArticleIdException());

        var version
                = versionContentRepository.findByArticleIdAndVersion(request.articleId(), request.version())
                .orElseThrow(() -> new InvalidArticleVersionException());

        canDeleteByUser(version, userId);

        deleteVersion(article, version);
    }

    private void deleteVersion(TranslationArticleEntity article, TranslationArticleVersionContentEntity version) {
        versionContentRepository.delete(version);
        if (Objects.equals(version.getVersion(), INITIAL_VERSION))
            translationArticleRepository.delete(article);
    }

    private void canDeleteByUser(TranslationArticleVersionContentEntity version, Long userId) {
        if (version.getEditorId() != userId)
            throw new InvalidUserException();
    }
}
