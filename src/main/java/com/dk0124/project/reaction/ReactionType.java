package com.dk0124.project.reaction;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum ReactionType {
    LIKE, DISLIKE;

    private static final Map<String, ReactionType> stringToEnum
            = Stream.of(values()).collect(toMap(Object::toString, e -> e));

    public static Optional<ReactionType> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
}
