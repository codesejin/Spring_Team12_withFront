package com.example.spring_team12_withfe.repository;

import com.example.spring_team12_withfe.domain.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface Book_ReviewRepository extends JpaRepository<BookReview,Long> {
    Optional<BookReview> findById(Long id);

    List<BookReview> findByMemberIdAndTitleAndAuthor(Long member_id, String title, String Author);

    Page<BookReview> findByMemberId(Long member_id,Pageable pageable);

    Page<BookReview> findAllByOrderByHeartCntDesc(Pageable pageable);

}
