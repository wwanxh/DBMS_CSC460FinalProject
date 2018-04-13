-- Add ships to the database
-- add ship
insert into hong1.model (mid, name, labor, markup)
	values(integer, 'string', integer, integer);
-- add basic parts
insert into hong1.model_part (mid, pid)
	values(integer, 10001);
insert into hong1.model_part (mid, pid)
	values(integer, 20001);
insert into hong1.model_part (mid, pid)
	values(integer, 80001);
-- add feature parts, add from 3 to 10
insert into hong1.model_part (mid, pid)
	values(integer, integer);

-- Add departments to the database
insert into hong1.department (did, mid, name)
	values(integer, integer, 'string');

-- Add parts to the database
insert into hong1.part(pid, name, price)
	values(integer, 'string', integer);