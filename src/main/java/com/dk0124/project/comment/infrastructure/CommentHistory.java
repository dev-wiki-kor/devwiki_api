package com.dk0124.project.comment.infrastructure;



import com.dk0124.project.global.constants.ArticleType;
import com.dk0124.project.global.constants.HistoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMMETN_HISTORY")
@NoArgsConstructor
@Getter
public class CommentHistory {

    @Id
    @GeneratedValue
    @Column(name = "COMMENT_HISTORY_ID")
    private Long commentHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ARTICLE_TYPE")
    private ArticleType articleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRUD_TYPE")
    private HistoryType historyType;

    private Long userId;

    private Long articleId;

    private Long commentId;

    private Long version;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    private CommentHistory(ArticleType articleType, HistoryType historyType, Long userId, Long articleId, Long commentId) {
        this.articleType = articleType;
        this.historyType = historyType;
        this.userId = userId;
        this.articleId = articleId;
        this.commentId = commentId;
        this.version = version;
        this.createdAt = createdAt;
    }

    public static CommentHistory of(ArticleType articleType, HistoryType historyType, Long userId, Long articleId, Long commentId){
        return new CommentHistory(
                articleType,
                historyType,
                userId,
                articleId,
                commentId
        );
    }
}
