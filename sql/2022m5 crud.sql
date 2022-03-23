--board crud
select * from board;

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
    title = '울산 베이킹클래스'
WHERE bbs_id = 3;

commit;

--삭제
delete from board
where bbs_id = 1;
rollback;

--전체 조회, 모든 게시물.
SELECT
    bbs_id,
    bcategory,
    title,
    author_id,
    nickname,
    hit,
    cdate
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
    


















