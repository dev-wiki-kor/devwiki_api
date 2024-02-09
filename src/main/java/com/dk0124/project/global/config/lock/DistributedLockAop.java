package com.dk0124.project.global.config.lock;


import com.dk0124.project.article.adapter.out.ArticleVersionAdapter;
import com.dk0124.project.article.exception.CanNotGenerateVersionException;
import com.dk0124.project.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

    private static final String LOCK_PREFIX = "REDIS_LOCK:";

    private final DistributedLockManager lockManager;
    private final LockManageTransaction lockManageTransaction;

    @Around("@annotation(com.dk0124.project.global.config.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = LOCK_PREFIX +
                DistributedLockKeyParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(),
                        distributedLock.key());

        boolean locked = false;

        try {
            locked = lockManager.tryLock(key, distributedLock.leaseMillis(), distributedLock.waitMillis());
            if (!locked) {
                log.warn("Failed to acquire distributed lock for key: {}", key);
                throw new CanNotAcquireLockException(ExceptionCode.INTERNAL_SEVER_ERROR);
            }

            return lockManageTransaction.proceed(joinPoint);
        } finally {
            if (locked)
                lockManager.release(key);
        }
    }
}