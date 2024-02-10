package com.dk0124.project.article.adapter.out;

import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.domain.SaveNewContentCommand;
import com.dk0124.project.article.application.port.out.VersionContentSavePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionContentSaveAdapter implements VersionContentSavePort {

    private final TranslationArticleVersionContentEntityRepository versionContentRepository;

    @Override
    public void saveNewContent(SaveNewContentCommand saveNewContentCommand) {

        versionContentRepository.save(
                TranslationArticleVersionContentEntity.of(
                        saveNewContentCommand.articleId(),
                        saveNewContentCommand.newVersion(),
                        saveNewContentCommand.editor(),
                        saveNewContentCommand.content()
                )
        );

    }
}
