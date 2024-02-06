package com.dk0124.project.article.domain;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


/**
 * MD content 에 대한 크기, 정책 validation ... 여기는 무조건 확장될거라 별도 클래스로 생성 .
 *
 * */
@AllArgsConstructor
@Getter
public class Content {
    @NonNull
    @Size(min = 1)
    private final String content;
}
