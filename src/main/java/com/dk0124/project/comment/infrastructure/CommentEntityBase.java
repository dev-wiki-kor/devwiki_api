package com.dk0124.project.comment.infrastructure;

import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@MappedSuperclass
public class CommentEntityBase extends BaseEntity {

    public CommentEntityBase(String content, Long commentOrder, Long level, Long sortNumber, Long parentId, Long writerId) {
        this.content = content;
        this.commentOrder = commentOrder;
        this.level = level;
        this.sortNumber = sortNumber;
        this.parentId = parentId;
        this.writerId = writerId;
    }

    @Setter
    @Column(name = "CONTENT")
    private String content;

    @Setter
    @Column(name = "DELETED")
    private Boolean deleted = Boolean.FALSE;

    @Setter
    @Column(name = "DELETED_REASON")
    @Enumerated(EnumType.STRING)
    private CommentDeletedReason deletedReason = null;


    /**
     * 0 depth 댓글의 순서 -> 정렬용 데이터 .
     * 따닥 방지를 위한 distributed lock 필요 .
     */
    @Column(name = "COMMENT_ORDER")
    private Long commentOrder;

    /**
     * 래밸 0: 댓글
     * 래밸 1: depth 1 의 대댓글
     * 래밸 2: depth 2 의 대댓글
     */
    @Column(name = "LEVEL")
    private Long level;

    @Column(name = "SORT_NUMBER")
    private Long sortNumber;


    @Column(name = "PARENT_ID")
    private Long parentId;


    @Column(name = "writer_id")
    private Long writerId;

}
