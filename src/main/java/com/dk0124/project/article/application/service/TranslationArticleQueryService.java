package com.dk0124.project.article.application.service;

import com.dk0124.project.article.adapter.out.TranslationArticleDetailAdapter;
import com.dk0124.project.article.application.TranslationArticleQuery;

import com.dk0124.project.article.application.TranslationArticleViewPort;
import com.dk0124.project.article.domain.translation.TranslationArticleDetail;

import com.dk0124.project.global.constants.TechTag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TranslationArticleQueryService implements TranslationArticleQuery {


    private final TranslationArticleDetailAdapter articleDetailAdapter;

    private final TranslationArticleViewPort viewPort;


    @Override
    @Transactional
    public TranslationArticleDetail query(Long articleId, Long version) {
        var articleDetail = articleDetailAdapter.getDetail(articleId, version);
        viewPort.countUp(articleId, version);

        return articleDetail;
    }

    public TranslationArticleDetail query(Long articleId, Long version, Long viewer) {
        var articleDetail = query(articleId, version);
        updatePersonalCategory(viewer, articleDetail.getTechTags());

        return articleDetail;
    }

    /*
    TODO : 유저 행동 기록 .
    * **/
    private void updatePersonalCategory(Long viewer, Set<TechTag> techTags) {

        /*do save user record */

    }


}
