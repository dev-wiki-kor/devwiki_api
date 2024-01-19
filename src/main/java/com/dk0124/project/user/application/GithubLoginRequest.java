package com.dk0124.project.user.application;

import jakarta.servlet.http.Cookie;

public record GithubLoginRequest(String code, String cookie) {}
