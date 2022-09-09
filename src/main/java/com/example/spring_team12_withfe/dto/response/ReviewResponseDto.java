package com.example.spring_team12_withfe.dto.response;

import com.example.spring_team12_withfe.domain.Heart;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ReviewResponseDto {
    private Long id;
    private String review;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
