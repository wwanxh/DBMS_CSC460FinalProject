-- This query is for:
-- List the total revenue from each department in descending order
-- The revenue come from every contract processed by the department plus the labor needed for the specific model
select did, name as department_name, total_price from
(
select did, ((cont_price+labor)*markup/10) as total_price from
(
select contid, sum(price) as cont_price
from hong1.contract 
join hong1.cont_part using (contid) 
join hong1.part using (pid) 
group by contid
)
join hong1.contract using (contid)
join hong1.model using (mid)
)
join hong1.department using (did)
order by total_price desc
;
