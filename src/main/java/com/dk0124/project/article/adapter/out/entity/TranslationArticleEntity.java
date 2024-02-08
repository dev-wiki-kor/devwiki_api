package com.dk0124.project.article.adapter.out.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSLATION_ARTICLE")
public class TranslationArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "TITLE", nullable = false, length = 512)
    private String title;

    @JoinColumn(name = "AUTHOR_ID")
    private Long authorId;


    @ElementCollection
    @CollectionTable(name = "ARTICLE_TAGS", joinColumns = @JoinColumn(name = "ARTICLE_TAGS"))
    @Column(name = "TECH_TAGS")
    private Set<String> techTags = new HashSet<>();

    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount = 0L;

    @Column(name = "LIKES")
    private Long likes = 0L;

    @Column(name = "DISLIKES")
    private Long disLikes = 0L;

    @Column(name = "COMMENTS")
    private Long comments = 0L;
}
