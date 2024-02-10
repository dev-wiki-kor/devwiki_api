package com.dk0124.project.article.adapter.out.entity;


import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "article_version_contents")
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE Tarticle_version_contents SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TranslationArticleVersionContentEntity extends BaseEntity {

    private final Long INITIAL_VERSION = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_VERSION_CONTENT_ID")
    private Long versionContentId;

    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "VERSION", nullable = false)
    private Long version = INITIAL_VERSION;

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

    @Column(name = "DELETED")
    private boolean deleted = Boolean.FALSE;


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
