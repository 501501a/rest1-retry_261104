package com.rest1.domain.member.dto;

import com.rest1.domain.member.entity.Member;

import java.time.LocalDateTime;

public record MemberDto(
        Long id,
        String username,
        String nickname,
        LocalDateTime createDate
){
    public MemberDto(Member member){
        this(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getCreateDate()
        );
    }
}
