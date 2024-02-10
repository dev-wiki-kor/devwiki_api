package com.dk0124.project.IT;

import com.dk0124.project.article.adapter.in.dto.TranslationArticleUpdateRequest;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleEntity;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.application.service.TranslationArticleUpdateService;
import com.dk0124.project.global.constants.TechTag;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
public class TranslationUpdateIT {
    @Autowired
    private TranslationArticleUpdateService updateService;

    @Autowired
    private TranslationArticleVersionContentEntityRepository versionContentRepository;

    @Autowired
    private TranslationArticleEntityRepository translationArticleRepository;

    private final Long USER_ID = 1L;

    private final Long ARTICLE_ID = 1L;

    private final Long INITIAL_VERSION = 1L;

    private final String INITIAL_TITLE = "TITLE";

    private final String INITIAL_CONTENT = "CONETENT";

    private final int ITERATION = 1000;

    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    @Test
    void 비동기_update_id_1000회_시도() throws InterruptedException {
        // Given
        readyDocument();

        // When
        asyncUpdateAndWait(ITERATION);

        //  all.join() 했는데 로컬에서 쓰레드 반환 안되었다고 에러 떠서 추가함 ...
        Thread.sleep(1000);

        // Then
        // iteration 횟수만큼 버전이 생겼는지 확인 .
        checkVersions(ITERATION);

    }

    private void checkVersions(int iteration) {
        List<TranslationArticleVersionContentEntity>
                versionContents = versionContentRepository.findAll();

        Set<Long> versions = new HashSet<>();

        versionContents.stream()
                .forEach(e -> {
                    if (e.getArticleId() == ARTICLE_ID)
                        versions.add(e.getVersion());
                });

        assertEquals(ITERATION + 1, versions.size());
        assertEquals(ITERATION + INITIAL_VERSION, versions.stream().mapToInt(x -> x.intValue()).max().getAsInt());
        assertEquals(INITIAL_VERSION, versions.stream().mapToInt(x -> x.intValue()).min().getAsInt());
    }

    private void asyncUpdateAndWait(int iter) {

        List<CompletableFuture<Boolean>> completableFutureList =
                IntStream.range(0, ITERATION)
                        .mapToObj(e -> CompletableFuture.supplyAsync(() -> {
                            updateService.update(
                                    new TranslationArticleUpdateRequest(
                                            ARTICLE_ID,
                                            INITIAL_VERSION,
                                            String.valueOf(e) + "content"
                                    ), USER_ID
                            );
                            log.info("{}th update ", e);
                            return Boolean.TRUE;
                        }).exceptionally(ex -> {
                            log.error("Error during update operation", ex);
                            return Boolean.FALSE; // 실패한 경우 false 반환
                        }))
                        .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[ITERATION]));

        log.info("Wait until all future close");
        // 모든 요청이 종료될때까지 대기.
        allOf.join();


        log.info("All future joined");

    }

    void readyDocument() {
        var article = monkey.giveMeBuilder(TranslationArticleEntity.class)
                .set("articleId", ARTICLE_ID)
                .set("title", INITIAL_TITLE)
                .set("authorId", USER_ID)
                .set("techTags", Set.of(TechTag.JAVA))
                .sample();
        translationArticleRepository.save(article);

        var versionContent = monkey.giveMeBuilder(TranslationArticleVersionContentEntity.class)
                .set("versionContentId", 0L)
                .set("articleId", ARTICLE_ID)
                .set("version", INITIAL_VERSION)
                .set("editorId", USER_ID)
                .set("content", INITIAL_CONTENT)
                .sample();
        versionContentRepository.save(versionContent);
    }

}
