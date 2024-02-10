package com.dk0124.project.article.translation;

import com.dk0124.project.article.adapter.out.TranslationArticleDetailAdapter;
import com.dk0124.project.article.application.service.TranslationArticleQueryService;
import com.dk0124.project.article.application.port.out.TranslationArticleViewPort;
import com.dk0124.project.article.domain.Author;
import com.dk0124.project.article.domain.Content;
import com.dk0124.project.article.domain.translation.TranslationArticleDetail;
import com.dk0124.project.article.domain.translation.VersionContent;
import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.dk0124.project.article.exception.InvalidArticleVersionException;
import com.dk0124.project.global.constants.TechTag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationArticleQueryServiceTest {

    @Mock
    private TranslationArticleDetailAdapter articleDetailAdapter;

    @Mock
    private TranslationArticleViewPort viewUpPort;

    @InjectMocks
    private TranslationArticleQueryService queryService;

    private final Long VALID_ARTICLE_ID = 1L;
    private final Long VALID_AUTHOR_ID = 1L;
    private final Long VALID_VERSION = 1L;
    private final Long INVALID_ARTICLE_ID = 99L;
    private final Long INVALID_VERSION = 99L;


    @Test
    void 조회_성공() {
        var articleDetail = articleDetail();

        when(articleDetailAdapter.getDetail(VALID_ARTICLE_ID, VALID_VERSION)).thenReturn(articleDetail);

        var result = queryService.query(VALID_ARTICLE_ID, VALID_VERSION);

        assertNotNull(result);
        verify(viewUpPort, times(1)).countUp(VALID_ARTICLE_ID, VALID_VERSION);
    }

    @Test
    void 조회_실패_없는_문서() {
        when(articleDetailAdapter.getDetail(INVALID_ARTICLE_ID, VALID_VERSION))
                .thenThrow(new InvalidArticleIdException());

        assertThrows(InvalidArticleIdException.class, () -> queryService.query(INVALID_ARTICLE_ID, VALID_VERSION));
    }


    @Test
    void 조회_실패_없는_버전() {
        when(articleDetailAdapter.getDetail(VALID_ARTICLE_ID, INVALID_VERSION))
                .thenThrow(new InvalidArticleVersionException());

        assertThrows(InvalidArticleVersionException.class, () -> queryService.query(VALID_ARTICLE_ID, INVALID_VERSION));
    }

    private TranslationArticleDetail articleDetail() {
        return TranslationArticleDetail.of(
                VALID_ARTICLE_ID,
                Author.of(
                        VALID_AUTHOR_ID,
                        "nickanme",
                        "url"
                ), "title"
                , VersionContent.of(
                        VALID_AUTHOR_ID,
                        "nickanme",
                        new Content("contentn"),
                        "",
                        1L,
                        10L,
                        10L,
                        10L,
                        10L,
                        LocalDateTime.now()
                ),
                Set.of(TechTag.JAVA)
                ,20L,
                20L,
                20L,
                20L,
                LocalDateTime.now()
        );
    }
}
