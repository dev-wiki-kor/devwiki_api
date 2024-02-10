package com.dk0124.project.article.translation;


import com.dk0124.project.article.adapter.in.TranslationArticleUploadRequest;
import com.dk0124.project.article.adapter.out.TranslationArticleUploadAdapter;
import com.dk0124.project.article.domain.TranslationArticleUploadCommand;
import com.dk0124.project.article.application.service.TranslationArticleUploadService;
import com.dk0124.project.article.exception.InvalidTitleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class TranslationArticleUploadServiceTest {

    @Mock
    private TranslationArticleUploadAdapter uploadAdapter;

    @InjectMocks
    private TranslationArticleUploadService uploadService;

    private final Long VALID_UPLOADER_ID = 1L;

    @Test
    void 업로드_성공() {
        TranslationArticleUploadRequest request = new TranslationArticleUploadRequest(
                "Title", "Content", Set.of("JAVA")
        );

        doNothing().when(uploadAdapter).upload(any(TranslationArticleUploadCommand.class));

        assertDoesNotThrow(() -> uploadService.upload(request, VALID_UPLOADER_ID));
    }

    @Test
    void 업로드_실패_제목없음() {
        TranslationArticleUploadRequest invalidRequest = new TranslationArticleUploadRequest(
                "", "11", Set.of("JAVA")
        );

        assertThrows(InvalidTitleException.class, () -> uploadService.upload(invalidRequest, VALID_UPLOADER_ID));
    }


    @Test
    void 업로드_실패_옳바르지_않은_태그() {
        TranslationArticleUploadRequest invalidRequest = new TranslationArticleUploadRequest(
                "", "11", Set.of("JAVA2")
        );

        assertThrows(InvalidTitleException.class, () -> uploadService.upload(invalidRequest, VALID_UPLOADER_ID));
    }
}