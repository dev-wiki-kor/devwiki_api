package com.dk0124.project.user.exception;

public class GithubAuthFailException extends RuntimeException {

    public GithubAuthFailException(){
        super();
    }

    public GithubAuthFailException(String message){
        super(message);
    }

}
