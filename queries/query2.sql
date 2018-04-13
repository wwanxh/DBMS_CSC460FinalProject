@Query2
SELECT hong1.contract.contID, hong1.model.mid, hong1.model.name, Developed_Part, In_Progress_Part 
	FROM hong1.model, hong1.contract, 
	(SELECT ContID AS CD, COUNT(status) AS Developed_Part FROM hong1.cont_part WHERE status=2 GROUP BY ContID),
	(SELECT ContID AS CI, COUNT(status) AS In_Progress_Part FROM hong1.cont_part WHERE status<>2 GROUP BY ContID)
	WHERE
	hong1.model.mid=hong1.contract.mid AND hong1.contract.contid=CD AND hong1.contract.contid=CI AND CD>0 AND CI>0;

