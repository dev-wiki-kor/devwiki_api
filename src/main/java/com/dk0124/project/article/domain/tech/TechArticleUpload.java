package com.dk0124.project.article.domain.tech;

import com.dk0124.project.article.domain.Content;
import com.dk0124.project.global.constants.TechTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Set;

@AllArgsConstructor
@Getter
public class TechArticleUpload {

    @NonNull
    private Long uploaderId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private Content content;

    @NotNull(message = "Tech tags are required")
    private Set<TechTag> techTags;
}