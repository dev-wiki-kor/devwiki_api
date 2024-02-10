package com.dk0124.project.article.application.port.out;

import com.dk0124.project.article.domain.SaveNewContentCommand;

public interface VersionContentSavePort {
    void saveNewContent(SaveNewContentCommand saveNewContentCommand);
}
