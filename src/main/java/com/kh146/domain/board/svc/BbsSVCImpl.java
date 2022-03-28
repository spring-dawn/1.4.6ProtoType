package com.kh146.domain.board.svc;

import com.kh146.domain.board.Bbs;
import com.kh146.domain.board.dao.BbsDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BbsSVCImpl implements BbsSVC{

//  final 로 상수화 = 불변
  private final BbsDAO bbsDAO;

//  추후 첨부파일 오버로딩 필요

  /**
   * 게시글 작성 > 등록
   * @param bbs
   * @return
   */
  @Override
  public Long saveBbs(Bbs bbs) {
    return bbsDAO.insertBbs(bbs);
  }

  /**
   * 상세 조회, 조회수 +1
   * @param id
   * @return
   */
  @Override
  public Bbs findById(Long id) {
    Bbs foundItem = bbsDAO.selectById(id);
    bbsDAO.increaseHit(id);
    return foundItem;
  }

  /**
   * 수정
   * @param id
   * @param bbs
   * @return
   */
  @Override
  public int updateById(Long id, Bbs bbs) {
    return bbsDAO.updateById(id, bbs);
  }

  /**
   * 삭제
   * @param id
   * @return
   */
  @Override
  public int deleteById(Long id) {
    return bbsDAO.deleteById(id);
  }

  /**
   * 카테고리별 게시판 
   * @param bcategory
   * @return
   */
//  @Override
//  public List<Bbs> findBoardByCategory(String bcategory) {
//    return bbsDAO.selectBoard(bcategory);
//  }

  /**
   * 페이징 적용된 카테고리별 게시판
   * @param bcategory 분류 코드
   * @param startRec 한 페이지 시작 레코드
   * @param endRec 한 페이지 마지막 레코드
   * @return
   */
  @Override
  public List<Bbs> findBoardByCategory(String bcategory, int startRec, int endRec) {
    return bbsDAO.selectBoard(bcategory, startRec, endRec);
  }

  /**
   * 카테고리별 게시물 총 개수
   * @param bcategory
   * @return
   */
  @Override
  public int totalCount(String bcategory) {
    return bbsDAO.totalCount(bcategory);
  }


}
