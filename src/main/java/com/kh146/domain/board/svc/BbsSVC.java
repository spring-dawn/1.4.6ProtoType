package com.kh146.domain.board.svc;

import com.kh146.domain.board.Bbs;

import java.util.List;

//서비스 레이어에 어울리게 이름 변경
public interface BbsSVC {

//  등록
  Long saveBbs(Bbs bbs);

  //  상세 조회
  Bbs findById(Long id);

  //  수정
  int updateById(Long id, Bbs bbs);

  //  삭제
  int deleteById(Long id);

  //  전체 조회. 각 게시판 분류별 오버로딩.
  List<Bbs> findBoardByCategory(String bcategory);
  List<Bbs> findBoardByCategory(String bcategory, int startRec, int endRec);

//  답글은 없음.

  //게시판별 전체 게시물 수. 페이징 기능에 필요
  int totalCount(String bcategory);
}
