package com.example.spring_team12_withfe.domain;

import com.example.spring_team12_withfe.dto.request.CommentReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookReview_id")
    private BookReview bookReview;

    @Column(nullable = false)
    private String comment;

    public Comment(CommentReqDto commentReqDto) {

        this.comment = commentReqDto.getComment();
    }
    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

    public void update(CommentReqDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
