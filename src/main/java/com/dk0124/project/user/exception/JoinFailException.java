package com.dk0124.project.user.exception;

public class JoinFailException extends RuntimeException {

    public JoinFailException(){
        super();
    }

    public JoinFailException(String message){
        super(message);
    }
}
