package com.rest1.global.security;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String apiKey = header.replace("Bearer ", "");

        String[] token = apiKey.split(" ");
        apiKey = token[0];

        Map<String, Object> payload = null;

        if (1 < token.length){
            String accessToken = token[1];
            payload = memberService.payloadOrNull(accessToken);
        }
        String username = "";
        if (payload != null){
            username = (String) payload.get("username");
        }
        if (username.isEmpty()){
            Member member = memberService.findByApiKey(apiKey).orElse(null);
            if (member != null){
                username = member.getUsername();
            }
        }
        if (username.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        // SecurityContextHolder에 인증데이터 저장
        UserDetails user = new User(
                username,
                "",
                List.of()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

}
