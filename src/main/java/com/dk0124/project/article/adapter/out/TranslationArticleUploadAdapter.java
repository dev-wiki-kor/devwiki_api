package com.dk0124.project.article.adapter.out;

import com.dk0124.project.article.adapter.out.entity.TranslationArticleEntity;
import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.application.port.out.TranslationArticleUploadPort;
import com.dk0124.project.article.domain.TranslationArticleUploadCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationArticleUploadAdapter implements TranslationArticleUploadPort {

    private final TranslationArticleEntityRepository articleEntityRepository;
    private final TranslationArticleVersionContentEntityRepository versionContentEntityRepository;

    @Override
    public void upload(TranslationArticleUploadCommand command) {

        var articleMeta = articleEntityRepository.save(
                TranslationArticleEntity.of(
                        command.authorId(),
                        command.title(),
                        command.techTags()
                )
        );

        versionContentEntityRepository.save(
                TranslationArticleVersionContentEntity.of(
                        articleMeta.getArticleId(),
                        0L,
                        command.authorId(),
                        command.content()
                )
        );
    }
}
