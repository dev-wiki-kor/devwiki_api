package com.dk0124.project.article.adapter.out;

import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.application.port.out.ArticleVersionPort;
import com.dk0124.project.article.exception.InvalidArticleIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleVersionAdapter implements ArticleVersionPort {

    private final TranslationArticleVersionContentEntityRepository versionContentRepository;

    @Override
    public Long newVersion(Long articleId) {
        Long maxVersion = versionContentRepository.findMaxVersionByArticleId(articleId);
        if (maxVersion == null)
            throw new InvalidArticleIdException();
        return maxVersion + 1;
    }
}
