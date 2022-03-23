package com.kh146.domain.board.bakingClass.dao;

import com.kh146.domain.board.bakingClass.BakingClass;

import java.util.List;

public interface BakingClassDAO {

//  게시물 등록, 첨부파일이 있는 경우와 없는 경우.
  Long insertBbs(BakingClass bakingClass);

//  상세 조회
  BakingClass findById(Long id);

//  수정
  BakingClass updateById(Long id, BakingClass bakingClass);

//  삭제
  int deleteById(Long id);

//  전체 조회. 각 게시판 분류별 오버로딩.
  List<BakingClass> mapBoard();
  List<BakingClass> mapBoard(String category);

//  답글은 없음.

  //전체 게시물 수. 페이징 기능에 필요
  int totalCount();

}

