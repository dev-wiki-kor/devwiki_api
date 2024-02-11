package com.dk0124.project.comment.infrastructure;

import com.dk0124.project.global.constants.ArticleType;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum CommentDeletedReason {
    DELETED, REPORTED;

    private static final Map<String, CommentDeletedReason> stringToEnum
            = Stream.of(values()).collect(toMap(Object::toString, e -> e));

    public static Optional<CommentDeletedReason> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
}
