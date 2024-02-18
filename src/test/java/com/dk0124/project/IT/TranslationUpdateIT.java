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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
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

    private final int ITERATION = 100;

    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();


    @BeforeEach
    void deleteAll(){
        versionContentRepository.deleteAll();
        translationArticleRepository.deleteAll();
    }

    @Test
    void 비동기_update_id_100회_시도() throws InterruptedException {
        // Given
        readyDocument();

        // When
        asyncUpdateAndWait(ITERATION);

        // Then
        // iteration 횟수만큼 버전이 생겼는지 확인 .
        checkVersions(ITERATION);

    }
    @Transactional
    private void checkVersions(int iteration) {
        List<TranslationArticleVersionContentEntity>
                versionContents = versionContentRepository.findAll();

        ConcurrentHashMap.KeySetView<Long,Boolean> versions = ConcurrentHashMap.newKeySet();

        versionContents.stream()
                .forEach(e -> {
                    if (e.getArticleId() == ARTICLE_ID)
                        versions.add(e.getVersion());
                });

        assertEquals(ITERATION + 1, versions.size());
        assertEquals(ITERATION + INITIAL_VERSION, versions.stream().mapToInt(x -> x.intValue()).max().getAsInt());
        assertEquals(INITIAL_VERSION, versions.stream().mapToInt(x -> x.intValue()).min().getAsInt());
    }

    private void asyncUpdateAndWait(int iter) throws InterruptedException {

        List<CompletableFuture<Boolean>> completableFutureList =
                IntStream.range(0, ITERATION)
                        .mapToObj(e -> CompletableFuture.supplyAsync(() -> {
                            doUpdate();
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

        Thread.sleep(1000);

    }

    @Transactional
    private void doUpdate() {
        doLog();
        updateService.update(
                new TranslationArticleUpdateRequest(
                        ARTICLE_ID,
                        INITIAL_VERSION,
                        "content"
                ), USER_ID
        );
    }

    void readyDocument() {
        var article = monkey.giveMeBuilder(TranslationArticleEntity.class)
                .set("articleId", ARTICLE_ID)
                .set("title", INITIAL_TITLE)
                .set("authorId", USER_ID)
                .set("techTags", Set.of(TechTag.JAVA))
                .set("deleted", Boolean.FALSE)
                .sample();
        translationArticleRepository.saveAndFlush(article);

        var versionContent = monkey.giveMeBuilder(TranslationArticleVersionContentEntity.class)
                .set("versionContentId", 0L)
                .set("articleId", ARTICLE_ID)
                .set("version", INITIAL_VERSION)
                .set("editorId", USER_ID)
                .set("content", INITIAL_CONTENT)
                .set("deleted", Boolean.FALSE)
                .sample();
        versionContentRepository.saveAndFlush(versionContent);
    }


    @Transactional
    void doLog(){

        log.info("-----------------------DO LOG-----------------------------------");
        String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        boolean isActualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();

        log.info("Thread: {}, Transaction: {}, Active: {}, TransactionManager: {}",
                Thread.currentThread().getName(),
                transactionName,
                isActualTransactionActive
              );
    }
}

