package com.killdongmu.Hanghae99Week5BackEnd.controller;


import com.killdongmu.Hanghae99Week5BackEnd.security.MemberDetails;
import com.killdongmu.Hanghae99Week5BackEnd.service.BoardService;
import com.killdongmu.Hanghae99Week5BackEnd.service.CommentService;
import com.killdongmu.Hanghae99Week5BackEnd.service.MemberService;
import com.killdongmu.Hanghae99Week5BackEnd.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@RestController
public class MypageController {

    private final MypageService mypageService;


    @GetMapping("/{username}")
    public ResponseEntity<?> findMemberInfo(@PathVariable String username ) {

        return mypageService.findMemberInfo(username);
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<?> findMyBoardList() {
//
//        return mypageService.findMyBoardList();
//
//    }
//
//
//    @GetMapping("/{username}")
//    public ResponseEntity<?> findMyCommentList(Long boardId) {
//
//        return mypageService.findMyCommentList(boardId);
//
//    }
//
//    @GetMapping("/{username}")
//    public ResponseEntity<?> findMyHeartedList(Long boardId) {
//
//        return mypageService.findMyHeartList(boardId);
//    }

}



// /api/mypage      .. api/mypage/userid