package com.example.spring_team12_withfe.controller;
import com.example.spring_team12_withfe.dto.response.B_ResponseDto;
import com.example.spring_team12_withfe.dto.response.ResponseDto;
import com.example.spring_team12_withfe.dto.request.Book_ReviewRequestDto;
import com.example.spring_team12_withfe.dto.request.ReviewRequestDto;
import com.example.spring_team12_withfe.service.Book_ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class Book_ReviewController {
    private final Book_ReviewService book_reviewService;

    @GetMapping("/home")
    public B_ResponseDto<?> getAllbook_review(@RequestParam("page")int page,
                                              @RequestParam("size")int size){
        return book_reviewService.getAllbook_review(page, size);
    }

    @GetMapping("/review/{id}")
    public ResponseDto<?> getbook_review(@PathVariable Long id) {
        return book_reviewService.getbook_review(id);
    }

    @PostMapping("/auth/review")
    public ResponseDto<?> createReview(@RequestBody Book_ReviewRequestDto requestDto, HttpServletRequest request){
        return book_reviewService.create(requestDto,request);
    }


    @PutMapping("/auth/review/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @RequestBody ReviewRequestDto requestDto,HttpServletRequest request){
        return book_reviewService.update(id,requestDto,request);
    }
    @DeleteMapping("/auth/review/{id}")
    public ResponseDto<?> delete(@PathVariable Long id,HttpServletRequest request){
        return book_reviewService.delete(id,request);
    }

}

