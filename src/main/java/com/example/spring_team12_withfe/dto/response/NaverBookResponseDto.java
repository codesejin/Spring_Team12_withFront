package com.example.spring_team12_withfe.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class NaverBookResponseDto {
    List<BookResponseDto> infos;
}
