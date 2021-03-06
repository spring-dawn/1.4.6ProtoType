package com.kh146.domain.common.code;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CodeDAOImplTest {

  @Autowired
  private CodeDAO codeDAO;

  @Test
  @DisplayName("하위 코드 반환")
  void code() {
    String pcode = "B03";

    List<Code> subCode = codeDAO.code(pcode);
    Assertions.assertThat(subCode.size()).isEqualTo(2);
    log.info("subCode={}", subCode);
  }

  @Test
  @DisplayName("일반 게시판 하위 코드 반환")
  void code_board() {
    List<Code> board = codeDAO.code();
    Assertions.assertThat(board.size()).isEqualTo(13);

    log.info("subCode={}", board);
  }

  @Test
  @DisplayName("일반 게시판 상위 코드 반환")
  void supercode_board() {
    String ccode = "B0302";
    List<Code> pcode = codeDAO.codeSuper(ccode);

    log.info("pcode={}", pcode);
  }

  @Test
  @DisplayName("모든 코드 반환")
  void codeAll() {
    List<CodeAll> codeAll = codeDAO.codeAll();
    log.info("codeAll={}", codeAll);

  }

  @Test
  @DisplayName("필요한 만큼 분류된 상위, 하위 반환")
  void SuperAndSub() {
    String pcode = "B02";
    List<CodeAll> all = codeDAO.codeAll(pcode);
    log.info("all={}", all);

  }



}