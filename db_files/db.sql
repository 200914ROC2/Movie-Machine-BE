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


select main.uid, main.username, main.firstname, main.lastname, movie_id from 
	(select
		u.user_id as uid, u.username as username, u.firstname as firstname, u.lastname  as lastname, f.movie_id as movie_id
		from users u
		left join favorite f
		on u.user_id = f.user_id 
	) as main where main.uid = 1
	
	
	select * from users u where u.user_id = 1; 



