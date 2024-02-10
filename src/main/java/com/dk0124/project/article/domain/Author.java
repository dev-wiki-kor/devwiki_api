package com.dk0124.project.article.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공통-> 안변함
 * */
@AllArgsConstructor(staticName = "of")
@Getter
public class Author {
    @NotBlank
    private Long userId;

    @NotBlank
    private String nickname;

    @NotBlank
    private String githubUrl;

}