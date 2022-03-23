package com.kh146.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/map")
//@RequiredArgsConstructor
public class BakingClassController {

//    지도화면의 게시판 이용 처리가 대부분. 게시판 컨트롤러와 똑같이 작성한다

//    뷰 매핑
    @GetMapping
    public String bakingClass(){

        return "/bakingClass/bakingClass";
    }

//    작성 양식
    @GetMapping("/add")
    public String bakingClassForm(){

        return "/bakingClass/bakingClassForm";
    }



}
