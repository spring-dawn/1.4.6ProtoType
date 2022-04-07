--board crud
select * from member;
select * from board;
select * from category;

--게시글 등록
INSERT INTO board (
    board_num,
    cate_code,
    board_title,
    member_num,
    nickname,
    board_content
) VALUES (
    BOARD_board_num_SEQ.nextval,
    41,
    '제목2',
    1,
    '닉네임1',
    '내용2'
);
commit;

--상세 조회
SELECT
    board_num,
    cate_code,
    board_title,
    member_num,
    nickname,
    board_date,
    board_hit,
    board_content,
    board_map_address,
    board_picture
FROM
    board
where board_num = 3;

--수정
UPDATE board
SET
    cate_code = 31,
    board_title = '제목 수정',
    board_content = '본문 수정'
WHERE board_num = 3;

UPDATE board
SET
    board_title = '1.4.6'
WHERE board_num = 2;

--삭제
delete from board
where board_num = 1;
rollback;

--조회수 증가 시키기
UPDATE board
SET board_hit = board_hit+1 
WHERE board_num = 2;

commit;
--전체 조회, 모든 게시물.
SELECT *   
FROM board
order by board_num desc;




-- 게시판(전체 조회), 특정 게시판의 게시물
SELECT
    board_num,
    cate_code,
    board_title,
    member_num,
    nickname,
    board_date,
    board_hit
FROM
    board
where cate_code = '41'
order by board_num desc;

--게시판별 게시물 총 개수
select count(*) from board where cate_code = 41;
commit;

--category crud
--부모 코드를 매개로 하위코드 찾기. 자기참조 셀프조인. 
SELECT t1.cate_num cate, t1.cate_name cateName
FROM category t1, category t2
where t1.pcate_num = t2.cate_num   --여기까지가 모든 하위코드
and t1.useyn = 'Y'
and t1.pcate_num = 50;

--첨부파일 빼고 게시판 코드만 반환하는 게 필요하다. code, decode만. 
SELECT t1.cate_num code, t1.cate_name decode
FROM category t1, category t2
where t1.pcate_num = t2.cate_num   --여기까지가 모든 하위코드
and t1.useyn = 'Y'
and t1.pcate_num < 60;

select * from category;

--모든 코드 반환
select t1.pcate_num pcode, t2.cate_name pdecode, t1.cate_num ccode, t1.cate_name cdecode
from category t1, category t2
where t1.pcate_num=t2.cate_num
and t1.useyn = 'Y';

--상위코드 추출하기
select cate_num, cate_name
from category
where cate_num in (
                    select pcate_num
                    from category
                    where cate_num like '32');

--페이징 쿼리
select t1.* from(
    SELECT ROW_NUMBER() OVER (ORDER BY board_num desc) no,
        board_num,
        cate_code,
        board_title,
        member_num,
        nickname,
        board_hit,
        board_date
    FROM
        board
     where cate_code = 41 ) t1 
 where t1.no between 11 and 20; 













