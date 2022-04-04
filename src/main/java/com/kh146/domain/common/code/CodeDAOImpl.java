package com.kh146.domain.common.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CodeDAOImpl implements CodeDAO{
// db 내역이니까 sql 이지
  private final JdbcTemplate jdbcTemplate;

  /**
   * 상위코드를 입력하면 하위코드를 반환
   * @param pcode 상위코드
   * @return 그 아래 하위코드와 디코드(코드 설명)
   */
  @Override
  public List<Code> code(String pcode) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select t1.code_id code, t1.decode decode  ");
    sql.append(" from code t1, code t2 ");
    sql.append(" where t1.pcode_id = t2.code_id  ");
    sql.append(" and t1.useyn = 'Y'  ");
    sql.append(" and t1.pcode_id = ? ");

    List<Code> codes = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Code.class),
        pcode
    );
    return codes;
  }

  /**
   * 상위 코드 > 하위 코드는 똑같은데 모든 일반 게시판 코드를 한 번에 반환.
   * @return B0101~B0502까지의 모든 게시판 코드.
   */
  @Override
  public List<Code> code() {
    StringBuffer sql = new StringBuffer();
    sql.append(" select t1.code_id code, t1.decode decode  ");
    sql.append(" from code t1, code t2 ");
    sql.append(" where t1.pcode_id = t2.code_id  ");
    sql.append(" and t1.useyn = 'Y'  ");
    sql.append(" and t1.pcode_id LIKE 'B%' ");

    List<Code> codes = jdbcTemplate.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(Code.class)
    );
    return codes;
  }

  /**
   * 모든 코드 반환
   * @return
   */
  @Override
  public List<CodeAll> codeAll() {
    StringBuffer sql = new StringBuffer();
    sql.append(" select t1.pcode_id pcode, t2.decode pdecode, t1.code_id ccode, t1.decode cdecode  ");
    sql.append("  from code t1, code t2 ");
    sql.append(" where t1.pcode_id = t2.code_id  ");
    sql.append("   and t1.useyn = 'Y' ");

    List<CodeAll> codeAll = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(CodeAll.class)
    );
    return codeAll;
  }

  /**
   * 렌더링 할 상위 카테고리명 추출
   * @return 상위코드명
   */
  @Override
  public List<Code> codeSuper(String ccode) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select code_id code, decode decode");
    sql.append(" from code ");
    sql.append(" where code_id in ( ");
    sql.append("                    select pcode_id ");
    sql.append("                    from code ");
    sql.append("                    where code_id like ?) ");

    List<Code> superCode = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Code.class),
        ccode
    );

    return superCode;
  }

  /**
   * 상위코드를 필요한 만큼 분류, 그 아래 하위코드 반환
   * @param pcode 접속하려는 페이지의 상위 분류
   * @return 상위 분류, 하위 분류의 이름.
   */
  @Override
  public List<CodeAll> codeAll(String pcode) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select t1.pcode_id pcode, t2.decode pdecode, t1.code_id ccode, t1.decode cdecode ");
    sql.append(" from code t1, code t2 ");
    sql.append(" where t1.pcode_id=t2.code_id ");
    sql.append(" and t1.useyn = 'Y' ");
    sql.append(" and t1.pcode_id Like ?||'%' ");
//    마지막 분류조건이 잘 동작할지 모르겠네;
    List<CodeAll> leftBanner = jdbcTemplate.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(CodeAll.class),
            pcode
    );

    return leftBanner;
  }
}
