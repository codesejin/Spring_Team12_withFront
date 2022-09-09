package com.example.spring_team12_withfe.repository;

import com.example.spring_team12_withfe.domain.BookReview;
import com.example.spring_team12_withfe.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBookReview(BookReview book_review);
}
