package com.kh146.domain.board.bakingClass.dao;

import com.kh146.domain.board.bakingClass.BakingClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BakingClassDAOImpl implements BakingClassDAO{

//  필수
  private JdbcTemplate jdbcTemplate;

  /**
   * 베이킹 클래스 찾기 게시판에 게시글 작성 > 등록
   * @param bakingClass 게시글
   * @return 게시글 id(bbs_id)
   */
  @Override
  public Long insertBbs(BakingClass bakingClass) {
//    우선 sql문을 준비
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO board (bbs_id, bcategory, title, author_id, nickname, bcontent) ");
    sql.append("    VALUES ( BOARD_BBS_ID_SEQ.nextval, ?, ?, ?, ?, ? ) ");

//    시퀀스 id를 읽을 keyholder 와 프리페어드스테이먼트를 매개값으로.
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"bbs_id"});
        pstmt.setString(1, bakingClass.getBcategory());
        pstmt.setString(2, bakingClass.getTitle());
        pstmt.setString(3, bakingClass.getAuthorId());
        pstmt.setString(4, bakingClass.getNickname());
        pstmt.setString(5, bakingClass.getBcontent());

        return pstmt;
      }
    }, keyHolder);

//    게시글 번호를 반환.
    return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
  }

  @Override
  public BakingClass findById(Long id) {
    return null;
  }

  @Override
  public BakingClass updateById(Long id, BakingClass bakingClass) {
    return null;
  }

  @Override
  public int deleteById(Long id) {
    return 0;
  }

  @Override
  public List<BakingClass> mapBoard() {
    return null;
  }

  @Override
  public List<BakingClass> mapBoard(String category) {
    return null;
  }

  @Override
  public int totalCount() {
    return 0;
  }
}
