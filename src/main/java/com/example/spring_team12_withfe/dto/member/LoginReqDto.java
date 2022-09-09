package com.example.spring_team12_withfe.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginReqDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}