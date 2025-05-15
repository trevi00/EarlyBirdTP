alter session set "_ORACLE_SCRIPT" = true; -- 12c 버젼 이후의 Oracle 환경에서 사용자명 설정을 자유롭게 함.

create user overflow_earlybird -- overflow_earlybird 사용자 생성.
identified by 12345 -- 비밀번호는 12345로 설정.
default tablespace users -- 기본 테이블스페이스는 users
temporary tablespace temp; -- 임시 테이블스페이스는 temp

grant create session to overflow_earlybird; -- overflow_earlybird 사용자에게 로그인 권한 부여.

grant create table, create view, create procedure, create sequence to overflow_earlybird; -- overflow_earlybird 사용자에게 테이블, 뷰, 프로시져, 시퀀스 생성 권한 부여. CRUD 권한도 함께 부여된다.

alter user overflow_earlybird quota unlimited on users; -- overflow_earlybird 사용자는 user 테이블스페이스를 무제한으로 사용 가능.