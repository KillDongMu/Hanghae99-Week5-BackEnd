package com.killdongmu.Hanghae99Week5BackEnd.controller;

import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/heart/{board-id}")
    public void clickHeart(@PathVariable("board-id") Long boardId,
                           @AuthenticationPrincipal MemberDetails memberDetails) {
        System.out.println(boardId);
        System.out.println(memberDetails);
        System.out.println("in");
        heartService.clickHeart(boardId, memberDetails.getMember());
    }



}
