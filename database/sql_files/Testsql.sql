delete from coupon_purchases;
delete from attendance;
delete from birds;
delete from diary;
delete from points;
delete from todos;

delete from coupons;
delete from users;

insert into users values('123', '123', '123');
insert into coupons values('00001', '싸이버거', 20);
insert into coupons values('00002', '박카스', 5);
insert into points values('123', 5000);

delete from attendance where id = '123' and attend_date = '2025-05-21';

commit;