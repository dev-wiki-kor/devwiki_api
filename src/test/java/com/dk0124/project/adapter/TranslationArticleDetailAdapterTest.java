package com.dk0124.project.adapter;

import com.dk0124.project.article.adapter.out.AuthorAdapter;
import com.dk0124.project.article.adapter.out.TranslationArticleDetailAdapter;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleEntity;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;

import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.dk0124.project.article.exception.InvalidArticleVersionException;
import com.dk0124.project.global.constants.TechTag;
import com.dk0124.project.user.adapter.out.user.entity.UserGithubInfoEntity;
import com.dk0124.project.user.adapter.out.user.entity.UserProfileEntity;
import com.dk0124.project.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserProfileEntityRepository;
import com.dk0124.project.user.exception.UserNotExistException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class TranslationArticleDetailAdapterTest {


    @Autowired
    private TranslationArticleEntityRepository articleRepository;
    @Autowired
    private TranslationArticleVersionContentEntityRepository versionContentRepository;

    @Autowired
    private UserGithubInfoEntityRepository userGithubInfoEntityRepository;
    @Autowired
    private UserProfileEntityRepository userProfileEntityRepository;

    private TranslationArticleDetailAdapter adapter;

    private  Long goodArticleId = 1L;
    private  Long goodArticleVersion = 1L;

    private  Long badArticleId = 10L;
    private  Long badArticleVersion = 10L;


    private  Long goodUserId = 1L;


    private final String TITLE = "TITLE";
    private final String CONTENT = "CONTENT";


    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    @BeforeEach
    void initialize() {
        if (adapter == null) {
            AuthorAdapter authorAdapter = new AuthorAdapter(userGithubInfoEntityRepository, userProfileEntityRepository);
            this.adapter = new TranslationArticleDetailAdapter(articleRepository, versionContentRepository, authorAdapter);
        }

    }

    @Test
    void 조회_성공() {

        readyGoodDocumentAndUser();
        var detail = adapter.getDetail(goodArticleId, goodArticleVersion);

        assertNotNull(detail);
        assertNotNull(detail.getAuthor());
        assertNotNull(detail.getVersionContent());
        assertEquals(TITLE , detail.getTitle());
        assertEquals(CONTENT, detail.getVersionContent().getContent().getContent());
    }

    @Test
    void 조회_실패_유저_정보가_없음(){
        readyGoodDocument();
        assertThrows(UserNotExistException.class, ()->adapter.getDetail(goodArticleId, goodArticleVersion));
    }

    @Test
    void 조회_실패_문서_정보가_없음(){
        readyGoodDocumentAndUser();

        assertThrows(InvalidArticleIdException.class, () -> adapter.getDetail(badArticleId, goodArticleVersion) );
        assertThrows(InvalidArticleVersionException.class, () -> adapter.getDetail(goodArticleId, badArticleVersion) );
    }


    private void readyGoodDocumentAndUser() {
        readyGoodDocument();

        readyGoodUser();
    }

    void readyGoodUser(){
        var githubUser = monkey.giveMeBuilder(UserGithubInfoEntity.class)
                .set("id", 1L)
                .set("userMetaId", goodUserId)
                .sample();

        userGithubInfoEntityRepository.save(githubUser);

        var profile = monkey.giveMeBuilder(UserProfileEntity.class)
                .set("id", 1L)
                .set("userMetaId", goodUserId)
                .set("nickname","nickname")
                .sample();

        userProfileEntityRepository.save(profile);
    }

    void readyGoodDocument(){
        var article = monkey.giveMeBuilder(TranslationArticleEntity.class)
                .set("articleId", goodArticleId)
                .set("title", TITLE)
                .set("authorId", goodUserId)
                .set("techTags", Set.of(TechTag.JAVA))
                .set("viewCount", 0L)
                .set("deleted", Boolean.FALSE)
                .sample();

        articleRepository.save(article);

        var translation = monkey.giveMeBuilder(TranslationArticleVersionContentEntity.class)
                .set("versionContentId", 1L)
                .set("articleId", goodArticleId)
                .set("version", goodArticleVersion)
                .set("editorId", goodUserId)
                .set("content", CONTENT)
                .set("viewCount", 0L)
                .set("deleted", Boolean.FALSE)
                .sample();

        versionContentRepository.save(translation);

    }

}
