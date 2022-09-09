package com.example.spring_team12_withfe.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentReqDto {
    private Long reviewId;
    private String comment;
}
