package com.kh146.web;

import com.kh146.domain.board.Bbs;
import com.kh146.domain.board.svc.BbsSVC;
import com.kh146.domain.common.code.Code;
import com.kh146.domain.common.code.CodeDAO;
import com.kh146.web.form.board.AddForm;
import com.kh146.web.form.board.DetailForm;
import com.kh146.web.form.board.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
public class BakingClassController {
//    아무래도 이상해서 시험 삼아 따로 매핑해본다
    private final BbsSVC bbsSVC;
    private final CodeDAO codeDAO;

//   베이킹 클래스 찾기 카테고리 B04 - B0401
    @ModelAttribute("classifier")
    public List<Code> classifier(){return codeDAO.code("B04");}



    //    작성 양식
    @GetMapping("/add")
    public String addForm(Model model){
//        1) 새 폼 객체 = 새로운 양식을 만든다.
        AddForm addForm = new AddForm();
//        2) 로그인 세션의 작성자 아이디와 닉네임을 미리 세팅한다

//        3) 모델 객체에 폼을 세팅
        model.addAttribute("addForm", addForm);
        return "/board/addForm";
    }

    //    작성 처리.
    @PostMapping("/add")
    public String add(@ModelAttribute AddForm addForm,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) throws IOException {
//        오류가 났으면 작성 양식에 머무름
        if(bindingResult.hasErrors()){
            log.info("add/bindingResult={}",bindingResult);
            return "bbs/addForm";
        }

//      1)  새 글 객체 생성하고 폼 입력값을 복사.
        Bbs bbs = new Bbs();
        BeanUtils.copyProperties(addForm, bbs);

        return "redirect:/map/{id}/detail";
    }


    //    상세 조회
    @GetMapping("/{id}/detail")
    public String detailForm(@PathVariable Long id,
                             Model model){
//        조회할 게시물
        Bbs foundItem = bbsSVC.findById(id);
//        빈 객체
        DetailForm detailForm = new DetailForm();
        BeanUtils.copyProperties(foundItem, detailForm);

        model.addAttribute("detail", detailForm);

        return "/board/detailForm";
    }

    //    수정 양식
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model){
//        수정할 게시글을 찾고,
        Bbs foundItem = bbsSVC.findById(id);
//        디테일 폼의 내용을 그대로 복사해와야한다
        EditForm editForm = new EditForm();

//        모델 객체에 담아 반환
        model.addAttribute("editForm", editForm);

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



//    베이킹 클래스 찾기 화면 = 게시판
    @GetMapping
    public String bakingClass(){

        return "/board/bakingClass";
    }



}
