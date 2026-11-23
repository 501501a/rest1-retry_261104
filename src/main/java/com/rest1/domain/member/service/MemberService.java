package com.rest1.domain.member.service;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.repository.MemberRepository;
import com.rest1.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member join(String username, String password, String nickname) {
        memberRepository.findByUsername(username).ifPresent(member -> {
            throw new ServiceException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        });

        String encodePassword = passwordEncoder.encode(password);
        String apiKey = UUID.randomUUID().toString();
        Member member = new Member(username, encodePassword, nickname, apiKey);
        return memberRepository.save(member);
    }
}
