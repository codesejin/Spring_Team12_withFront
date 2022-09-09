package com.example.spring_team12_withfe.controller;


import com.example.spring_team12_withfe.dto.response.ResponseDto;
import com.example.spring_team12_withfe.dto.request.CommentReqDto;
import com.example.spring_team12_withfe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 등록하기
    @PostMapping("/comment")
    public ResponseDto<?> createComment(@RequestBody CommentReqDto commentReqDto,  HttpServletRequest request) {
        return commentService.createComment(commentReqDto, request);
    }
    //댓글 보여주기
    @GetMapping("/comment")
    public ResponseDto<?> showComment(){
        return commentService.showComment();
    }

    //댓글 수정하기

    @PutMapping("/comment/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @RequestBody CommentReqDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(commentId,requestDto,request);
    }

    //댓글 삭제하기
    @DeleteMapping("/comment/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, HttpServletRequest request){
        return commentService.deleteComment(commentId, request);
    }

}
