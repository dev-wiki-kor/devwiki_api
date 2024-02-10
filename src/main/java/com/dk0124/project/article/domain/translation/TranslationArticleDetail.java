package com.dk0124.project.article.domain.translation;


import com.dk0124.project.article.domain.Author;
import com.dk0124.project.global.constants.TechTag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor(staticName = "of")
@Getter
public class TranslationArticleDetail {
    @NonNull
    private final Long id;

    @NonNull
    private final Author author;

    @NotBlank(message = "Title is required")
    @Size(max = 512)
    private final String title;

    @NonNull
    private final VersionContent versionContent;

    @NotNull(message = "Tech tags are required")// endered for display, e.g., HTML from Markdown
    private final Set<TechTag> techTags;

    @Min(0)
    private final Long totalLikesCount;
    @Min(0)
    private final Long totalDislikesCount;
    @Min(0)
    private final Long totalCommentCount;
    @Min(0)
    private final Long totalViews;

    @NonNull
    private final LocalDateTime createdAt;

    public Set<TechTag> getTechTags() {
        return Collections.unmodifiableSet(techTags);
    }
}