package com.dk0124.project.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.dk0124.project.global.exception.ExceptionCode.INTERNAL_SEVER_ERROR;
import static com.dk0124.project.global.exception.ExceptionCode.INVALID_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        log.warn(e.getMessage(), e);

        final String errMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(INVALID_REQUEST.getCode(), errMessage));
    }

    /**
     * application 로직에 의해 관리되는 커스텀 에러
     * */

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final ApplicationException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }


    /**
     * 외부 호출에서 발생하는 에러
     * */
    @ExceptionHandler(GithubApiException.class)
    public ResponseEntity<ExceptionResponse> handleGithubApiException(final GithubApiException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    /**
     * 처리 못한 에러 .
     * */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(INTERNAL_SEVER_ERROR.getCode(), INTERNAL_SEVER_ERROR.getMessage()));
    }
}


/**
 *
 * 기본으로 핸들되는 익셉션 목록 .
 *           HttpRequestMethodNotSupportedException.class,
 * 			HttpMediaTypeNotSupportedException.class,
 * 			HttpMediaTypeNotAcceptableException.class,
 * 			MissingPathVariableException.class,
 * 			MissingServletRequestParameterException.class,
 * 			MissingServletRequestPartException.class,
 * 			ServletRequestBindingException.class,
 * 			MethodArgumentNotValidException.class,
 * 			HandlerMethodValidationException.class,
 * 			NoHandlerFoundException.class,
 * 			NoResourceFoundException.class,
 * 			AsyncRequestTimeoutException.class,
 * 			ErrorResponseException.class,
 * 			MaxUploadSizeExceededException.class,
 * 			ConversionNotSupportedException.class,
 * 			TypeMismatchException.class,
 * 			HttpMessageNotReadableException.class,
 * 			HttpMessageNotWritableException.class,
 * 			MethodValidationException.class,
 * 			BindException.class
 * */