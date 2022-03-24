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
    String pcode = "B01";

    List<Code> subCode = codeDAO.code(pcode);
    Assertions.assertThat(subCode.size()).isEqualTo(4);
    log.info("subCode={}", subCode);
  }

  @Test
  @DisplayName("모든 코드 반환")
  void codeAll() {
    List<CodeAll> codeAll = codeDAO.codeAll();
    log.info("codeAll={}", codeAll);

  }

}