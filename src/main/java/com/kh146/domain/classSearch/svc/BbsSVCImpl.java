package com.kh146.domain.classSearch.svc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service       //서비스 레이어
@RequiredArgsConstructor
@Transactional  //데이터 추가, 삭제, 수정 등의 작업에 오류가 발생하면 작업을 전부 원상태로 되돌려 DB에 적용되지 않게 한다.
public class BbsSVCImpl implements BbsSVC{

}
