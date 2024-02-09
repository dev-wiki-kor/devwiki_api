package com.dk0124.project.article.application.service;

import com.dk0124.project.article.adapter.in.TechArticleUpdateRequest;
import com.dk0124.project.article.adapter.in.TechArticleUploadRequest;
import com.dk0124.project.article.adapter.out.entity.ArticleActionHistoryEntity;
import com.dk0124.project.article.adapter.out.entity.TechArticleEntity;
import com.dk0124.project.article.adapter.out.repository.ArticleActionHistoryEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TechArticleEntityRepository;
import com.dk0124.project.article.application.TechArticleService;
import com.dk0124.project.article.application.port.out.AuthorPort;
import com.dk0124.project.article.domain.ArticleType;
import com.dk0124.project.article.domain.Author;
import com.dk0124.project.article.domain.Content;
import com.dk0124.project.article.domain.tech.TechArticleDetail;
import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.dk0124.project.article.exception.InvalidUserException;
import com.dk0124.project.global.constants.TechTag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechArticleServiceImpl implements TechArticleService {

    private final TechArticleEntityRepository repository;
    private final ArticleActionHistoryEntityRepository historyEntityRepository;
    private final AuthorPort authorPort;

    @Override
    public void upload(TechArticleUploadRequest request, Long userId) {
        TechArticleEntity article = createArticleEntity(request, userId);
        TechArticleEntity saved = repository.save(article);
        saveArticleActionHistory(saved, ArticleActionHistoryEntity.ModifyType.CREATE);
    }

    @Override
    @Transactional
    public void update(TechArticleUpdateRequest request, Long userId) {
        TechArticleEntity article = repository.findById(request.articleId())
                .orElseThrow(() -> new InvalidArticleIdException());

        validateUserAccess(article, userId);
        updateArticleFields(article, request);
        saveArticleActionHistory(article, ArticleActionHistoryEntity.ModifyType.UPDATE);
    }

    @Override
    public void delete(Long articleId, Long userId) {
        TechArticleEntity article = repository.findById(articleId)
                .orElseThrow(() -> new InvalidArticleIdException());

        validateUserAccess(article, userId);
        repository.delete(article);
        saveArticleActionHistory(article, ArticleActionHistoryEntity.ModifyType.DELETE);
    }

    @Override
    public TechArticleDetail detail(Long articleId) {
        TechArticleEntity article = repository.findById(articleId)
                .orElseThrow(() -> new InvalidArticleIdException());

        Author author = authorPort.get(article.getAuthorId());
        repository.updateViewCount(articleId, article.getVersion_());
        return convertToDetail(article, author);
    }

    private TechArticleEntity createArticleEntity(TechArticleUploadRequest request, Long userId) {
        Set<TechTag> techTags = request.techTags().stream()
                .map(TechTag::valueOf)
                .collect(Collectors.toSet());

        return TechArticleEntity.of(userId, request.title(), request.content(), techTags);
    }

    private void validateUserAccess(TechArticleEntity article, Long userId) {
        if (!article.getAuthorId().equals(userId)) {
            throw new InvalidUserException();
        }
    }

    private void updateArticleFields(TechArticleEntity article, TechArticleUpdateRequest request) {
        Optional.ofNullable(request.title()).ifPresent(article::setTitle);
        Optional.ofNullable(request.content()).ifPresent(article::setContent);
        Optional.ofNullable(request.techTags()).ifPresent(tags ->
                article.setTechTags(tags.stream().map(TechTag::valueOf).collect(Collectors.toSet())));
    }

    private void saveArticleActionHistory(TechArticleEntity article, ArticleActionHistoryEntity.ModifyType modifyType) {
        historyEntityRepository.save(ArticleActionHistoryEntity.of(
                article.getAuthorId(),
                article.getArticleId(),
                0L,
                modifyType, ArticleType.TECH
        ));
    }

    private TechArticleDetail convertToDetail(TechArticleEntity article, Author author) {
        return TechArticleDetail.of(
                article.getArticleId(),
                author,
                article.getTitle(),
                new Content(article.getContent()),
                article.getTechTags(),
                article.getLikes(),
                article.getDisLikes(),
                article.getComments(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }
}