package com.dk0124.project.article.application;

import com.dk0124.project.article.domain.translation.TranslationArticleDetail;

public interface TranslationArticleQuery {
    TranslationArticleDetail query(Long articleId, Long versionId);
}
