package com.kh146.domain.board.dao;

import com.kh146.domain.board.Bbs;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//이제야 된다 ㅠㅠㅠㅠ
@Slf4j
@SpringBootTest
class BbsDAOImplTest {

//  디펜던시 인젝션
  @Autowired
  private BbsDAO bbsDAO;

  @Test
  @DisplayName("게시글 등록")
  void insertBbs() {
//    새 게시글(빈 객체)을 만들고,
    Bbs bbs = new Bbs();
//    필요한 입력값을 채우고,
    bbs.setBcategory("B0401");
    bbs.setTitle("울산 신정동 베이킹 클래스");
    bbs.setAuthorId("tester1");
    bbs.setNickname("테스터1");
    bbs.setBcontent("몽블랑 클래스");
//    등록해.
    Long insertedId = bbsDAO.insertBbs(bbs);
    Assertions.assertThat(insertedId).isEqualTo(4);
    log.info("insertedId={}", insertedId);

  }

  @Test
  @DisplayName("게시글 조회")
  void findById(){
//    조회할 게시물 id
    Long bbsId = 32L;
    Bbs foundItem = bbsDAO.selectById(bbsId);
//    조회수 증가
    bbsDAO.increaseHit(bbsId);

    log.info("foundItem={}",foundItem);
    Assertions.assertThat(foundItem.getBbsId()).isEqualTo(bbsId);
    Assertions.assertThat(foundItem.getHit()).isEqualTo(12);

  }

  @Test
  @DisplayName("게시글 수정")
  void updateById(){
//    수정할 게시물 id
    Long bbsId = 1L;
//    덮어쓸 새 객체를 만들고 수정하고 싶은 내용 채우기.
    Bbs bbs = new Bbs();
//    다 안 넣어도 될 걸..? 아니네.
    bbs.setBcategory("B0401");
    bbs.setTitle("장현동 베이킹 클래스");
    bbs.setBcontent("당근케이크 만들었어요 맛있어보이죠?");
//    메소드 넣고 돌려
    int i = bbsDAO.updateById(bbsId, bbs);
    Assertions.assertThat(i).isEqualTo(1);
    log.info("updatedItem={}", bbs);
  }

  @Test
  @DisplayName("게시글 삭제")
  void deleteById(){
//    삭제할 id
    Long bbsId = 3L;
//    삭제빵
    int i = bbsDAO.deleteById(bbsId);
    Assertions.assertThat(i).isEqualTo(1);

  }

  @Test
  @DisplayName("조회수 증가")
  void increaseHit() {
    Long bbsId = 1L;
    bbsDAO.increaseHit(bbsId);

    Assertions.assertThat(bbsDAO.selectById(bbsId).getHit()).isEqualTo(3);
    log.info("hit={}", bbsDAO.selectById(bbsId).getHit());
  }

//  @Test
//  @DisplayName("특정 카테고리 게시판 목록")
//  void selectBoard(){
////    찾을 게시판 카테고리
//    String category = "B0401";  //베이킹클래스 게시판
//    List<Bbs> mapBoard = bbsDAO.selectBoard(bcategory);
//
//    log.info("mapBoard={}", mapBoard);
//
//  }

  @Test
  @DisplayName("게시판별 총 게시물 개수")
  void totalCount(){
    String category = "B0401";
    int i = bbsDAO.totalCount(category);

    Assertions.assertThat(i).isEqualTo(2);
    log.info("i={}", i);
  }

  @Test
  @DisplayName("다수 게시글 등록")
  void insertBbss() {
//    샘플 데이터 넣기용.
    for(int i=1; i<101; i++) {
//    새 게시글(빈 객체)을 만들고,
      Bbs bbs = new Bbs();
//    필요한 입력값을 채우고,
      bbs.setBcategory("B0103");
      bbs.setTitle("초콜릿 음료 먹고 싶다"+i);
      bbs.setAuthorId("tester1");
      bbs.setNickname("테스터1");
      bbs.setBcontent("인생은 쓰디쓴 초콜렛 참을 수 없는 초콜레엣"+i);

//    등록해.
      bbsDAO.insertBbs(bbs);
    }
//    Assertions.assertThat(insertedId).isEqualTo(4);
//    log.info("insertedId={}", insertedId);

  }

//  페이징 테스트
  @Test
  @DisplayName("특정 카테고리 게시판 목록")
  void selectBoardWithPaging(){
  //    찾을 게시판 카테고리
    String bcategory = "B0401";  //제과 게시판
    int startRec = 1;
    int endRec = 10;
    List<Bbs> bbs = bbsDAO.selectBoard(bcategory, startRec, endRec);

    log.info("bbs={}", bbs);

  }









}