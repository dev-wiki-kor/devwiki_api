package com.dk0124.project.reaction.article;

import com.dk0124.project.global.constants.ArticleType;
import com.dk0124.project.global.jpa.BaseEntity;
import com.dk0124.project.reaction.ReactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "REACTION")
@NoArgsConstructor
@Getter
public class ReactionEntity  extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "REACTION_ID")
    private Long reactionId;

    @Column(name = "REACTION_TYPE")
    @Enumerated(EnumType.STRING)
    @Setter
    private ReactionType reactionType;

    @Column(name = "ARTICLE_TYPE")
    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    private Long articleId;

    private Long version;

    @Column(name = "USER_ID")
    private Long userId;


    private ReactionEntity(ReactionType reactionType, ArticleType articleType, Long articleId, Long version, Long userId) {
        this.reactionType = reactionType;
        this.articleType = articleType;
        this.articleId = articleId;
        this.version = version;
        this.userId = userId;
    }

    public static ReactionEntity of(ReactionType reactionType, ArticleType articleType, Long articleId, Long version, Long userId) {
        return new ReactionEntity(reactionType, articleType, articleId, version, userId);
    }
}
