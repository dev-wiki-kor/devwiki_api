package com.dk0124.project.article.application.service;

import com.dk0124.project.article.domain.SaveNewContentCommand;

public interface VersionContentSavePort {
    void saveNewContent(SaveNewContentCommand saveNewContentCommand);
}
