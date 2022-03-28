package com.kh146.domain.board.dao;

import com.kh146.domain.board.Bbs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class BbsDAOImpl implements BbsDAO {

//  필수. final 안 붙이면 활성화가 안 되네 이거.
  private final JdbcTemplate jdbcTemplate;

  /**
   * 게시글 작성 > 등록
   * @param bbs 게시글
   * @return 게시글 id(bbs_id)
   */
  @Override
  public Long insertBbs(Bbs bbs) {
//    우선 sql문을 준비
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO board (bbs_id, bcategory, title, author_id, nickname, bcontent ) ");
    sql.append("    VALUES ( BOARD_BBS_ID_SEQ.nextval, ?, ?, ?, ?, ? ) ");

//    시퀀스 id를 읽을 keyholder 와 프리페어드스테이먼트를 매개값으로.
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"bbs_id"});
        pstmt.setString(1, bbs.getBcategory());
        pstmt.setString(2, bbs.getTitle());
        pstmt.setString(3, bbs.getAuthorId());
        pstmt.setString(4, bbs.getNickname());
        pstmt.setString(5, bbs.getBcontent());

        return pstmt;
      }
    }, keyHolder);

//    게시글 번호를 반환.
    return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
  }

  /**
   * 상세 조회
   * @param id 조회할 게시글 id
   * @return 게시글 전체 내용(*)
   */
  @Override
  public Bbs selectById(Long id) {
//    우선 sql.
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ");
    sql.append("    bbs_id, ");
    sql.append("    bcategory, ");
    sql.append("    title, ");
    sql.append("    author_id, ");
    sql.append("    nickname, ");
    sql.append("    bcontent, ");
    sql.append("    hit, ");
    sql.append("    cdate, ");
    sql.append("    udate ");
    sql.append(" FROM  board ");
    sql.append(" where bbs_id = ? ");

//    단일 리턴이니 쿼리포오브젝트(sql, 매퍼, 매개값). 이거 매핑하다 오류날 수 있댔던가.. 암튼.
    Bbs foundItem = null;
    try {
      foundItem = jdbcTemplate.queryForObject(sql.toString(),
          new BeanPropertyRowMapper<>(Bbs.class),
          id);
    }catch (Exception e){
      foundItem = null;
    }
//    찾았으면 반환
    return foundItem;
  }

  /**
   * 게시물 수정
   * @param id 수정할 게시물 id
   * @param bbs 수정할 게시물의 변경 가능한 내용
   * @return 성공 시 1
   */
  @Override
  public int updateById(Long id, Bbs bbs) {
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE board ");
    sql.append(" SET ");
    sql.append("    bcategory = ?, ");
    sql.append("    title = ?, ");
    sql.append("    bcontent = ?, ");
    sql.append("    udate = default ");
    sql.append(" WHERE bbs_id = ? ");

    int updateCnt = jdbcTemplate.update(sql.toString(),
        bbs.getBcategory(),
        bbs.getTitle(),
        bbs.getBcontent(),
        id
    );
//    성공했으면 1 반환
    return updateCnt;
  }

  /**
   * 게시물 삭제
   * @param id 삭제할 게시물 id
   * @return 성공 시 1
   */
  @Override
  public int deleteById(Long id) {
    String sql = " delete from board where bbs_id = ? ";
    int deleteCnt = jdbcTemplate.update(sql, id);

    return deleteCnt;
  }

  /**
   * 조회수
   * @param id 대상 게시물 id
   * @return 조회수 +1
   */
  @Override
  public int increaseHit(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE board ");
    sql.append(" SET hit = hit+1 ");
    sql.append(" WHERE bbs_id = ? ");

    int updateHit = jdbcTemplate.update(sql.toString(), id);
    return updateHit;

  }

  /**
   * 게시판 목록
   * @param category 조회할 게시판 분류코드
   * @return 코드에 맞는 특정 게시판
   */
//  @Override
//  public List<Bbs> selectBoard(String category) {
////    사용자가 선택한 분류의 게시판을 출력.
//    StringBuffer sql = new StringBuffer();
//    sql.append(" SELECT ");
//    sql.append("     bbs_id, ");
//    sql.append("     bcategory, ");
//    sql.append("     title, ");
//    sql.append("     author_id, ");
//    sql.append("     nickname, ");
//    sql.append("     hit, ");
//    sql.append("     cdate ");
//    sql.append(" FROM board ");
//    sql.append(" where bcategory = ? ");
//    sql.append(" order by bbs_id desc ");
//
////    리스트 출력, 다수 리턴이니 쿼리
//    List<Bbs> foundBoard = jdbcTemplate.query(sql.toString(),
//        new BeanPropertyRowMapper<>(Bbs.class),
//        category);
//
//    return foundBoard;
//  }

  /**
   * 카테고리별 페이징 적용된 게시판 출력
   * @param bcategory 게시판 구분
   * @param startRec 한 페이지의 시작 레코드(게시물)
   * @param endRec 한 페이지의 마지막 레코드
   * @return 게시판에 원하는 단위의 페이징 적용
   */
  @Override
  public List<Bbs> selectBoard(String bcategory, int startRec, int endRec) {

      StringBuffer sql = new StringBuffer();
      sql.append(" select t1.* from( ");
      sql.append("         SELECT ROW_NUMBER() OVER (ORDER BY bbs_id desc) no, ");
      sql.append("         bbs_id, ");
      sql.append("         bcategory, ");
      sql.append("         title, ");
      sql.append("         author_id, ");
      sql.append("         nickname, ");
      sql.append("         hit, ");
      sql.append("         cdate ");
      sql.append("         FROM ");
      sql.append("         board ");
      sql.append("         where bcategory = ? ) t1 ");
      sql.append(" where t1.no between ? and ? ");

    List<Bbs> list = jdbcTemplate.query(
              sql.toString(),
              new BeanPropertyRowMapper<>(Bbs.class),
              bcategory, startRec, endRec
      );
      return list;
  }

  /**
   * 페이징 용도
   * @return 각 게시판의 총 게시물 개수
   */
  @Override
  public int totalCount(String category) {
    String sql = " select count(*) from board where bcategory = ? ";
//    단일 리턴: 쿼리포오브젝트 > 이거 쓰니까 단일 매핑이 아니라고 하는데... 뭐지. 결과숫자를 못 받고 있다.
    Integer itemCnt = 0;
    try {
      itemCnt = jdbcTemplate.queryForObject(sql, Integer.class, category);
    }catch (Exception e){
      itemCnt = 0;
    }
//    찾았으면 반환
    return itemCnt;
  }

}
