-- Add to a ship’s progress by updating which parts of the ship have been built
update hong1.cont_part
	set status = integer(1:In Progress, 2:Completed)
	where contid = integer
	and pid = integer;

-- returns current cost of construction

-- version 1
-- returns current cost of ONE construction (meaning ONE contract) separated by status
-- will have 2 tuples
-- cost for In Progress
-- cost for Completed
select contid, status, sum(price) as curr_cost
from (
	select contid, pid, status
	from hong1.cont_part
	where contid = 14213232
	and status > 0
	)
join hong1.part using (pid)
group by status, contid
order by status;

-- version 2
-- returns current cost of ONE construction (meaning ONE contract)
-- sum of status 1 and 2
select contid, sum(price) as curr_cost
from (
	select contid, pid, status
	from hong1.cont_part
	where contid = 14213232
	and status > 0
	)
join hong1.part using (pid)
group by contid;