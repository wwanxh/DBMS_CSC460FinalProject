-- This query returns every customer's total amount of money spent
-- in our company.


select cusid, firstname, lastname, money_spent from
(
select cusid, sum(total_price) as money_spent from
(
select contid, mid, cusid, ((cont_price+labor)*markup/10) as total_price from
(
select contid, mid, cusid, sum(price) as cont_price
from hong1.contract 
join hong1.cont_part using (contid) 
join hong1.part using (pid) 
group by contid, mid, cusid
)
join hong1.model using (mid)
) 
join hong1.customer using (cusid)
group by cusid
)
join hong1.customer using (cusid);