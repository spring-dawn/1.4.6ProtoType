package com.kh146.web.form.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EditForm {
    @NotBlank
    @Size(min=5,max = 11)
    private String bcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)

    @NotBlank
    @Size(min=5,max=50)
    private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)

    @NotBlank
    @Size(min=5)
    private String bcontent;      //  내용 BCONTENT	CLOB

//    authorId, nickname, udate 등 사용자가 건드리면 안 되거나 입력하지 않아도 되는 부분은 input type="hidden" 처리

}
