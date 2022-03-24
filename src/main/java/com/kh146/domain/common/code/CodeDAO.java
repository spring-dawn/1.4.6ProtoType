package com.kh146.domain.common.code;

import java.util.List;

public interface CodeDAO {

//  상위 코드(매개값) > 하위 코드 컬렉션(리턴값)으로 접근하는 메소드.
  List<Code> code(String pcode);

//  모든 코드 반환. 헤더 드롭다운 메뉴인가?
  List<CodeAll> codeAll();

}