package com.kh146.domain.common.code;

import lombok.Data;

@Data
public class CodeAll {
  private String pcode;         //상위 코드 ex)B01
  private String pdecode;       //상위 디코드(이름)
  private String ccode;         //하위 코드 ex)B0101, B0102, B0103...
  private String cdecode;       //하위 디코드
}
