package com.dk0124.project.config.security.common;

import com.dk0124.project.config.security.common.UUIDHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class DefaultUUIDHolder implements UUIDHolder {

    @Override
    public UUID createOne() {
        return UUID.randomUUID();
    }
}
