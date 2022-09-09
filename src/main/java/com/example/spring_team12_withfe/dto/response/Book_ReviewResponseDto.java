package com.example.spring_team12_withfe.dto.response;

import com.example.spring_team12_withfe.domain.Heart;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
public class Book_ReviewResponseDto {
    private Long id;
    private String thumbnail;
    private String title;
    private String author;
    private String publisher;
    private String username;
    private String review;
    private List<CommentResponseDto> comments;
    private Long heart;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
