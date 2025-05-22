-- 최종 데이터베이스

-- 자식 테이블 삭제
DROP TABLE ATTENDANCE;
DROP TABLE TODOS;
DROP TABLE DIARY;
DROP TABLE POINTS;
DROP TABLE BIRDS;
DROP TABLE COUPON_PURCHASES;

-- 부모 테이블 삭제
DROP TABLE USERS;
DROP TABLE COUPONS;

-- 1. USERS (회원 정보)
CREATE TABLE USERS (
    username VARCHAR2(50) PRIMARY KEY,
    password VARCHAR2(100) NOT NULL,
    nickname VARCHAR2(50) NOT NULL
);

-- 2. ATTENDANCE (출석 기록)
CREATE TABLE ATTENDANCE (
    id VARCHAR2(100) PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    attend_date DATE NOT NULL,
    CONSTRAINT fk_att_user FOREIGN KEY (username) REFERENCES USERS(username)
);

-- 3. TODOS (할 일)
CREATE TABLE TODOS (
    id VARCHAR2(100) PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    todo_date DATE NOT NULL,
    title VARCHAR2(100) NOT NULL,
    content CLOB,
    done NUMBER(1) DEFAULT 0 CHECK (done IN (0, 1)),
    CONSTRAINT fk_todo_user FOREIGN KEY (username) REFERENCES USERS(username)
);

-- 4. DIARY (일기)
CREATE TABLE DIARY (
    id VARCHAR2(100) PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    diary_date DATE NOT NULL,
    title VARCHAR2(100),
    content CLOB,
    weather VARCHAR2(50),
    CONSTRAINT fk_diary_user FOREIGN KEY (username) REFERENCES USERS(username)
);

-- 5. POINTS (포인트 누적)
CREATE TABLE POINTS (
    username VARCHAR2(50) PRIMARY KEY,
    total_point NUMBER DEFAULT 0,
    CONSTRAINT fk_point_user FOREIGN KEY (username) REFERENCES USERS(username)
);

-- 6. BIRDS (새 상태)
CREATE TABLE BIRDS (
    username VARCHAR2(50) PRIMARY KEY,
    stage VARCHAR2(20), -- 예: "알", "병아리", "성조"
    CONSTRAINT fk_bird_user FOREIGN KEY (username) REFERENCES USERS(username)
);

-- 7. COUPONS (쿠폰 목록)
CREATE TABLE COUPONS (
    id VARCHAR2(50) PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    cost NUMBER NOT NULL
);

-- 8. COUPON_PURCHASES (사용자 쿠폰 보관함)
CREATE TABLE COUPON_PURCHASES (
    id VARCHAR2(100) PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    coupon_id VARCHAR2(50) NOT NULL,
    purchase_date TIMESTAMP DEFAULT SYSDATE,
    CONSTRAINT fk_cp_user FOREIGN KEY (username) REFERENCES USERS(username),
    CONSTRAINT fk_cp_coupon FOREIGN KEY (coupon_id) REFERENCES COUPONS(id)
);