package com.dk0124.project.article.adapter.out.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "article_version_contents")
public class TranslationArticleVersionContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_VERSION_CONTENT_ID")
    private Long versionContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSLATION_ARTICLE_ID")
    private TranslationArticleEntity translationArticle;

    @Column(name = "VERSION", nullable = false)
    private Long version;

    @JoinColumn(name = "EDITOR_ID")
    private Long editor_id;

    @Column(name = "CONETNT", nullable = false, columnDefinition = "LONGTEXT")
    private String content;


}
