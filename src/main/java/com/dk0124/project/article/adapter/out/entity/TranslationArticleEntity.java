package com.dk0124.project.article.adapter.out.entity;

import com.dk0124.project.global.constants.TechTag;
import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSLATION_ARTICLE")
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE TRANSLATION_ARTICLE SET deleted = true WHERE ARTICLE_ID = ?")
@Where(clause = "deleted = false")
public class TranslationArticleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "TITLE", nullable = false, length = 512)
    private String title;

    @JoinColumn(name = "AUTHOR_ID")
    private Long authorId;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ARTICLE_TAGS", joinColumns = @JoinColumn(name = "ARTICLE_TAGS"))
    @Column(name = "TECH_TAGS")
    @Enumerated(EnumType.STRING)
    private Set<TechTag> techTags = new HashSet<>();

    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount = 0L;

    @Column(name = "LIKES")
    private Long likes = 0L;

    @Column(name = "DISLIKES")
    private Long disLikes = 0L;

    @Column(name = "COMMENTS")
    private Long comments = 0L;

    @Column(name = "VERSION_")
    private Long version_ = 0L;

    @Column(name = "DELETED")
    private boolean deleted = Boolean.FALSE;

    private TranslationArticleEntity(String title, Long authorId, Set<TechTag> techTags) {
        this.title = title;
        this.authorId = authorId;
        this.techTags = techTags;
    }

    public static TranslationArticleEntity of(Long authorId, String title, Set<TechTag> techTags) {
        return new TranslationArticleEntity(title, authorId, techTags);
    }
}
