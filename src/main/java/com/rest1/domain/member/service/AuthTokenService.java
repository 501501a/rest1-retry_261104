package com.rest1.domain.member.service;

import com.rest1.domain.member.entity.Member;
import com.rest1.standard.ut.Ut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {
    @Value("${custom.jwt.secretPattern}")
    private String secretKey;
    @Value("${custom.jwt.expiration}")
    private long expireTime;

    public String genAccessToken(Member member) {
        return Ut.jwt.toString(secretKey, expireTime,
                Map.of("id", member.getId(),
                        "username", member.getUsername(),
                        "nickname", member.getNickname()
                )
        );
    }

    public Map<String, Object> payloadOrNull(String token){
        return Ut.jwt.payloadOrNull(token, secretKey);
    }
}
