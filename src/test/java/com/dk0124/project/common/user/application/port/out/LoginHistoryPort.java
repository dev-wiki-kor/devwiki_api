package com.dk0124.project.common.user.application.port.out;


public interface LoginHistoryPort {
    void writeLoginHistory(Long userId);
}
