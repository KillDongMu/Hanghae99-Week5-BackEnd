package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.dto.TokenDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.request.LoginRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.request.SignupRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.dto.request.TokenRequestDto;
import com.killdongmu.Hanghae99Week5BackEnd.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return memberService.createMember(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return memberService.loginMember(loginRequestDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        System.out.println(tokenRequestDto);
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }
}
