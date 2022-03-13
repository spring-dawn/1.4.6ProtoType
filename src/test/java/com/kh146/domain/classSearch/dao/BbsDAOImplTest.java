package com.kh146.domain.classSearch.dao;

import com.kh146.domain.classSearch.Bbs;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BbsDAOImplTest {

    @Autowired
    private BbsDAO bbsDAO;

    @Test
    @DisplayName("원글 등록")
    void saveOrigin() {
//      1) 게시물 객체를 새로 만든다. 게시물 1개 = 객체 1개겠지?
        Bbs bbs = new Bbs();

//      2) 참조변수와 setter로 객체에 필요한 내용을 할당한다
        bbs.setBcategory("B0101");
        bbs.setTitle("제목1");
        bbs.setEmail("test1@kh.com");
        bbs.setNickname("테스터1");
        bbs.setBcontent("본문1");

//      3) 테스트용 변수를 선언하고 만들어진 게시물을 대입하여 검증해본다
        Long savedId = bbsDAO.saveOrigin(bbs);
        Assertions.assertThat(savedId).isEqualTo(9);
        log.info("savedId={}", savedId);

    }

    @Test
    @DisplayName("게시판")
    void findAll() {
//        1) 매개값은 따로 없고, 인터페이스에서 목록 조회 메소드를 가져와 변수에 담는다
        List<Bbs> mapBoard = bbsDAO.findAll();

//        2) 맞게 나오는지 검증해본다. 사실 위에 저게 다다.
        Assertions.assertThat(mapBoard.size()).isEqualTo(1);
        Assertions.assertThat(mapBoard.get(0).getTitle()).isEqualTo("제목1");   //인덱스로 식별.
        for (Bbs bbs : mapBoard) {
            log.info(bbs.toString());
        }
    }

    @Test
    @DisplayName("게시물 조회")
    void findByBbsId() {
//        매개 타입이 Long이니 그에 맞춰 변수 선언하고 매개로 준다.
        Long bbsId = 9L;
        Bbs findedBbsItem = bbsDAO.findByBbsId(bbsId);
        Assertions.assertThat(findedBbsItem.getTitle()).isEqualTo("제목1");
//        자꾸 NullPointerException이 나는데 로직이 잘못됐다기보다 이 컬럼값 자체에 null이 있어서 그런 듯?
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteByBbsId() {
        Long bbsId = 8L;
        int deletedBbsItemCount = bbsDAO.deleteByBbsId(bbsId);

        Assertions.assertThat(deletedBbsItemCount).isEqualTo(1);

        Bbs findedBbsItem = bbsDAO.findByBbsId(bbsId);
        Assertions.assertThat(findedBbsItem).isNull();
    }

    @Test
    @DisplayName("게시물 수정")
    void updateByBbsId() {
//        수정하려는 게시물의 번호 선택(매개값)
        Long bbsId = 9L;

//        개별조회 메소드(null 때문에 오류나서 여기서 막힘)로 수정할 게시글을 찾고, setter 로 원하는 부분 수정.
        Bbs beforeUpdatingItem = bbsDAO.findByBbsId(bbsId);
        beforeUpdatingItem.setBcategory("B0102");
        beforeUpdatingItem.setTitle("수정 후 제목");
        beforeUpdatingItem.setBcontent("수정 후 본문");
//        메소드를 불러와 게시물 번호, 수정된 게시물 내용을 매개값으로 준다
        bbsDAO.updateByBbsId(bbsId, beforeUpdatingItem);

        //수정 후 결과
        Bbs afterUpdatingItem = bbsDAO.findByBbsId(bbsId);

        //수정전후 비교
        Assertions.assertThat(beforeUpdatingItem.getBcategory())
                .isEqualTo(afterUpdatingItem.getBcategory());
        Assertions.assertThat(beforeUpdatingItem.getTitle())
                .isEqualTo(afterUpdatingItem.getTitle());
        Assertions.assertThat(beforeUpdatingItem.getBcontent())
                .isEqualTo(afterUpdatingItem.getBcontent());
        Assertions.assertThat(beforeUpdatingItem.getUdate())
                .isNotEqualTo(afterUpdatingItem.getUdate());
    }
}