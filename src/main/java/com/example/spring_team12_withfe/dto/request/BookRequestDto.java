package com.example.spring_team12_withfe.dto.request;

import lombok.Getter;
import org.json.JSONObject;

@Getter
public class BookRequestDto {
    private Long id;
    private String thumbnail;
    private String title;
    private String author;
    private String publisher;


    public BookRequestDto(JSONObject book){
        this.id = book.getLong("isbn");
        this.thumbnail = book.getString("image");
        this.title = book.getString("title");
        this.author = book.getString("author");
        this.publisher = book.getString("publisher");
    }
}
