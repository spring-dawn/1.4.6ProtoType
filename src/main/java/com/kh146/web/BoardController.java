package com.kh146.web;

import com.kh146.domain.board.Bbs;
import com.kh146.domain.board.svc.BbsSVC;
import com.kh146.domain.common.code.Code;
import com.kh146.domain.common.code.CodeAll;
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
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/board")   //헉 매핑 어떻게 넣어야 하지. 여기서부터 카테고리 코드 입력을 받아야 하네
@RequiredArgsConstructor
public class BoardController {
//    아직 첨부파일 준비물을 임포트하지 않았다. 대략적 뼈대 잡기.
    private final BbsSVC bbsSVC;
    private final CodeDAO codeDAO;

//    페이징 구현 (10, 10) - 디폴트?
    @Autowired
    @Qualifier("pc10")
    private PageCriteria pc10;

//    페이징 구현 (9, 5)
    @Autowired
    @Qualifier("pc5")
    private PageCriteria pc5;

    //모든 하위 게시판 코드, 디코드 가져오기
    @ModelAttribute("classifier")
    public List<CodeAll> classifier(){
//        모든 일반 게시판의 상위-하위 코드를 반환
        return codeDAO.codeAll();
    }

    //상위 코드별 하위 코드 각각 가져오기
    @ModelAttribute("classifier2")
    public List<Code> classifier2(String pcode){
        pcode = "";
        switch (pcode){
            case "B01" :
                return codeDAO.code("B01");
            case "B02" :
                return codeDAO.code("B02");
            case "B03" :
                return codeDAO.code("B03");
            case "B04" :
                return codeDAO.code("B04");
            case "B05" :
                return codeDAO.code("B05");
            default:
                break;
        }

//        상위 코드에 따른 하위 코드 반환
        return codeDAO.code(pcode);
    }

//
//    //페이징이 없는 카테고리별 전체 목록
//    @GetMapping
//    public String list(
//            @RequestParam String bcategory,
//            Model model) {
//
//        List<Bbs> list = bbsSVC.findBoardByCategory(bcategory);
//
//        List<ListForm> partOfList = new ArrayList<>();
//        for (Bbs bbs : list) {
//            ListForm listForm = new ListForm();
//            BeanUtils.copyProperties(bbs, listForm);
//            partOfList.add(listForm);
//        }
//        model.addAttribute("list", partOfList);
//
//        //      각각의 카테고리로 이동.
//        if(bcategory.equals("B0401")){
//            return "/board/bakingClass";
//        }else if(bcategory.equals("B0501")){
//            return "/board/QnA";
//        }else if(bcategory.equals("B0502")){
//            return "/board/free";
//
//        }else{
//            return "/board/list";
//        }
//    }

    //    각 카테고리별 게시판으로 이동. 카테고리 매개값을 받아야만 한다.
    @GetMapping("/{reqPage}")
    public String list(
        @PathVariable(required = false) Optional<Integer> reqPage,
        @RequestParam String bcategory,
            Model model
    ){
//   페이징 기준에 맞춰 게시판 구성
        makingBoard(reqPage, bcategory, model);

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

//    게시판 메소드 추출
    private void makingBoard(Optional<Integer> reqPage, String bcategory, Model model) {
//        페이징 (10, 10)/(9,5) 분기
        PageCriteria pc = null;
        if(bcategory.equals("B0401") || bcategory.equals("B0501") || bcategory.equals("B0502")){
            pc = pc10;
        }else{
            pc = pc5;
        }

        //페이지 요청이 없으면 1페이지로.
        Integer page = reqPage.orElse(1);
        String cate = getCategory(bcategory);
//      1) 사용자 입력 - 요청 페이지
        pc.getRc().setReqPage(page);
//       2) 게시판 타입의 리스트 객체를 생성
        List<Bbs> list = null;

//       3) 해당 게시판의 게시물 총 개수를 구하고 페이징 적용 메소드로 게시판의 처음과 끝 페이지 출력
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
        model.addAttribute("bcategory", cate);
    }

    //쿼리스트링 카테고리 읽기, 없으면 ""반환. 어째... 좀... 내용이 쓰잘데기없다?
    private String getCategory(String bcategory) {
        String cate = bcategory;
//        log.info("category={}", cate);
        return cate;
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
