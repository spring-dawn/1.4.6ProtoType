package com.kh146.domain.classSearch.dao;

import com.kh146.domain.classSearch.Bbs;

import java.util.List;

public interface BbsDAO {

    //    원글 작성
    Long saveOrigin(Bbs bbs);

//    게시물 리스트 출력 = 게시판
    List<Bbs> findAll();

//    게시물 개별 조회 = 게시물 번호로 식별해서 본문 상세페이지 이동. @PathVariable 필요.
    Bbs findByBbsId(Long id);

//    게시글 삭제.
    int deleteByBbsId(Long id);

//    게시글 수정
    int updateByBbsId(Long id, Bbs bbs);

    // 답글작성
    Long saverReply(Bbs bbs);

    // 조회수
    int hitCount(Long id);

    // 전체건수
    int totalCount();

}
