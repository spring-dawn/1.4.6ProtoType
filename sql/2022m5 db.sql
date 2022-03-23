--member, code, board, uploadfile 순으로 마지막에 commit. 반대 순서로 drop.

drop table uploadfile;
drop table board;
drop table code;
drop table member;

--code테이블은 시퀀스가 없다.
drop sequence uploadfile_uploadfile_id_seq;
drop SEQUENCE board_bbs_id_seq;
drop SEQUENCE member_member_num_seq;

--회원 테이블
create table member(
    member_num      number,
    name            varchar2(30),
    member_id       varchar2(40),
    pw              varchar2(30),
    nickname        varchar2(40),
    email           varchar2(50),
    tel             varchar2(20),
    birth           date,
    gender          char(3),
    show_list       char(10) default '공개',     
    cdate           TIMESTAMP default SYSTIMESTAMP
);

--기본키: 시퀀스
alter table member add CONSTRAINT member_member_num_pk PRIMARY key(member_num);

--제약조건. member_id, email은 uk, nn 모두 적용. nickname은 uk. 
-- unique, check는 add(pk, fk와 같은 문법), nn은 modify. 
alter table member modify name CONSTRAINT member_name_nn not null;
alter table member modify pw CONSTRAINT member_pw_nn not null;

alter table member add CONSTRAINT member_member_id_uk unique(member_id);
alter table member modify member_id CONSTRAINT member_member_id_nn not null;

alter table member add CONSTRAINT member_nickname_uk unique(nickname);

alter table member add CONSTRAINT member_email_uk unique(email);  
alter table member modify email CONSTRAINT member_email_nn not null;

alter table member modify tel CONSTRAINT member_tel_nn not null;
alter table member add CONSTRAINT member_tel_uk unique(tel);  

alter table member modify birth CONSTRAINT member_birth_nn not null;

alter table member modify gender CONSTRAINT member_gender_nn not null;
alter table member add CONSTRAINT member_gender_ck check(gender in ('여','남'));
alter table member add CONSTRAINT member_show_list_ck check(show_list in ('공개','비공개'));

--시퀀스
create SEQUENCE member_member_num_seq nocache;

--샘플데이터. 제약조건 확인해보니 다 check로 돼있는데 왜 이러지; 문제 생기면 확인해볼 것.
INSERT INTO member (
    member_num,
    name,
    member_id,
    pw,
    nickname,
    email,
    tel,
    birth,
    gender,
    show_list,
    cdate
) VALUES (
    member_member_num_seq.nextval,
    '김뫄뫄',
    'tester1',
    '1234',
    null,
    'test1@kh.com',
    '010-1111-1111',
    '1994-01-20',
    '여',
    '비공개',
    default
);
INSERT INTO member (
    member_num,
    name,
    member_id,
    pw,
    nickname,
    email,
    tel,
    birth,
    gender,
    show_list,
    cdate
) VALUES (
    member_member_num_seq.nextval,
    '박뫄뫄',
    'tester2',
    '1234',
    '테스터2',
    'test2@kh.com',
    '010-2222-2222',
    '2001-08-11',
    '남',
    default,
    default
);

--분류 코드. 필요한 분류가 생기면 때마다 추가해서 사용. 
create table code(
    code_id     varchar2(11),       --코드
    decode      varchar2(30),       --코드명
    descript    clob,               --코드설명
    pcode_id    varchar2(11),       --상위코드
    useyn       char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);

--기본 키
alter table code add CONSTRAINT code_code_id_pk PRIMARY key(code_id);

--외래 키. 상위 분류의 code_id가 하위 분류를 포괄할 부모키가 된다. 자기참조. 사용하려면 셀프조인 필요. 
alter table code add constraint code_pcode_id_fk foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));   --해당 분류의 현재 사용 여부.
commit;

--샘플. 게시판=B, 첨부파일=F
insert into code (code_id,decode,pcode_id,useyn) values ('B01','분야별 레시피',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0101','제과','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0102','제빵','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0103','음료','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0104','노오븐 베이킹','B01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('B02','테마별 레시피',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0201','발렌타인 데이','B02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0202','할로윈','B02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0203','빼빼로 데이','B02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0204','크리스마스','B02','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('B03','사진 팁',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0301','구도/각도','B03','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0302','보정','B03','Y');

--베이킹 클래스 찾기 게시판의 경우 숫자가 너무 적어 변경 여지 있음
insert into code (code_id,decode,pcode_id,useyn) values ('B04','베이킹 클래스 찾기',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0401','베이킹 클래스 찾기','B04','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('B05','커뮤니티',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0501','QnA','B05','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0502','자유 게시판','B05','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('F01','첨부',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0101','파일','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0102','이미지','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0103','프로필','F01','Y');

------------------------
--게시판 Board. 답글 사용 x
create table board(
    bbs_id          number,         --게시물 고유번호
    bcategory       varchar2(11),   --code_id fk
    title           varchar2(150),
    author_id       varchar2(40),   --member_id fk
    nickname        varchar2(40),   
    bcontent        clob,
    hit             number default 0,
    cdate           TIMESTAMP default SYSTIMESTAMP,
    udate           TIMESTAMP default SYSTIMESTAMP
);

--기본키
alter table board add Constraint board_bbs_id_pk primary key (bbs_id);

--외래키. bcategory, author.    
alter table board add constraint board_bcategory_fk
    foreign key(bcategory) references code(code_id);
alter table board add constraint board_author_id_fk
    foreign key(author_id) references member(member_id);

--제약조건, nickname nn.
alter table board modify bcategory constraint board_bcategory_nn not null;
alter table board modify title constraint board_title_nn not null;
alter table board modify author_id constraint board_author_id_nn not null;
alter table board modify nickname constraint board_nickname_nn not null;
alter table board modify bcontent constraint board_bcontent_nn not null;

--시퀀스
create SEQUENCE board_bbs_id_seq nocache;
select * from board;

--샘플


---------
--첨부파일
---------
create table uploadfile(
    uploadfile_id   number(10),                 --파일아이디
    code            varchar2(11),               --분류코드
    rid             number,                     --참조번호(게시글번호등) = reference id. 뭘 참조하게 될지 알 수 없다는 건가.
    store_filename  varchar2(50),               --서버보관 파일명
    upload_filename varchar2(50),               --업로드 파일명(유저가 업로드한파일명)
    fsize           varchar2(45),               --업로드 파일크기(단위byte)
    ftype           varchar2(50),               --파일 유형(mimetype)
    cdate           timestamp default systimestamp,      --등록일시
    udate           timestamp default systimestamp       --수정일시
);

--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);
--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk foreign key(code) references code(code_id);

--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;

--시퀀스
create sequence uploadfile_uploadfile_id_seq nocache;

commit;

select * from board;
select * from member;
select * from code;
select * from uploadfile;

