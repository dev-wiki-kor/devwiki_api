package com.dk0124.project.article.adapter.out.entity;

import com.dk0124.project.global.constants.ArticleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ARTICLE_ACTION_HISTORY")
@NoArgsConstructor
public class ArticleActionHistoryEntity {
    public enum ModifyType {
        CREATE, UPDATE, DELETE;
    }

    @Id
    @Column(name = "ARTICLE_ACTION_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "USER_ID")
    Long userId;

    @Column(name = "ARTICLE_ID")
    Long articleId;

    @Column(name = "VERSION_ID")
    Long versionId;

    @Enumerated(EnumType.STRING)
    ModifyType modifyType;

    @Enumerated(EnumType.STRING)
    ArticleType articleType;

    private ArticleActionHistoryEntity(Long userId, Long articleId, Long versionId, ModifyType modifyType, ArticleType articleType) {
        this.userId = userId;
        this.articleId = articleId;
        this.versionId = versionId;
        this.modifyType = modifyType;
        this.articleType = articleType;
    }

    public static ArticleActionHistoryEntity of(Long userId, Long articleId, Long versionId, ModifyType modifyType, ArticleType articleType) {
        return new ArticleActionHistoryEntity(
                userId,
                articleId,
                versionId,
                modifyType,
                articleType
        );
    }
}
