package com.rest1.domain.member.service;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.repository.MemberRepository;
import com.rest1.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

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

    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow(()-> new ServiceException(HttpStatus.UNAUTHORIZED,  "로그인에 실패했습니다."));
        String encodedPassword = member.getPassword();
        if (!passwordEncoder.matches(password, encodedPassword)){
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다.");
        }
        return member;
    }

    public String genAccessToken(Member member){
        return authTokenService.genAccessToken(member);
    }

    public Optional<Member> findByApiKey(String apiKey){
       return memberRepository.findByApiKey(apiKey);
    }

    public Optional<Member> findByUsername(String username){
        return memberRepository.findByUsername(username);
    }

    public Map<String, Object> payloadOrNull(String token){
        return authTokenService.payloadOrNull(token);
    }
}
