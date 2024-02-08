package com.dk0124.project.article.adapter.out.entity;

import com.dk0124.project.global.constants.TechTag;
import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TECH_ARTICLE")
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE TECH_ARTICLE SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TechArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "TITLE", nullable = false, length = 512)
    @Setter
    private String title;

    @Column(name = "AUTHOR_ID")
    private Long authorId;

    // 본문 크기 제한 -> LongText
    @Column(name = "CONTENT", nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String content;

    @ElementCollection
    @CollectionTable(name = "article_tech_tags", joinColumns = @JoinColumn(name = "ARTICLE_ID"))
    @Column(name = "TECH_TAGS")
    @Enumerated(EnumType.STRING)
    @Setter
    private Set<TechTag> techTags = new HashSet<>();

    @Column(name = "VIEW_COUNT", nullable = false)
    @Setter
    private Long viewCount = 0L;

    @Column(name = "LIKES")
    private Long likes = 0L;

    @Column(name = "DISLIKES")
    private Long disLikes = 0L;

    @Column(name = "COMMENTS")
    private Long comments = 0L;

    @Column(name = "DELETED")
    private boolean deleted = Boolean.FALSE;

    @Column(name = "VERSION")
    private Long version = 0L;

    private TechArticleEntity(Long authorId, String title, String content, Set<TechTag> techTags) {
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.techTags = techTags;
    }

    public static TechArticleEntity of(Long authorId, String title, String content, Set<TechTag> techTags) {
        return new TechArticleEntity(authorId, title, content, techTags);
    }
}
