package com.example.spring_team12_withfe.domain;

import com.example.spring_team12_withfe.dto.request.Book_ReviewRequestDto;
import com.example.spring_team12_withfe.dto.request.ReviewRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.*;


import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class BookReview extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column
    private String review;

    @Column
    private Long heartCnt = 0L;//개수 보여줄 용도


    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;


    @OneToMany(mappedBy = "bookReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;


    @JsonIgnore// Restcontroller에서  Heart엔티티를 JSON으로 반환하는 과정에서 recursion 에러 발생 => serialize(직렬화) 과정에서 무한재귀 발생 해결방안
    @OneToMany(mappedBy = "bookReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //지우는 용도
    private List<Heart> heart;


    public BookReview(Book_ReviewRequestDto requestDto){
        this.review = requestDto.getReview();
    }
    public void update(ReviewRequestDto requestDto){
        this.review = requestDto.getReview();
    }
    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
    public void setHeartCnt(Long heartCnt){
        this.heartCnt = heartCnt;
    }

}
