package com.dk0124.project.user.article.tech;

import com.dk0124.project.article.adapter.in.TechArticleUpdateRequest;
import com.dk0124.project.article.adapter.in.TechArticleUploadRequest;
import com.dk0124.project.article.adapter.out.entity.ArticleActionHistoryEntity;
import com.dk0124.project.article.adapter.out.entity.TechArticleEntity;
import com.dk0124.project.article.adapter.out.repository.ArticleActionHistoryEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TechArticleEntityRepository;
import com.dk0124.project.article.application.port.out.AuthorPort;
import com.dk0124.project.article.application.service.TechArticleServiceImpl;
import com.dk0124.project.article.domain.Author;
import com.dk0124.project.article.domain.tech.TechArticleDetail;
import com.dk0124.project.article.exception.InvalidUserException;
import com.dk0124.project.global.constants.TechTag;
import com.dk0124.project.user.adapter.out.user.entity.UserMetaEntity;
import com.dk0124.project.user.domain.UserRole;
import com.dk0124.project.user.domain.UserStatus;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechArticleServiceTest {

    @Mock
    private TechArticleEntityRepository repository;

    @Mock
    private ArticleActionHistoryEntityRepository historyEntityRepository;

    @Mock
    private AuthorPort authorPort;

    @InjectMocks
    private TechArticleServiceImpl techArticleService;

    private final Long VALID_USER_ID = 1L;
    private final Long VALID_AUTHOR_ID = 1L;
    private final Long VALID_ARTICLE_ID = 1L;

    private final Long IN_VALID_USER_ID = 2L;


    private final String TITLE = "TITLE";
    private final String CONTENT = "CONTENT";

    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    UserMetaEntity savedEntity = monkey.giveMeBuilder(UserMetaEntity.class)
            .set("id", 1L)
            .set("userRoles", Set.of(UserRole.USER))
            .set("userStatus", Set.of(UserStatus.NORMAL))
            .set("active", true)
            .sample();

    @Test
    void 업로드_성공() {
        var request = new TechArticleUploadRequest(TITLE, CONTENT, Set.of("JAVA"));
        var article = giveMeGoodArticleEntity();

        when(repository.save(any(TechArticleEntity.class))).thenReturn(article);

        techArticleService.upload(request, VALID_USER_ID);

        verify(repository, times(1)).save(any(TechArticleEntity.class));
        verify(historyEntityRepository, times(1)).save(any(ArticleActionHistoryEntity.class));
    }

    @Test
    void 업데이트_성공() {
        TechArticleUpdateRequest request = new TechArticleUpdateRequest(VALID_ARTICLE_ID, "Updated Title", "Updated Content", Set.of("DB"));
        TechArticleEntity article = giveMeGoodArticleEntity();

        when(repository.findById(VALID_ARTICLE_ID)).thenReturn(Optional.of(article));

        techArticleService.update(request, VALID_USER_ID);

        verify(historyEntityRepository, times(1)).save(any(ArticleActionHistoryEntity.class));
    }

    @Test
    void 업데이트_실패_유저권한_없음() {
        TechArticleUpdateRequest request = new TechArticleUpdateRequest(VALID_ARTICLE_ID, "Updated Title", "Updated Content", Set.of("DB"));
        TechArticleEntity article = giveMeGoodArticleEntity();

        when(repository.findById(VALID_ARTICLE_ID)).thenReturn(Optional.of(article));

        assertThrows(InvalidUserException.class, () -> techArticleService.update(request, IN_VALID_USER_ID));
    }



    @Test
    void 아티클_삭제_성공() {
        TechArticleEntity article = giveMeGoodArticleEntity();

        when(repository.findById(VALID_ARTICLE_ID)).thenReturn(Optional.of(article));

        techArticleService.delete(VALID_ARTICLE_ID, VALID_USER_ID);

        verify(repository, times(1)).delete(article);
        verify(historyEntityRepository, times(1)).save(any(ArticleActionHistoryEntity.class));
    }


    @Test
    void 아티클_삭제_실패_유저_권한_없음() {
        TechArticleEntity article = giveMeGoodArticleEntity();
        when(repository.findById(VALID_ARTICLE_ID)).thenReturn(Optional.of(article));
        techArticleService.delete(VALID_ARTICLE_ID, VALID_USER_ID);

        assertThrows(InvalidUserException.class, () -> techArticleService.delete(VALID_ARTICLE_ID, IN_VALID_USER_ID));
    }


    @Test
    void 아티클_단건_조회_성공() {
        TechArticleEntity article = giveMeGoodArticleEntity();

        Author author = Author.of(VALID_USER_ID, "Nickname", "url");

        when(repository.findById(VALID_ARTICLE_ID)).thenReturn(Optional.of(article));
        when(authorPort.get(anyLong())).thenReturn(author);

        TechArticleDetail detail = techArticleService.detail(VALID_ARTICLE_ID);

        assertNotNull(detail);
        assertEquals(VALID_ARTICLE_ID, detail.getArticleId());
        verify(repository, times(1)).updateViewCount(VALID_ARTICLE_ID, article.getVersion());
    }



    private TechArticleEntity giveMeGoodArticleEntity() {
        return monkey.giveMeBuilder(TechArticleEntity.class)
                .set("articleId", VALID_USER_ID)
                .set("title", TITLE)
                .set("authorId", VALID_AUTHOR_ID)
                .set("content", CONTENT)
                .set("techTags", Set.of(TechTag.JAVA))
                .set("viewCount", 0L)
                .set("likes", 0L)
                .set("disLikes", 0L)
                .set("comments", 0L)
                .set("deleted", false)
                .set("version", 0L)
                .set("updatedAt", LocalDateTime.now())
                .set("createdAt", LocalDateTime.now())
                .sample();
    }


}