select * from Users;
select * from points;

-- 임시로 point 기본키 + user_id 입력 , 기본 total  = 0으로 시작. 
insert into points values ('1', 'topblame', 0);

select user_id, total from points where user_id = 'topblame';
-- total에있는 포인트를 조회하여 값을 끌고옴.

update points set total = 10 where user_id = 'topblame';
--  끌고온 포인트값에 포인트를 추가하여 데이터베이스에 update. 
desc points;
-- 포인트

select * from coupons;

INSERT INTO COUPONS (id, name, cost) VALUES('921655926429', '싸이버거', 20); 
INSERT INTO COUPONS (id, name, cost) VALUES('9316780657962253', '박카스', 5); 
-- 싸이버거, 박카스 쿠폰 업데이트.  포인트 상점에 생김. 

-- 유저가 구매시 구매데이터 저장.
select * from coupon_purchases;