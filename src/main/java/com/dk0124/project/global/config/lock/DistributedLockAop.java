package com.dk0124.project.global.config.lock;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final LockManageTransaction lockManageTransaction;


    @Around("@annotation(com.dk0124.project.global.config.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX +
                DistributedLockKeyParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(),
                        distributedLock.key());


        RLock rLock = redissonClient.getLock(key);

        try {
            log.info("TRY Lock For distributed lock on {}", Thread.currentThread());
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(),
                    distributedLock.timeUnit());
            if (!available) {
                log.error("FAIL Lock For distributed lock on {}", Thread.currentThread());
                return false;
            }

            log.error("GET Lock For distributed lock on {}", Thread.currentThread());
            return lockManageTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            try {
                if(rLock.isHeldByCurrentThread()){
                    rLock.unlock();
                }
            } catch (IllegalArgumentException e) {
                log.info("Redisson lock Already unlock {} of {}", key, method);
            }
        }
    }
}