--board crud
select * from board;
select * from code;

--게시글 등록
INSERT INTO board (
    bbs_id,
    bcategory,
    title,
    author_id,
    nickname,
    bcontent
) VALUES (
    BOARD_BBS_ID_SEQ.nextval,
    'B0401',
    '제목1',
    'tester1',
    '테스터1',
    '내용1'
);
commit;

INSERT INTO board (
    bbs_id,
    bcategory,
    title,
    author_id,
    nickname,
    bcontent
) VALUES (
    BOARD_BBS_ID_SEQ.nextval,
    'B0201',
    '발렌타인 레시피',
    'tester1',
    '테스터1',
    '파베 초콜릿'
);


--상세 조회
SELECT
    bbs_id,
    bcategory,
    title,
    author_id,
    nickname,
    bcontent,
    hit,
    cdate,
    udate
FROM
    board
where bbs_id = 2;     

--수정
UPDATE board
SET
    title = '울산 베이킹클래스',
    udate = default
WHERE bbs_id = 3;

commit;

--삭제
delete from board
where bbs_id = 1;
rollback;

--조회수 증가 시키기
UPDATE board
SET hit = hit+1 
WHERE bbs_id = 2;

commit;
--전체 조회, 모든 게시물.
SELECT *   
FROM board
order by bbs_id desc;
    
-- 전체조회, 특정 게시판의 게시물
SELECT
    bbs_id,
    bcategory,
    title,
    author_id,
    nickname,
    hit,
    cdate
FROM board
where bcategory = 'B0401'
order by bbs_id desc;

--게시판별 게시물 총 개수
select count(*) from board where bcategory = 'B0401';
commit;

--code crud
--부모 코드를 매개로 하위코드 찾기. 자기참조 셀프조인. 
SELECT t1.code_id code, t1.decode decode
FROM code t1, code t2
where t1.pcode_id = t2.code_id   --여기까지가 모든 하위코드
and t1.useyn = 'Y'
and t1.pcode_id = 'B05';

--첨부파일 빼고 게시판 코드만 반환하는 게 필요하다. code, decode만. 
SELECT t1.code_id code, t1.decode decode
FROM code t1, code t2
where t1.pcode_id = t2.code_id   --여기까지가 모든 하위코드
and t1.useyn = 'Y'
and t1.pcode_id LIKE 'B%';

--모든 코드 반환
select t1.pcode_id pcode, t2.decode pdecode, t1.code_id ccode, t1.decode cdecode
from code t1, code t2
where t1.pcode_id=t2.code_id
and t1.useyn = 'Y';
















