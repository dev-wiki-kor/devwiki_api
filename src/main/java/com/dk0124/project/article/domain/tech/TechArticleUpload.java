package com.dk0124.project.article.domain.tech;

import com.dk0124.project.article.domain.Content;
import com.dk0124.project.article.exception.TagNotExsixtException;
import com.dk0124.project.global.constants.TechTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class TechArticleUpload {

    @NonNull
    private final Long uploaderId;

    @NotBlank(message = "Title is required")
    private final String title;

    @NotBlank(message = "Content is required")
    private final Content content;

    @NotNull(message = "Tech tags are required")
    private final Set<TechTag> techTags;

    public Set<TechTag> getTechTags() {
        return Collections.unmodifiableSet(techTags);
    }


    public static TechArticleUpload of(Long uploaderId, String title, String content, Set<String> techTags) {


        return new TechArticleUpload(
                uploaderId,
                title,
                new Content(content),
                techTags.stream()
                        .map(e -> TechTag.fromString(e)
                                .orElseThrow(() -> new TagNotExsixtException()))
                        .collect(Collectors.toSet())
        );

    }
}