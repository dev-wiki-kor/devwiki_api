package com.dk0124.project.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentCursor {
    private final Long commentOrder;
    private final Long sortNumber;
}
