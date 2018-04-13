// Department Selector
SELECT hong1.contract.did, count(hong1.contract.contid) 
	FROM hong1.department, hong1.contract 
	WHERE hong1.contract.mid=10005 AND hong1.department.did=hong1.contract.did 
GROUP BY hong1.contract.did 
ORDER BY count(hong1.contract.contid);