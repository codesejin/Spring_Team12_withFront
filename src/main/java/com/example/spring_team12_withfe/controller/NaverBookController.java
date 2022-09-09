package com.example.spring_team12_withfe.controller;


import com.example.spring_team12_withfe.dto.response.NaverBookResponseDto;
import com.example.spring_team12_withfe.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class NaverBookController {

    private final BookService bookService;

    @GetMapping("/api/books/search")
    public NaverBookResponseDto getBooks(@RequestParam String query) {
        return bookService.getOpenAPI(query);
    }



}
