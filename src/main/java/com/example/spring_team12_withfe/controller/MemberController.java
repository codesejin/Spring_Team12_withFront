package com.example.spring_team12_withfe.controller;


import com.example.spring_team12_withfe.dto.member.LoginReqDto;
import com.example.spring_team12_withfe.dto.member.MemberReqDto;
import com.example.spring_team12_withfe.dto.response.MypageResponseDto;
import com.example.spring_team12_withfe.dto.response.ResponseDto;
import com.example.spring_team12_withfe.service.MemberService;
import com.example.spring_team12_withfe.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MypageService mypageService;

    @RequestMapping(value = "/member/signup", method = RequestMethod.POST)
    public ResponseDto<?> signup(@RequestBody @Valid MemberReqDto requestDto) {
        return memberService.createMember(requestDto);
    }


    @RequestMapping(value = "/member/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginReqDto requestDto,
                                HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    @RequestMapping(value = "/member/logout", method = RequestMethod.POST)
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }


    @RequestMapping(value = "/auth/member/mypage" , method = RequestMethod.GET)
    public MypageResponseDto mypage(HttpServletRequest request,
                                    @RequestParam("page")int page,
                                    @RequestParam("size")int size){
        return mypageService.mypage(request,page,size);
    }

//    @GetMapping("/issue/token")
//    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
//        //refresh토큰을 주면서 access토큰을 발급하는 API
//        response.addHeader(JwtFilter.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getUsername(), "Access"));
//        return new GlobalResDto("issuedToken Success", HttpStatus.OK.value());
//    }

}