-- This query returns part's information and the number of parts
-- counted under certain status condition grouped by pid.
--
-- Default: status -> In Progress
--
-- To change status setting, change value x in line "where status = x"
-- 0 = To Be Added
-- 1 = In Progress
-- 2 = Completed

select pid, name, status, quantity, price as price_each
from (
	select pid, status, count(pid) as quantity
	from (
		select pid, status
		from hong1.cont_part
		where status = 1
	)
	group by pid, status
)
join hong1.part using (pid)
order by quantity desc;
