package com.dk0124.project.article.adapter.out.entity;

import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TECH_ARTICLE")
public class TechArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "TITLE", nullable = false, length = 512)
    private String title;

    @Column(name = "AUTHOR_ID")
    private Long authorId;

    // 본문 크기 제한 -> LongText
    @Column(name = "CONTENT", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @ElementCollection
    @CollectionTable(name = "article_tech_tags", joinColumns = @JoinColumn(name = "ARTICLE_ID"))
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
