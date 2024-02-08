package com.dk0124.project.article.domain.tech;

import com.dk0124.project.article.domain.Author;
import com.dk0124.project.article.domain.Content;
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
public class TechArticleDetail {
    @NonNull
    private final Long articleId;

    @NonNull
    private final Author author;

    @NotBlank(message = "Title is required")
    @Size(max = 512)
    private final String title;

    @NotNull(message = "Content is required")
    private final Content content;

    @NotNull(message = "Tech tags are required")
    private final Set<TechTag> techTags;

    @Min(0)
    private final Long likesCount;
    @Min(0)
    private final Long dislikesCount;
    @Min(0)
    private final Long commentCount;

    @NonNull
    private final LocalDateTime createdAt;
    @NonNull
    private final LocalDateTime updatedAt;

    public Set<TechTag> getTechTags() {
        return Collections.unmodifiableSet(techTags);
    }
}
