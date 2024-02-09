package com.dk0124.project.global.config.lock;

import jakarta.transaction.Transactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class LockManageTransaction {
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
