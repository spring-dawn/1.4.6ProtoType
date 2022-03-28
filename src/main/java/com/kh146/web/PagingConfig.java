package com.kh146.web;

import com.kh146.domain.common.paging.PageCriteria;
import com.kh146.domain.common.paging.RecordCriteria;
import com.kh146.domain.common.paging.PageCriteria;
import com.kh146.domain.common.paging.RecordCriteria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagingConfig {
//    상수 필드
//  갤러리형  일반 게시판
    private static final  int REC_COUNT_10_PER_PAGE = 10;
    private static final  int PAGE_COUNT_10_PER_PAGE = 10;
//    베이킹 클래스, 커뮤니티 게시판
    private static final  int REC_COUNT_9_PER_PAGE = 9;
    private static final  int PAGE_COUNT_5_PER_PAGE = 5;

//    스프링 컨테이너에서 생성~소멸까지 관리하는 객체 = Bean 으로 등록.
//    디폴트로 메소드 = bean 이름이 된다. name 별도 부여해줄 수 있음.
    @Bean
    public RecordCriteria rc10(){
        return new RecordCriteria(REC_COUNT_10_PER_PAGE);
    }

    @Bean
    public PageCriteria pc10(){
        return new PageCriteria(rc10(), PAGE_COUNT_10_PER_PAGE);
    }

    @Bean
    public RecordCriteria rc5(){
        return new RecordCriteria(REC_COUNT_9_PER_PAGE);
    }

    @Bean
    public PageCriteria pc5(){
        return new PageCriteria(rc10(), PAGE_COUNT_5_PER_PAGE);
    }

}
