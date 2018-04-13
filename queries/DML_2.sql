-- Order a ship as if the user was a customer

-- create a new contract
insert into hong1.contract (contid, cusid, mid, did, time_stamp)
	values(integer(random generate or auto increment),
		integer(associated with current cusid),
		integer(chosen by customer),
		integer(chosen based on department.mid),
		'string'(follow format yyyymmddhhmm, 12 digits)
		);

-- create cont_part tuple for basic part 10001
insert into hong1.cont_part (contid, pid, status)
	values(integer(associated with new contract id),
		10001(basic part id),
		0 (all parts start with status 'To Be Added')
		);

-- create cont_part tuple for basic part 20001
insert into hong1.cont_part (contid, pid, status)
	values(integer(associated with new contract id),
	   	20001(basic part id),
	   	0 (all parts start with status 'To Be Added')
	   	);

-- create cont_part tuple for basic part 80001
insert into hong1.cont_part (contid, pid, status)
	values(integer(associated with new contract id),
	   	80001(basic part id),
	   	0 (all parts start with status 'To Be Added')
	   	);

-- create cont_part tuple for feature part, needs multiple
insert into hong1.cont_part (contid, pid, status)
	values(integer(associated with new contract id),
		integer(feature part id),
		0 (all parts start with status 'To Be Added')
		);