// Get Contract Detail
SELECT CONTID, hong1.model.name, Time_stamp 
	FROM hong1.model, hong1.contract, hong1.customer  
	WHERE hong1.contract.cusid=hong1.customer.cusid AND hong1.contract.mid=hong1.model.mid AND hong1.customer.username='wenkang';

// Compute Each Contract price
SELECT (((Retail+Labor) * MARKUP)/10) AS PRICE FROM
	(SELECT SUM(price) AS Retail
		FROM hong1.contract, hong1.cont_part, hong1.part
		WHERE hong1.contract.contID=hong1.cont_part.contid AND hong1.cont_part.pid=hong1.part.pid AND hong1.contract.contID=12345671
		GROUP BY hong1.contract.contid),
	(SELECT labor, markup FROM hong1.contract, hong1.model WHERE hong1.contract.mid=hong1.model.mid AND hong1.contract.contID=12345671);