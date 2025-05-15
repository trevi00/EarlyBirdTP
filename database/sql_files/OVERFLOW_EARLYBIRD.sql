drop table attendance;
drop table points;
drop table birds;
drop table todos;
drop table diary;
drop table user_coupons;

drop table users;
drop table coupons;

create table users (
    id            varchar2(64) primary key,
    username      varchar2(50) unique not null,
    password      varchar2(100) not null,
    display_name  varchar2(100) not null
);

create table attendance (
    id          varchar2(64) primary key,
    user_id     varchar2(64) references users(id),
    attend_date date not null,
    constraint uq_attendance unique (user_id, attend_date)
);

create table points (
    id        varchar2(64) primary key,
    user_id   varchar2(64) references users(id),
    total     number default 0
);

create table birds (
    id         varchar2(64) primary key,
    user_id    varchar2(64) references users(id),
    stage      varchar2(20),
    point      number,
    born_date  date
);

create table todos (
    id         varchar2(64) primary key,
    user_id    varchar2(64) references users(id),
    todo_date  date,
    title      varchar2(200),
    content    clob,
    done       char(1) check (done in ('y', 'n')),
    constraint uq_todo unique (user_id, todo_date)
);

create table diary (
    id         varchar2(64) primary key,
    user_id    varchar2(64) references users(id),
    diary_date date,
    weather    varchar2(100),
    title      varchar2(200),
    content    clob,
    constraint uq_diary unique (user_id, diary_date)
);

create table coupons (
    id          varchar2(64) primary key,
    name        varchar2(100),
    description varchar2(200),
    cost        number
);

create table user_coupons (
    id         varchar2(64) primary key,
    user_id    varchar2(64) references users(id),
    coupon_id  varchar2(64) references coupons(id),
    acquired_at date
);
