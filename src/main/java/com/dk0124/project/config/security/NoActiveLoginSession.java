package com.dk0124.project.config.security;


public class NoActiveLoginSession extends RuntimeException {

    public NoActiveLoginSession() {
        super();
    }

    public NoActiveLoginSession(String message) {
        super(message);
    }
}
