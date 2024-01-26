package com.dk0124.project.common.user.join;

public class JoinFailException extends RuntimeException {

    public JoinFailException(){
        super();
    }

    public JoinFailException(String message){
        super(message);
    }
}
