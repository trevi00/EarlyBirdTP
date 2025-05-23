delete from coupon_purchases;
delete from attendance;
delete from birds;
delete from diary;
delete from points;
delete from todos;

delete from coupons;
delete from users;

insert into users values('123', '123', '123');
insert into coupons values('00004', '허니브레드', 20);
insert into coupons values('00003', '프라푸치노', 15);
insert into coupons values('00002', '아메리카노', 10);
insert into coupons values('00001', '콜라', 5);

insert into points values('123', 5000);

delete from attendance where id = '123' and attend_date = '2025-05-21';

commit;

update POINTS set total_point=1000 where username=789;
