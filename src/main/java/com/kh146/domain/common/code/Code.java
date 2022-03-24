package com.kh146.domain.common.code;

import lombok.Data;

@Data
public class Code {
  private String code;        //코드 번호. ex) B01 > B0101
  private String decode;      //카테고리 이름. ex) 분야별 레시피 > 제과

}
