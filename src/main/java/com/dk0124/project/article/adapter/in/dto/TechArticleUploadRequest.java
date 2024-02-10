package com.dk0124.project.article.adapter.in.dto;

import com.dk0124.project.global.constants.TechTag;

import java.util.Set;

public record TechArticleUploadRequest(
        String title,
        String content,
        Set<String> techTags
) {
}
