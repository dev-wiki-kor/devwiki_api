package com.dk0124.project.common.session;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultUUIDHolder implements UUIDHolder{
    @Override
    public UUID random() {
        return UUID.randomUUID();
    }
}
