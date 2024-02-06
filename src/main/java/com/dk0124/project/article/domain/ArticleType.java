package com.dk0124.project.article.domain;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum ArticleType {
    TECH, TRANSLATION;

    private static final Map<String, ArticleType> stringToEnum
            = Stream.of(values()).collect(toMap(Object::toString, e -> e));

    public static Optional<ArticleType> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
}
