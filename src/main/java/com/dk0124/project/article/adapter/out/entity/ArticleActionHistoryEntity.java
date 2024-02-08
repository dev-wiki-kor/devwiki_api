package com.dk0124.project.article.adapter.out.entity;

import com.dk0124.project.article.domain.ArticleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ARTICLE_ACTION_HISTORY")
@NoArgsConstructor
public class ArticleActionHistoryEntity {
    enum ModifyType {
        CREATE, EDIT, DELETE;
    }

    @Id
    @Column(name = "ARTICLE_ACTION_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "USER_ID")
    Long userId;

    @Column(name = "USER_ID")
    Long articleId;

    @Column(name = "VERSION_ID")
    Long versionId;

    @Enumerated(EnumType.STRING)
    ModifyType modifyType;

    @Enumerated(EnumType.STRING)
    ArticleType articleType;

}
