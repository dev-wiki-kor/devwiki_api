package com.dk0124.project.article.adapter.out;

import com.dk0124.project.article.adapter.out.repository.TranslationArticleEntityRepository;
import com.dk0124.project.article.adapter.out.repository.TranslationArticleVersionContentEntityRepository;
import com.dk0124.project.article.application.port.out.AuthorPort;
import com.dk0124.project.article.application.port.out.TranslationArticleDetailPort;
import com.dk0124.project.article.exception.InvalidArticleVersionException;
import com.dk0124.project.article.domain.Content;
import com.dk0124.project.article.domain.translation.TranslationArticleDetail;
import com.dk0124.project.article.domain.translation.VersionContent;
import com.dk0124.project.article.exception.InvalidArticleIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationArticleDetailAdapter implements TranslationArticleDetailPort {

    private final TranslationArticleEntityRepository articleRepository;
    private final TranslationArticleVersionContentEntityRepository versionContentRepository;

    private final AuthorPort authorPort;

    @Override
    public TranslationArticleDetail getDetail(Long articleId, Long version) {
        var article = articleRepository.findById(articleId)
                .orElseThrow(InvalidArticleIdException::new);

        var versionContent = versionContentRepository.findByArticleIdAndVersion(articleId, version)
                .orElseThrow(InvalidArticleVersionException::new);

        var articleAuthor = authorPort.get(articleId);
        var editor = authorPort.get(versionContent.getEditorId());

        return TranslationArticleDetail.of(
                article.getArticleId(),
                articleAuthor,
                article.getTitle(),
                VersionContent.of(
                        editor.getUserId(),
                        editor.getNickname(),
                        new Content(versionContent.getContent()),
                        "",
                        versionContent.getVersion(),
                        versionContent.getLikes(),
                        versionContent.getDisLikes(),
                        versionContent.getComments(),
                        versionContent.getViewCount(),
                        versionContent.getCreatedAt()
                ),
                article.getTechTags(),
                article.getLikes(),
                article.getDisLikes(),
                article.getComments(),
                article.getViewCount(),
                article.getCreatedAt()
        );
    }
}
