package com.example.spring_team12_withfe.repository;

import com.example.spring_team12_withfe.domain.BookReview;
import com.example.spring_team12_withfe.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    // 동일한 리뷰에 동일한 계정으로 이미 좋아요한 내역이 있는지 찾을 때 사용할 메소드

    //레포지토리 쿼리메소드 이름은 엔티티의 컬럼 변수명이랑 동일해야한다(반환타입X)
    List<Heart> findByMemberIdAndBookReviewId(Long member, Long bookReview_id);

}