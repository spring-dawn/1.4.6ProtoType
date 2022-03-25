package com.kh146.web.form.board;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class DetailForm {
    private Long bbsId;                     //게시글번호
    private String bcategory;               //분류
    private String title;                   //제목
    private String authorId;                   //이메일
    private String nickname;                //별칭
    private String bcontent;                //내용
    private int hit;                        //조회수
    private LocalDateTime cdate;            //생성일
    private LocalDateTime udate;            //수정일
}
