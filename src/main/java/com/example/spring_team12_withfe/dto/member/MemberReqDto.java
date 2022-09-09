package com.example.spring_team12_withfe.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberReqDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
