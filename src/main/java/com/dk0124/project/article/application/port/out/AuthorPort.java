package com.dk0124.project.article.application.port.out;

import com.dk0124.project.article.domain.Author;

public interface AuthorPort {

    Author get(Long authorId);
}
