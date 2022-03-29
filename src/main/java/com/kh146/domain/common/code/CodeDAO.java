package com.kh146.domain.common.code;

import java.util.List;

public interface CodeDAO {

//  상위 코드(매개값) > 하위 코드 컬렉션(리턴값)으로 접근하는 메소드.
//  like 연산자로 모든 게시판 코드를 출력하는 메소드 오버로딩
  List<Code> code(String pcode);
  List<Code> code();

//  모든 코드 정보 반환.
  List<CodeAll> codeAll();

}
