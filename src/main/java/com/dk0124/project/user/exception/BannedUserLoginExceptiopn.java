package com.dk0124.project.user.exception;

public class BannedUserLoginExceptiopn extends RuntimeException {

    public BannedUserLoginExceptiopn(){
        super();
    }

    public BannedUserLoginExceptiopn(String message){
        super(message);
    }
}
