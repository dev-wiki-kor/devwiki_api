package com.dk0124.project.global.logging;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;

/***
 *
 * BODY 를 읽는 input stream 은 보안상 목적으로 2번 읽을 수 없음 ( 톰캣 기본 정책 )
 * 로깅 시, 바디를 남기고, 파라미터로 바디를 쓰고 싶다면 body 를 HttpServletRequestWrapper로 래핑해서 읽어야 함 .
 * */

public class ReadableRequestBodyWrapper extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public ReadableRequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        cacheRequestBody(request);
    }

    private void cacheRequestBody(HttpServletRequest request) throws IOException {
        var baos = new ByteArrayOutputStream();
        var is = request.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        cachedBody = baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        var bais = new ByteArrayInputStream(cachedBody);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException("Not supported");
            }
        };
    }

    public String getBody() throws IOException {
        return new String(cachedBody, getCharacterEncoding());
    }
}