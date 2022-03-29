package com.kh146.domain.board;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Bbs {
//    베이킹 클래스 게시판에서 사용할 필드 정의
  private Long bbsId;
  private String bcategory;
  private String title;
  private String authorId;
  private String nickname;
  private String bcontent;
  private int hit;
  private LocalDate cdate;
  private LocalDate udate;

//  BBS_ID	NUMBER
//  BCATEGORY	VARCHAR2(11 BYTE)
//  TITLE	VARCHAR2(150 BYTE)
//  AUTHOR_ID	VARCHAR2(40 BYTE)
//  NICKNAME	VARCHAR2(40 BYTE)
//  BCONTENT	CLOB
//  HIT	NUMBER
//  CDATE	TIMESTAMP(6)
//  UDATE	TIMESTAMP(6)



}
