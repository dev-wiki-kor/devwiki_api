package com.dk0124.project.article.domain.translation;

import com.dk0124.project.article.domain.Content;
import com.dk0124.project.global.constants.TechTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@Getter
public class TranslationArticleUpload {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private Content content;

    @NotNull(message = "Tech tags are required")
    private Set<TechTag> techTags;

    @NotBlank(message = "Editor ID is required")
    private String editorId;

    public Set<TechTag> getTechTags() {
        return Collections.unmodifiableSet(techTags);
    }
}
