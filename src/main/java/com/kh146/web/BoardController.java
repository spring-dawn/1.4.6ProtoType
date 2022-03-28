package com.kh146.web;

import com.kh146.domain.board.Bbs;
import com.kh146.domain.board.svc.BbsSVC;
import com.kh146.domain.common.code.Code;
import com.kh146.domain.common.code.CodeDAO;
import com.kh146.domain.common.paging.PageCriteria;
import com.kh146.web.form.board.DetailForm;
import com.kh146.web.form.board.ListForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")   //헉 매핑 어떻게 넣어야 하지. 여기서부터 카테고리 코드 입력을 받아야 하네
@RequiredArgsConstructor
public class BoardController {
//    아직 첨부파일 준비물을 임포트하지 않았다. 대략적 뼈대 잡기.
    private final BbsSVC bbsSVC;
    private final CodeDAO codeDAO;

//    페이징 구현
    @Autowired
    @Qualifier("pc10")
    private PageCriteria pc;

    //게시판 코드, 디코드 가져오기
    @ModelAttribute("classifier")
    public List<Code> classifier(){
//        13개 게시판 코드를 전부 반환
        return codeDAO.code();
    }


//    각 카테고리별 게시판으로 이동. 카테고리 매개값을 받아야만 한다.
    @GetMapping("/{reqPage}")
    public String list(
            @PathVariable Integer reqPage,
            @RequestParam String bcategory,
            Model model
            ){

//      1) 사용자 입력 - 요청 페이지
        pc.getRc().setReqPage(reqPage);

//       2) 게시판 타입의 리스트 객체를 생성
        List<Bbs> list = null;

//       3) 해당 게시판의 게시물 총 개수를 구하고 페이징 적용 메소드로 게시판 출력
        pc.setTotalRec(bbsSVC.totalCount(bcategory));
         list = bbsSVC.findBoardByCategory(bcategory, pc.getRc().getStartRec(), pc.getRc().getEndRec());

//       4)ListForm과 데이터를 대조해 복사
        List<ListForm> partOfList = new ArrayList<>();
        for (Bbs bbs : list) {
            ListForm listForm = new ListForm();
            BeanUtils.copyProperties(bbs, listForm);
            partOfList.add(listForm);
        }
        model.addAttribute("list", partOfList);
        model.addAttribute("pc", pc);

//      각각의 카테고리로 이동.
        if(bcategory.equals("B0401")){
            return "/board/bakingClass";
        }else if(bcategory.equals("B0501")){
            return "/board/QnA";
        }else if(bcategory.equals("B0502")){
            return "/board/free";

        }else{
            return "/board/list";
        }

    }



//  공통 CRUD
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
    public String detailForm(
            @PathVariable Long id,
            Model model
    ){
        Bbs detailBbs = bbsSVC.findById(id);
        DetailForm detailForm = new DetailForm();
        BeanUtils.copyProperties(detailBbs, detailForm);
        model.addAttribute("detailForm", detailForm);

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





}
