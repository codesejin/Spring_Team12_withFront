package com.example.spring_team12_withfe.service;

import com.example.spring_team12_withfe.domain.Comment;
import com.example.spring_team12_withfe.domain.Member;
import com.example.spring_team12_withfe.domain.BookReview;
import com.example.spring_team12_withfe.dto.response.*;
import com.example.spring_team12_withfe.dto.request.Book_ReviewRequestDto;
import com.example.spring_team12_withfe.dto.request.ReviewRequestDto;
import com.example.spring_team12_withfe.jwt.TokenProvider;
import com.example.spring_team12_withfe.repository.Book_ReviewRepository;
import com.example.spring_team12_withfe.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Book_ReviewService {
    private final Book_ReviewRepository book_reviewRepository;

    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;


    @Transactional
    public B_ResponseDto<?> getAllbook_review(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<BookReview> bookReviews = book_reviewRepository.findAllByOrderByHeartCntDesc(pageable);
        List<Book_ReviewResponseDto> book_reviewResponseDto = new ArrayList<>();

        for(BookReview book_review : bookReviews){
            book_reviewResponseDto.add(
                    Book_ReviewResponseDto.builder()
                            .id(book_review.getId())
                            .username(book_review.getMember().getUsername())
                            .thumbnail(book_review.getThumbnail())
                            .title(book_review.getTitle())
                            .author(book_review.getAuthor())
                            .publisher(book_review.getPublisher())
                            .review(book_review.getReview())
                            .heart(book_review.getHeartCnt())
                            .createdAt(book_review.getCreatedAt())
                            .modifiedAt(book_review.getModifiedAt())
                            .build()
            );
        }
        return B_ResponseDto.success(book_reviewResponseDto);
    }


    // 전체 책/리뷰 조회
    @Transactional
    public ResponseDto<?> getbook_review(Long id){
        BookReview book_review = isParesentReview(id);
        if(book_review == null)
            ResponseDto.fail("NOT_FOUND_REVIEW" ,"리뷰가 존재하지 않습니다.");

        List<Comment> commentList= commentRepository.findAllByBookReview(book_review);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();


        for(Comment comment:commentList){
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


        return ResponseDto.success(
                Book_ReviewResponseDto.builder()
                        .id(book_review.getId())
                        .username(book_review.getMember().getUsername())
                        .thumbnail(book_review.getThumbnail())
                        .title(book_review.getTitle())
                        .author(book_review.getAuthor())
                        .publisher(book_review.getPublisher())
                        .review(book_review.getReview())
                        .comments(commentResponseDtoList)
                        .heart(book_review.getHeartCnt())
                        .createdAt(book_review.getCreatedAt())
                        .modifiedAt(book_review.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> create(Book_ReviewRequestDto requestDto, HttpServletRequest request) {
        Member member = validateMember(request);

        if (member == null) {
            throw new NullPointerException("Token이 유효하지 않습니다.");
        }



        BookReview book_review = BookReview.builder()
                .thumbnail(requestDto.getThumbnail())
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .publisher(requestDto.getPublisher())
                .review(requestDto.getReview())
                .heartCnt(0L)
                .member(member)
                .build();


        // 유저 ID, 책 제목, 책 저자만 가져오기
        List<BookReview> bookReview = book_reviewRepository.findByMemberIdAndTitleAndAuthor(member.getId(),requestDto.getTitle(), requestDto.getAuthor());

        // 같은 책/리뷰에 중복 작성 확인
        if(bookReview.size() == 1)
            return ResponseDto.fail("ALREADY_REVIEW","이미 리뷰를 작성하셨습니다.");

        book_reviewRepository.save(book_review);

        return ResponseDto.success(
                Book_ReviewResponseDto.builder()
                        .id(book_review.getId())
                        .thumbnail(book_review.getThumbnail())
                        .title(book_review.getTitle())
                        .author(book_review.getAuthor())
                        .publisher(book_review.getPublisher())
                        .username(book_review.getMember().getUsername())
                        .review(book_review.getReview())
                        .heart(book_review.getHeartCnt())
                        .createdAt(book_review.getCreatedAt())
                        .modifiedAt(book_review.getModifiedAt())
                        .build()
        );

    }

    @Transactional
    public ResponseDto<?> update(Long id,ReviewRequestDto requestDto,HttpServletRequest request){
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        BookReview book_review = isParesentReview(id);
        if(book_review == null)
            return ResponseDto.fail("NOT_FOUND_REVIEW", "리뷰가 존재하지 않습니다.");

        if(book_review.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST","작성자만 수정할 수 있습니다.");
        }
        book_review.update(requestDto);
        return ResponseDto.success(
                ReviewResponseDto.builder()
                        .id(book_review.getId())
                        .review(requestDto.getReview())
                        .username(book_review.getMember().getUsername())
                        .createdAt(book_review.getCreatedAt())
                        .modifiedAt(book_review.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> delete(Long id,HttpServletRequest request){
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        BookReview book_review = isParesentReview(id);
        if(book_review == null)
            return ResponseDto.fail("NOT_FOUND_REVIEW", "리뷰가 존재하지 않습니다.");

        if(book_review.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST","작성자만 삭제할 수 있습니다.");
        }

        book_reviewRepository.deleteById(id);
        return ResponseDto.success("");
    }


    @Transactional
    public BookReview isParesentReview(Long id){
        Optional<BookReview> optionalPost = book_reviewRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}

