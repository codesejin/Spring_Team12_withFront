package com.example.spring_team12_withfe.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartDto {
    private Long reviewId;
    private Long memberId;
}