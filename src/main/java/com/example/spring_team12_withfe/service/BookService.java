package com.example.spring_team12_withfe.service;



import com.example.spring_team12_withfe.dto.response.BookResponseDto;
import com.example.spring_team12_withfe.dto.response.NaverBookResponseDto;

import com.example.spring_team12_withfe.dto.request.BookRequestDto;
import com.example.spring_team12_withfe.dto.response.NaverBookResponseDto;
import com.example.spring_team12_withfe.utils.NaverBookSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final NaverBookSearch naverBookSearch;

    public NaverBookResponseDto getOpenAPI(String query) {
        String result = naverBookSearch.search(query);
        List<BookRequestDto> naver_book = naverBookSearch.fromJSONtoBooks(result);

        List<BookResponseDto> books = new ArrayList<>();
        for(BookRequestDto book : naver_book){
            books.add(
                    BookResponseDto.builder()
                            .id(book.getId())
                            .thumbnail(book.getThumbnail())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .publisher(book.getPublisher())
                            .build()
            );
        }
        return NaverBookResponseDto.builder()
                .infos(books)
                .build();
    }


}
