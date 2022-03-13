package com.kh146.domain.classSearch.dao;

import com.kh146.domain.classSearch.Bbs;
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
public class BbsDAOImpl implements BbsDAO{

//    DB와 Java를 연결하기 위한 디펜던시 임포트.
    private final JdbcTemplate jdbcTemplate;

    /**
     * 원글작성
     * @param bbs
     * @return 게시글 번호
     */
    @Override
    public Long saveOrigin(Bbs bbs) {
        //    sql 작성
        StringBuffer sql = new StringBuffer();
        sql.append( "insert into bbs (bbs_id, bcategory, title, email, nickname, bcontent, bimgs, bgroup )" );
        sql.append( "values( bbs_bbs_id_seq.nextval, ?, ?, ?, ?, ?, ?, bbs_bbs_id_seq.currval )" );

//    sql 실행
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        sql.toString(),
                        new String[]{"bbs_id"}    //insert 실행 후에 그 중 반환할 컬럼명. keyholder가 받아준다.
                );
//              각 값을 매칭
                pstmt.setString(1, bbs.getBcategory());
                pstmt.setString(2, bbs.getTitle());
                pstmt.setString(3, bbs.getEmail());
                pstmt.setString(4, bbs.getNickname());
                pstmt.setString(5, bbs.getBcontent());
                pstmt.setString(6, bbs.getBimgs());

                return pstmt;
            }
        }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
    }

    /**
     * 목록
     * @return 게시판
     */
    @Override
    public List<Bbs> findAll() {
//        1) 검증된 sql문을 만든다
        StringBuffer sql = new StringBuffer();
        sql.append( "select bbs_id, bcategory, title, email, nickname, hit, bcontent, bgroup," +
                " step, bindent, status, cdate, udate " );
        sql.append( "from bbs" );

//        2) sql문과 각 컬럼을 자동매핑 시켜(수동 필요한 경우도 있음) 결과를 리스트 컬렉션에 담아 반환한다
        List<Bbs> mapBoard = jdbcTemplate.query(
                sql.toString(),
                new BeanPropertyRowMapper<>(Bbs.class)   //단일 타입 아니고 여러 타입인 경우엔 클래스 단위로.
        );
        return mapBoard;
    }

    /**
     * 상세 조회
     * @param id 찾는 특정 게시글의 번호
     * @return
     */
    @Override
    public Bbs findByBbsId(Long id) {
        StringBuffer sql = new StringBuffer();
        sql.append( "select bbs_id, bcategory, title, email, nickname, hit, bcontent, bimgs, bgroup," +
                " step, bindent, status, cdate, udate " );
        sql.append( "from bbs" );
        sql.append( "where bbs_id = ?" );

        Bbs bbsItem = null;
        try {
            bbsItem = jdbcTemplate.queryForObject(
                    sql.toString(),
                    new BeanPropertyRowMapper<>(Bbs.class),
                    id);
        }catch (Exception e){ // 1건을 못찾으면
            bbsItem = null;
        }
        return bbsItem;
    }

    /**
     * 삭제
     * @param id 게시글번호
     * @return 삭제건수
     */
    @Override
    public int deleteByBbsId(Long id) {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM bbs ");
        sql.append(" WHERE bbs_id = ? ");

        int updateItemCount = jdbcTemplate.update(sql.toString(), id);

        return updateItemCount;
    }

    /**
     * 수정
     * @param id 게시글 번호
     * @param bbs 수정내용
     * @return 수정건수
     */
    @Override
    public int updateByBbsId(Long id, Bbs bbs) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE bbs ");
        sql.append("   SET bcategory = ?, ");
        sql.append("       title = ?, ");
        sql.append("       bcontent = ?, ");
        sql.append("       udate = systimestamp ");
        sql.append(" WHERE bbs_id = ? ");

        int updatedItemCount = jdbcTemplate.update(
                sql.toString(),
                bbs.getBcategory(),
                bbs.getTitle(),
                bbs.getBcontent(),
                id
        );

        return updatedItemCount;
    }



    @Override
    public Long saverReply(Bbs bbs) {
        return null;
    }

    @Override
    public int hitCount(Long id) {
        return 0;
    }

    @Override
    public int totalCount() {
        return 0;
    }
}
