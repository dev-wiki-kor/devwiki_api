package com.dk0124.project.config.security.loginSession;


public class NoActiveLoginSession extends RuntimeException {

    public NoActiveLoginSession() {
        super();
    }

    public NoActiveLoginSession(String message) {
        super(message);
    }
}
