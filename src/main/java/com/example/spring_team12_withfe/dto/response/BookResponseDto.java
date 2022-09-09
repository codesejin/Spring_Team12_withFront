package com.example.spring_team12_withfe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookResponseDto {
    private Long id;
    private String thumbnail;
    private String title;
    private String author;
    private String publisher;
}
