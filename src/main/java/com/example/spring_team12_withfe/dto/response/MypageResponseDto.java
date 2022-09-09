package com.example.spring_team12_withfe.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MypageResponseDto {
    private String username;
    private List<Book_ReviewResponseDto> Book_List;
}
