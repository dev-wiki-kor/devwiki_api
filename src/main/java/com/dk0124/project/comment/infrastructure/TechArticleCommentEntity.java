package com.dk0124.project.comment.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TECH_ARTICLE_COMMENT")
@NoArgsConstructor
@Getter
public class TechArticleCommentEntity extends CommentEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;


    @Column(name = "article_id")
    private Long articleId;

    private TechArticleCommentEntity(String content, Long commentOrder, Long level, Long sortNumber, Long parentId, Long writerId) {
        super(content, commentOrder, level, sortNumber, parentId, writerId);
    }

    private TechArticleCommentEntity(Long articleId, String content, Long commentOrder, Long level, Long sortNumber, Long parentId, Long writerId) {
        super(content, commentOrder, level, sortNumber, parentId, writerId);
        this.articleId = articleId;
    }


    public static TechArticleCommentEntity of(Long articleId, String content, Long commentOrder, Long level, Long sortNumber, Long parentId, Long writerId) {
        return new TechArticleCommentEntity(articleId, content, commentOrder, level, sortNumber, parentId, writerId);
    }

}
