package com.kh146.web.form.board;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ListForm {
//    게시판 목록에서 보여질 내용
    private Long bbsId;                     //게시글번호
    private String bcategory;               //분류
    private String title;                   //제목
//    private String authorId;                //이메일
    private String nickname;                //별칭
    private LocalDate cdate;            //생성일
    private int hit;                        //조회수

}
