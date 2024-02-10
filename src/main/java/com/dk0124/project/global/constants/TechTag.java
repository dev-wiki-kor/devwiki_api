package com.dk0124.project.global.constants;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum TechTag {

    JAVA, DB;


    private static final Map<String, TechTag> stringToEnum
            = Stream.of(values()).collect(toMap(Object::toString, e -> e));

    public static Optional<TechTag> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }


}
