package com.example.spring_team12_withfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BResponseDto<T> { //T가 아니고 A/B어떤거든 상관없음 제네릭임을 선언
    private boolean success;
    private T books; //제네릭의 변수 선언방법
    private Error error;

    @AllArgsConstructor
    @Getter
    public static class Error {
        private String code;
        private String message;
    }


    public static <T> BResponseDto<T> success(T books) {

        return new BResponseDto<>(true, books, null);
    }
}