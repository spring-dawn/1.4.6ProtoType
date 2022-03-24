package com.kh146.web;

import com.kh146.domain.board.svc.BbsSVC;
import com.kh146.domain.common.code.CodeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")   //헉 매핑 어떻게 넣어야 하지.
@RequiredArgsConstructor
public class BoardController {
//    모든 게시판 아우르는 컨트롤러. 필요하다 코드 클래져파이.
    private final BbsSVC bbsSVC;
    private final CodeDAO codeDAO;


//    작성 양식
    @GetMapping("/add")
    public String boardForm(){

        return "/bakingClass/bakingClassForm";
    }

//    작성 처리



}
