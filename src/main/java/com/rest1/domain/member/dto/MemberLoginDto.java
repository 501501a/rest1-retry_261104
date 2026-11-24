package com.rest1.domain.member.dto;

import com.rest1.domain.member.entity.Member;

public record MemberLoginDto(
        String apiKey,
        String accessToken,
        MemberDto memberDto
){
    public MemberLoginDto(Member member, String accessToken){
        this(
                member.getApiKey(),
                accessToken,
                new MemberDto(member)
        );
    }
}
