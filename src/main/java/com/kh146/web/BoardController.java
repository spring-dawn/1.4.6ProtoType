package com.kh146.web;

import com.kh146.domain.board.svc.BbsSVC;
import com.kh146.domain.common.code.Code;
import com.kh146.domain.common.code.CodeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")   //헉 매핑 어떻게 넣어야 하지. 여기서부터 카테고리 코드 입력을 받아야 하네
@RequiredArgsConstructor
public class BoardController {
//    아직 첨부파일, 페이징을 임포트하지 않았다. 대략적 뼈대 잡기.
    private final BbsSVC bbsSVC;
    private final CodeDAO codeDAO;

    //게시판 코드, 디코드 가져오기
    //모든 게시판 아우르는 컨트롤러. 필요하다 코드 클래져파이.
    @ModelAttribute("classifier")
    public List<Code> classifier(){
//        13개 게시판 코드를 전부 반환
        return codeDAO.code();
    }


//    작성 양식
    @GetMapping("/add")
    public String addForm(){

        return "/board/addForm";
    }

//    작성 처리.
    @PostMapping("/add")
    public String add(){

        return "redirect:/board/{id}/detail";
    }


//    상세 조회
    @GetMapping("/{id}/detail")
    public String detailForm(){

        return "/board/detailForm";
    }


//    수정 양식
    @GetMapping("/{id}/edit")
    public String editForm(){

        return  "/board/editForm";
    }

//    수정 처리
    @PostMapping("/{id}/edit")
    public String edit(){

        return  "redirect:/board/{id}/detail";
    }

//    삭제
    @GetMapping("/{id}/delete")
    public String delete(){

        return "redirect:/board";
    }


//    전체 목록...?
    @GetMapping("/list")
    public String list(){

        return "/board/list";
    }








}
