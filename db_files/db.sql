drop table if exists users;

CREATE TABLE users (
	user_id serial PRIMARY KEY,
	username VARCHAR ( 50 ) UNIQUE NOT NULL,
	password_hash VARCHAR ( 255 ) NOT NULL,
	firstname VARCHAR ( 255 ) NOT NULL,
	lastname VARCHAR ( 255 ) NOT NULL	
); 

CREATE TABLE favorite(
   user_id int references users,
   movie_id int,
);


insert into users values (DEFAULT,'user1','123456','Deanna','Warren');
insert into users values (DEFAULT,'user2','123456','Brooke','Barrett');  



