package com.example.spring_team12_withfe.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Book_ReviewRequestDto {
    private Long id;
    private String thumbnail;
    private String title;
    private String author;
    private String publisher;
    private String review;
}
