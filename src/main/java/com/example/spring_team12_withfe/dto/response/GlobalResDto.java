package com.example.spring_team12_withfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResDto {
    private String msg;
    private int statusCode;
}