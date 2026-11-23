package com.rest1.domain.member.dto;

import com.rest1.domain.member.entity.Member;

public record MemberLoginDto(
        String apiKey,
        MemberDto memberDto
){
    public MemberLoginDto(Member member){
        this(
                member.getApiKey(),
                new MemberDto(member)
        );
    }
}
