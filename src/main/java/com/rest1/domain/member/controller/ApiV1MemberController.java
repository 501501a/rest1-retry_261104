package com.rest1.domain.member.controller;

import com.rest1.domain.member.dto.MemberDto;
import com.rest1.domain.member.dto.MemberLoginDto;
import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ApiV1MemberController {

    private final MemberService memberService;

    record MemberJoinReqBody(
            @NotBlank
            String username,
            @NotBlank
            String password,
            @NotBlank
            String nickname
    ){}

    @PostMapping("/api/v1/members")
    public MemberDto join(
            @Valid @RequestBody MemberJoinReqBody reqBody
    ){
        Member member = memberService.join(reqBody.username(), reqBody.password(), reqBody.nickname());
        return new MemberDto(member);
    }

    record MemberLoginReqBody(
            @NotBlank
            String username,
            @NotBlank
            String password
    ){}

    @PostMapping("/api/v1/members/login")
    public MemberLoginDto login(
            @Valid @RequestBody MemberLoginReqBody reqBody
    ){
       Member member = memberService.login(reqBody.username(), reqBody.password());
       String accessToken = memberService.genAccessToken(member);
        return new MemberLoginDto(member, accessToken);
    }

}
