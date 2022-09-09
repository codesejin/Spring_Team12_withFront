package com.example.spring_team12_withfe.utils;

import com.example.spring_team12_withfe.dto.request.BookRequestDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class NaverBookSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "kupmuD94MnuYIy_B3uCI");
        headers.add("X-Naver-Client-Secret", "z7FIhZguZd");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/book.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public List<BookRequestDto> fromJSONtoBooks(String query) {
        JSONObject jsonObject = new JSONObject(query);
        JSONArray items = jsonObject.getJSONArray("items");
        List<BookRequestDto> bookDtoList = new ArrayList<>();

        for(int i = 0; i<items.length();i++){
            JSONObject itemJson = items.getJSONObject(i);
            BookRequestDto requestDto = new BookRequestDto(itemJson);
            bookDtoList.add(requestDto);
        }
        return bookDtoList;
    }
}