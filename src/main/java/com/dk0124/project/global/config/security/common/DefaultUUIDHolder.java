package com.dk0124.project.global.config.security.common;

import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class DefaultUUIDHolder implements UUIDHolder {

    @Override
    public UUID createOne() {
        return UUID.randomUUID();
    }
}
