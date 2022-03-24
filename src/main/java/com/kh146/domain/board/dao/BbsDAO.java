package com.kh146.domain.board.dao;

import com.kh146.domain.board.Bbs;

import java.util.List;

//DB와 같은 키워드 사용
public interface BbsDAO {

//  게시물 등록. 어... 그냥 Bbs 하나만 있으면 분류를 다르게 해서 모든 게시판에 똑같이 적용되는 거 아닌가? 굳이 이걸 만들고 있는 이유가?
//  그러네 미친. 내가 게시판 담당이니까 그냥 내가 이거 통일하면 되는 거잖아.
  Long insertBbs(Bbs bbs);

//  상세 조회
  Bbs selectById(Long id);

//  수정
  int updateById(Long id, Bbs bbs);

//  삭제
  int deleteById(Long id);

//  아맞다 조회수
  int increaseHit(Long id);

//  전체 조회. 각 게시판 분류별 오버로딩.
//  List<BakingClass> mapBoard();
  List<Bbs> selectBoard(String category);

//  답글은 없음.

  //게시판별 전체 게시물 수. 페이징 기능에 필요
  int totalCount(String category);

}

