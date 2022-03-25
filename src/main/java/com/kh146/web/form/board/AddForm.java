package com.kh146.web.form.board;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddForm {
    @NotBlank
    @Size(min=5,max = 11)
    private String bcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)

    @NotBlank
    @Size(min=5,max=50)
    private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)

    @NotBlank
    @Size(min=3,max=15)
    private String authorId;      //  작성자ID AUTHOR_ID VARCHAR2(50 BYTE)

    @NotBlank
    @Size(min=3,max=15)
    private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)

    @NotBlank
    @Size(min=5)
    private String bcontent;      //  내용 BCONTENT	CLOB



}
