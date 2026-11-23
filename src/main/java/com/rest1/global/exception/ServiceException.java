package com.rest1.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter                                   // ③ status 칸을 꺼내는 getStatus()를 롬복이 만들어 줘요
public class ServiceException extends RuntimeException {

    private final HttpStatus status;      // ① 내가 새로 만든 칸: 상태 코드 (예: HttpStatus.CONFLICT = 409)

    public ServiceException(HttpStatus status, String message) {
        super(message);                   // ② 메시지는 부모의 칸에 보관 → 나중에 getMessage()로 꺼내요
        this.status = status;             // ① 상태 코드는 내 칸에 보관
    }
}