package com.dk0124.project.common.user.application;

import jakarta.servlet.http.Cookie;

public record GithubLoginRequest(String code, String cookie) {}
