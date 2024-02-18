package com.dk0124.project.adapter;

import com.dk0124.project.article.adapter.out.ArticleVersionAdapter;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;

import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("local")
public class ArticleVersionAdapterTest {

    @Autowired
    TranslationArticleVersionContentEntityRepository repository;

    private ArticleVersionAdapter articleVersionAdapter;

    private Long goodArticleId = 1L;
    private Long v1 = 1L;
    private Long v2 = 2L;
    private Long v3 = 3L;

    private Long badArticleId = 3L;

    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    @BeforeEach
    void readyDependencyAndData() {
        if (this.articleVersionAdapter == null)
            this.articleVersionAdapter = new ArticleVersionAdapter(repository);
    }


    @Test
    public void 정상_버전_획득() {
        saveNormalArticleData();
        var newVersion = articleVersionAdapter.newVersion(goodArticleId);
        assertEquals(List.of(v1, v2, v3).stream().max(Comparator.comparingLong(e -> e)).get() + 1L, newVersion);
    }


    @Test
    public void 버전_획득_실패_아티클_없음() {
        assertThrows(InvalidArticleIdException.class, () -> articleVersionAdapter.newVersion(badArticleId));
    }

    @Test
    public void 버전_획득_성공_소프트_삭제된_문서_존재_하는_경우() {
        saveNormalArticleAndDeletedData();
        var newVersion = articleVersionAdapter.newVersion(goodArticleId);
        assertEquals(List.of(v1, v2, v3).stream().max(Comparator.comparingLong(e -> e)).get() + 1L, newVersion);
    }


    void saveNormalArticleData() {
        var a1_v1 = getNewArticleVersionContent(goodArticleId, v1, Boolean.FALSE);
        var a1_v2 = getNewArticleVersionContent(goodArticleId, v2, Boolean.FALSE);
        var a1_v3 = getNewArticleVersionContent(goodArticleId, v3, Boolean.FALSE);

        repository.saveAll(List.of(a1_v1, a1_v2, a1_v3));
    }

    void saveNormalArticleAndDeletedData() {
        var a1_v1 = getNewArticleVersionContent(goodArticleId, v1, Boolean.FALSE);
        var a1_v2 = getNewArticleVersionContent(goodArticleId, v2, Boolean.FALSE);
        var a1_v3 = getNewArticleVersionContent(goodArticleId, v3, Boolean.TRUE);

        repository.saveAll(List.of(a1_v1, a1_v2, a1_v3));
    }

    private TranslationArticleVersionContentEntity getNewArticleVersionContent(long articleId, long version, Boolean deleted) {
        return monkey.giveMeBuilder(TranslationArticleVersionContentEntity.class)
                .set("versionContentId", null)
                .set("articleId", articleId)
                .set("version", version)
                .set("content", "content")
                .set("deleted", deleted)
                .sample();
    }


}
