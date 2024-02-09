package com.dk0124.project.article.domain;

import com.dk0124.project.article.exception.InvalidContentException;
import com.dk0124.project.article.exception.InvalidTitleException;
import com.dk0124.project.article.exception.TagNotExsixtException;
import com.dk0124.project.global.constants.TechTag;

import java.util.Set;

public class ArticleValidator {
    public static void validateTitle(String title){
        if(title == null ||title.isBlank())
            throw new InvalidTitleException();

        if(title.length() <=1 || title.length()> 5 * 1024 )
            throw new InvalidTitleException();
    }

    public static void validateContent(String content){
        if(content == null || content.isBlank())
            throw new InvalidContentException();

        if(content.length() <=1 || content.length()> 10 * 1024 * 1024)
            throw new InvalidContentException();
    }

    public static void validateTags(Set<String> tags){
        tags.stream().forEach(
                e-> TechTag.fromString(e).orElseThrow(
                        ()-> new TagNotExsixtException()
                )
        );
    }
}
