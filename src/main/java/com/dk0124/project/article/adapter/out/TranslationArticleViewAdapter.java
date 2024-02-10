package com.dk0124.project.article.adapter.out;

import com.dk0124.project.article.adapter.out.repository.TranslationArticleEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.application.port.out.TranslationArticleViewPort;
import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.dk0124.project.article.exception.InvalidArticleVersionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationArticleViewAdapter implements TranslationArticleViewPort {
    private final TranslationArticleEntityRepository articleRepository;
    private final TranslationArticleVersionContentEntityRepository versionContentRepository;

    @Override
    public void countUp(Long articleId, Long version) {
        var article = articleRepository.findById(articleId)
                .orElseThrow(InvalidArticleIdException::new);
        articleRepository.updateViewCount(article.getArticleId(), article.getVersion_());

        var versionContent = versionContentRepository.findByArticleIdAndVersion(articleId, version)
                .orElseThrow(InvalidArticleVersionException::new);
        versionContentRepository.updateViewCount(articleId, version, versionContent.getVersion_());
    }
}
