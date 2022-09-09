package com.example.spring_team12_withfe.service;


import com.example.spring_team12_withfe.domain.Member;
import com.example.spring_team12_withfe.dto.response.ResponseDto;
import com.example.spring_team12_withfe.domain.Comment;
import com.example.spring_team12_withfe.dto.request.CommentReqDto;
import com.example.spring_team12_withfe.dto.response.CommentResponseDto;
import com.example.spring_team12_withfe.domain.BookReview;
import com.example.spring_team12_withfe.jwt.TokenProvider;
import com.example.spring_team12_withfe.repository.BookReviewRepository;

import com.example.spring_team12_withfe.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BookReviewRepository book_reviewRepository;

    private final CommentRepository commentRepository;

    private final TokenProvider tokenProvider;
    //댓글 등록하기
    @Transactional
    public ResponseDto<?> createComment(CommentReqDto commentReqDto, HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        BookReview book_review = isPresentBook_review(commentReqDto.getReviewId());
        if (null == book_review) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 리뷰 id 입니다.");
        }
        Comment comment = Comment.builder()
                .member(member)
                .bookReview(book_review)
                .comment(commentReqDto.getComment())
                .build();
        commentRepository.save(comment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .username(member.getUsername())
                        .comment(comment.getComment())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public BookReview isPresentBook_review(Long id) {
        Optional<BookReview> optionalBook_review = book_reviewRepository.findById(id);
        return optionalBook_review.orElse(null);
    }

        //댓글 보여주기
    public ResponseDto<?> showComment() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            //좋아요 개수 추가
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .username(comment.getMember().getUsername())
                            .comment(comment.getComment())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()

            );
        }
        return ResponseDto.success(commentResponseDtoList);
    }


    //댓글 수정하기
    @Transactional
    public ResponseDto<?> updateComment(Long commentId, CommentReqDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        BookReview book_review =  isPresentBook_review(requestDto.getReviewId());
        if (book_review == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 리뷰 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        comment.update(requestDto);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .username(comment.getMember().getUsername())
                        .comment(comment.getComment())
                        .createdAt(comment.getCreatedAt())
                        .build());
    }

    //댓글 삭제하기

    public ResponseDto<?> deleteComment(Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
        return ResponseDto.success("success");
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalPost = commentRepository.findById(id);
        return optionalPost.orElse(null);
    }

}

