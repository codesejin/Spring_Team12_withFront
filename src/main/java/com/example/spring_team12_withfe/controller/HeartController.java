package com.example.spring_team12_withfe.controller;

import com.example.spring_team12_withfe.dto.response.ResponseDto;
import com.example.spring_team12_withfe.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heart")
public class HeartController {

    private final HeartService heartService;

    // 좋아요 + 좋아요 해제 둘다 가능
    @PostMapping("/{bookReview_id}")
    public ResponseDto<?> heart(@PathVariable Long bookReview_id, HttpServletRequest request) throws IOException {
        return heartService.heart(bookReview_id,request);
    }

//    @DeleteMapping
//    public ResponseEntity<HeartDto> unHeart(@RequestBody @Valid HeartDto heartDto) {
//        heartService.unHeart(heartDto);
//        return new ResponseEntity<>(heartDto, HttpStatus.OK);
//    }
}
