package com.dk0124.project.global.config.security;


/*
* 403 에러 exception
* */
public class NoActiveLoginSession extends RuntimeException {

    public NoActiveLoginSession() {
        super();
    }

    public NoActiveLoginSession(String message) {
        super(message);
    }
}
