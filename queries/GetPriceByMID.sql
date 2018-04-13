SELECT (((Retail+Labor) * MARKUP)/10) AS PRICE FROM 
	(SELECT SUM(price) AS Retail FROM hong1.model_part, hong1.part 
			WHERE hong1.model_part.mid=10000 AND hong1.model_part.pid=hong1.part.pid), 
	(SELECT labor, markup FROM hong1.model WHERE hong1.model.mid=10000)
