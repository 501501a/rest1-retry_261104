package com.rest1.domain.member.service;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
@ActiveProfiles("test")
class AuthTokenServiceTest {

    @Autowired
    private AuthTokenService authTokenService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("accessToken 발급 후 확인")
    void t1() throws Exception {
        Member member = memberRepository.findByUsername("user1").get();
        String accessToken = authTokenService.genAccessToken(member);
        assertThat(accessToken).isNotBlank();
        Map<String, Object> payload = authTokenService.payloadOrNull(accessToken);
        assertThat(payload).containsEntry("username", member.getUsername());
        assertThat(payload).containsEntry("id", member.getId().intValue());

    }


}