package com.example.spring_team12_withfe.domain;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "heart")
@Entity
public class Heart {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookReview_id")
    private BookReview bookReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Heart(BookReview bookReview, Member member){
        this.bookReview = bookReview;
        this.member = member;
    }
}
