package com.dk0124.project.user.application;

//TODO : 쿠키는 변수명 바꾸자 jsession string 이런걸로
public record GithubLoginRequest(String code, String cookie) {}
