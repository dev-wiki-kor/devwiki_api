package com.dk0124.project.global.config.lock;

import com.dk0124.project.article.exception.CanNotGenerateVersionException;
import com.dk0124.project.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Component
@RequiredArgsConstructor
public class DistributedLockManager {
    private final StringRedisTemplate stringRedisTemplate;

    private final Long RETRY_MILLIS = 30L;


    /**
     * 메모 : setIfAbsenst 가 atomic 한 연산이라 추가로 동시성에 대한 제어가 필요하지 않을 것 같음 .
     */
    private boolean acquire(String key, Long leaseTime) {
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(key, "locked", Duration.of(leaseTime, ChronoUnit.MILLIS));
        return Boolean.TRUE.equals(success);
    }

    public boolean tryLock(String key,Long leaseTime, Long waitTime) throws InterruptedException {

        int tryCount = (int) Math.ceil( ( 0.0+ waitTime)/RETRY_MILLIS);

        for (int i = 0; i < tryCount; i++) {
            if (acquire(key, leaseTime)) {
                return true;
            }
            Thread.sleep(RETRY_MILLIS);
        }
        throw new CanNotAcquireLockException(ExceptionCode.INTERNAL_SEVER_ERROR);
    }

    public void release(String key) {
        stringRedisTemplate.delete(key);
    }
}
