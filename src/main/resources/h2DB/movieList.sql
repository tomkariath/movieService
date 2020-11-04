delete from movie where name='Good Will Hunting';
set mode MYSQL;
insert ignore into movie values (1,3,1,'A Clockwork Orange',3);
insert ignore into movie values (2,4,1,'Rangi Taranga',1);
insert ignore into movie values (3,5,9,'Masaan',4);
insert ignore into movie values (4,2,6,'Afro Samurai',1);
insert ignore into movie values (5,5,3,'City of God',5);
insert ignore into movie values (6,5,8,'True Grit',9);

insert ignore into role values (1,'ROLE_ADMIN');
insert ignore into role values (2,'ROLE_USER');

insert ignore into user values (1,'$2a$10$bgk7S3QFoXUj9a0arvQ8WOoi0GYDw4P7d6UxZwurPD9V32FV7rWzu','Johny');
insert ignore into user values (2,'$2a$10$JoKoL1wmCSZ7Oe2PNU/J8OWkZkpGrzFU/DLcahRYC0m.veSOem1Qu','Thomas');

insert ignore into user_roles values (1,2);
insert ignore into user_roles values (2,1);

delete from rating where rating_id > 0;