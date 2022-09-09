package com.example.spring_team12_withfe.service;

import com.example.spring_team12_withfe.domain.BookReview;
import com.example.spring_team12_withfe.domain.Member;
import com.example.spring_team12_withfe.dto.response.Book_ReviewResponseDto;
import com.example.spring_team12_withfe.dto.response.MypageResponseDto;
import com.example.spring_team12_withfe.jwt.TokenProvider;
import com.example.spring_team12_withfe.repository.Book_ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final Book_ReviewRepository book_reviewRepository;
    private final TokenProvider tokenProvider;


    public MypageResponseDto mypage(HttpServletRequest request, int page, int size) {
        Member member = validateMember(request);

        if(member == null)
            throw new NullPointerException("Token이 유효하지 않습니다.");

        Pageable pageable = PageRequest.of(page, size);
        Page<BookReview> book_reviewList = book_reviewRepository.findByMemberId(member.getId(),pageable);

        List<Book_ReviewResponseDto> book_review_List = new ArrayList<>();

        for(BookReview book_review : book_reviewList){
            book_review_List.add(Book_ReviewResponseDto.builder()
                    .id(book_review.getId())
                    .username(member.getUsername())
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

        return MypageResponseDto.builder()
                        .username(member.getUsername())
                        .Book_List(book_review_List)
                        .build();
    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
