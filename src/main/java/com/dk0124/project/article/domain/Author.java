package com.dk0124.project.article.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공통-> 안변함
 * */
@AllArgsConstructor
@Getter
public class Author {
    @NotBlank
    private String userId;

    @NotBlank
    private String nickname;

    @NotBlank
    private String avatar;

}