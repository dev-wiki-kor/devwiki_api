package com.dk0124.project.article.adapter.out.entity;


import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_version_contents")
@NoArgsConstructor
@Getter
public class TranslationArticleVersionContentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_VERSION_CONTENT_ID")
    private Long versionContentId;

    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "VERSION", nullable = false)
    private Long version;

    @JoinColumn(name = "EDITOR_ID")
    private Long editorId;

    @Column(name = "CONETNT", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

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


    private TranslationArticleVersionContentEntity(Long articleId, Long version, Long editorId, String content) {
        this.articleId = articleId;
        this.version = version;
        this.editorId = editorId;
        this.content = content;
    }

    public static TranslationArticleVersionContentEntity of(Long articleId, Long version, Long editorId, String content) {
        return new TranslationArticleVersionContentEntity(
                articleId,
                version,
                editorId,
                content
        );
    }
}
