-- 자식 테이블 삭제
drop table attendance;
drop table todos;
drop table user_coupons;

-- 부모 테이블 삭제
drop table users; 
drop table coupons;

create table users (
    id            varchar2(64) primary key, -- 유저 아이디
    username      varchar2(50) unique not null, -- 유저 닉네임
    password      varchar2(100) not null, -- 유저 비밀번호 
    point         number not null
);

create table attendance (
    user_id     varchar2(64), -- 출석한 유저 아이디
    attend_date date -- 출석일자
);

alter table attendance add primary key(user_id, attend_date); 

create table todos (
    id         varchar2(64) primary key, -- 투두 아이디
    user_id    varchar2(64) references users(id), -- 투두 리스트를 작성한 유저 아이디
    todo_date  date, -- 작성된 일자
    content    varchar2(200), -- 해야될 일
    done       char(1) check (done in ('y', 'n')) -- 수행 여부 체크
);

create table coupons (
    id          varchar2(64) primary key, -- 쿠폰 일련번호(아이디)
    name        varchar2(100), -- 쿠폰 이름
    description varchar2(200), -- 쿠폰 설명
    cost        number -- 쿠폰 가격
);

create table user_coupons (
    user_id     varchar2(64) references users(id), -- 쿠폰을 소지한 유저 아이디
    coupon_id   varchar2(64) references coupons(id), -- 쿠폰 일련번호
    acquired_at date -- 쿠폰 획득일자
);

alter table user_coupons add primary key(user_id, coupon_id);

